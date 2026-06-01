package Autoshkolla.Autoshkolla.Controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Autoshkolla.Autoshkolla.Model.Instruktori;
import Autoshkolla.Autoshkolla.Model.Kandidati;
import Autoshkolla.Autoshkolla.repository.InstruktoriRepository;
import Autoshkolla.Autoshkolla.repository.KandidatiRepository;
import Autoshkolla.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class AuthController {

    private final KandidatiRepository kandidatiRepo;
    private final InstruktoriRepository instruktoriRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(KandidatiRepository k, InstruktoriRepository i,
                          JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.kandidatiRepo   = k;
        this.instruktoriRepo = i;
        this.jwtUtil         = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body,
                                   HttpServletResponse response) {
        String email    = body.get("email");
        String password = body.get("password");

        // Kontrollo Kandidatët
        Optional<Kandidati> kOpt = kandidatiRepo.findByEmail(email);
        if (kOpt.isPresent()) {
            Kandidati k = kOpt.get();
            if (!checkPassword(password, k.getPassword()))
                return ResponseEntity.status(401).body(Map.of("message", "Fjalëkalim i gabuar"));

            String role = k.getRole() != null ? k.getRole() : "USER";
            return buildTokenResponse(email, role, k.getEmri(), k.getMbiemri(),
                                      k.getKandidatId(), response);
        }

        // Kontrollo Instruktorët
        Optional<Instruktori> iOpt = instruktoriRepo.findByEmail(email);
        if (iOpt.isPresent()) {
            Instruktori i = iOpt.get();
            if (!checkPassword(password, i.getPassword()))
                return ResponseEntity.status(401).body(Map.of("message", "Fjalëkalim i gabuar"));

            return buildTokenResponse(email, "INSTRUKTOR", i.getEmri(), i.getMbiemri(),
                                      i.getInstruktorId(), response);
        }

        return ResponseEntity.status(401).body(Map.of("message", "Email nuk ekziston"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;

        if (request.getCookies() != null) {
            refreshToken = Arrays.stream(request.getCookies())
                    .filter(c -> "refreshToken".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst().orElse(null);
        }

        if (refreshToken == null || !jwtUtil.isValid(refreshToken))
            return ResponseEntity.status(401).body(Map.of("message", "Refresh token i pavlefshëm"));

        String email = jwtUtil.getEmail(refreshToken);

        String role = "USER";
        Optional<Kandidati> k = kandidatiRepo.findByEmail(email);
        if (k.isPresent()) {
            role = k.get().getRole() != null ? k.get().getRole() : "USER";
        } else {
            Optional<Instruktori> i = instruktoriRepo.findByEmail(email);
            if (i.isPresent()) role = "INSTRUKTOR";
        }

        String newAccess = jwtUtil.generateAccessToken(email, role);
        return ResponseEntity.ok(Map.of("accessToken", newAccess));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(Map.of("message", "U shkyç"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (kandidatiRepo.findByEmail(email).isPresent())
            return ResponseEntity.badRequest().body(Map.of("message", "Email ekziston tashmë"));

        Kandidati k = new Kandidati();
        k.setEmri(body.get("firstName"));
        k.setMbiemri(body.get("lastName"));
        k.setEmail(email);
        k.setPassword(passwordEncoder.encode(body.get("password")));
        k.setRole("USER");
        k.setNumriPersonal("NP-" + System.currentTimeMillis());
        k.setDataLindjes(java.time.LocalDate.now());
        k.setDataRegjistrimit(java.time.LocalDate.now());
        kandidatiRepo.save(k);
        return ResponseEntity.ok(Map.of("message", "Llogaria u krijua!"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer "))
            return ResponseEntity.status(401).body(Map.of("message", "Pa token"));
        String token = header.substring(7);
        if (!jwtUtil.isValid(token))
            return ResponseEntity.status(401).body(Map.of("message", "Token i pavlefshëm"));
        return ResponseEntity.ok(Map.of(
                "email", jwtUtil.getEmail(token),
                "role",  jwtUtil.getRole(token)
        ));
    }

    // ── helpers ──────────────────────────────────────────────────
    private boolean checkPassword(String raw, String stored) {
        if (stored == null) return false;
        if (stored.startsWith("$2")) return passwordEncoder.matches(raw, stored);
        return raw.equals(stored);
    }

    private ResponseEntity<?> buildTokenResponse(String email, String role,
                                                   String firstName, String lastName,
                                                   Long id, HttpServletResponse response) {
        String accessToken  = jwtUtil.generateAccessToken(email, role);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "email",       email,
                "firstName",   firstName != null ? firstName : "",
                "lastName",    lastName  != null ? lastName  : "",
                "role",        role,
                "id",          id
        ));
    }
}