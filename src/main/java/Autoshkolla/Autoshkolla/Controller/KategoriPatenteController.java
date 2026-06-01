package Autoshkolla.Autoshkolla.Controller;

import Autoshkolla.Autoshkolla.Model.KategoriPatente;
import Autoshkolla.Autoshkolla.Service.KategoriPatenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kategorite-patentes")
@CrossOrigin(origins = "*")
public class KategoriPatenteController {

    @Autowired
    private KategoriPatenteService kategoriPatenteService;

    @GetMapping
    public List<KategoriPatente> getAll() {
        return kategoriPatenteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KategoriPatente> getById(@PathVariable Long id) {
        return kategoriPatenteService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public KategoriPatente create(@RequestBody KategoriPatente kategoriPatente) {
        return kategoriPatenteService.save(kategoriPatente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KategoriPatente> update(@PathVariable Long id, @RequestBody KategoriPatente kategoriPatente) {
        try {
            return ResponseEntity.ok(kategoriPatenteService.update(id, kategoriPatente));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        kategoriPatenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
