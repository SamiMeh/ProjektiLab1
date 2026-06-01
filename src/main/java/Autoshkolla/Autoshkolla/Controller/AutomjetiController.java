package Autoshkolla.Autoshkolla.Controller;

import Autoshkolla.Autoshkolla.Model.Automjeti;
import Autoshkolla.Autoshkolla.Service.AutomjetiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automjete")
@CrossOrigin(origins = "*")
public class AutomjetiController {

    @Autowired
    private AutomjetiService automjetiService;

    @GetMapping
    public List<Automjeti> getAll() {
        return automjetiService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Automjeti> getById(@PathVariable Long id) {
        return automjetiService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Automjeti create(@RequestBody Automjeti automjeti) {
        return automjetiService.save(automjeti);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Automjeti> update(@PathVariable Long id, @RequestBody Automjeti automjeti) {
        try {
            return ResponseEntity.ok(automjetiService.update(id, automjeti));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        automjetiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
