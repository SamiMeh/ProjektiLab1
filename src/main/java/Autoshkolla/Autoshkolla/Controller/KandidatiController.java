package Autoshkolla.Autoshkolla.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Autoshkolla.Autoshkolla.Model.Kandidati;
import Autoshkolla.Autoshkolla.Service.KandidatiService;

@RestController
@RequestMapping("/api/kandidate")
@CrossOrigin(origins = "*")
public class KandidatiController {

    @Autowired
    private KandidatiService kandidatiService;

    @GetMapping
    public List<Kandidati> getAll() {
        return kandidatiService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kandidati> getById(@PathVariable Long id) {
        return kandidatiService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Kandidati create(@RequestBody Kandidati kandidati) {
        return kandidatiService.save(kandidati);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kandidati> update(@PathVariable Long id, @RequestBody Kandidati kandidati) {
        try {
            return ResponseEntity.ok(kandidatiService.update(id, kandidati));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        kandidatiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
