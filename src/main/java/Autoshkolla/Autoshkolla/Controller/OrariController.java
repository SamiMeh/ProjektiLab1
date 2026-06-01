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

import Autoshkolla.Autoshkolla.Model.Orari;
import Autoshkolla.Autoshkolla.Service.OrariService;

@RestController
@RequestMapping("/api/oraret")
@CrossOrigin(origins = "*")
public class OrariController {

    @Autowired
    private OrariService orariService;

    @GetMapping
    public List<Orari> getAll() {
        return orariService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orari> getById(@PathVariable Long id) {
        return orariService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Orari create(@RequestBody Orari orari) {
        return orariService.save(orari);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orari> update(@PathVariable Long id, @RequestBody Orari orari) {
        try {
            return ResponseEntity.ok(orariService.update(id, orari));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orariService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
