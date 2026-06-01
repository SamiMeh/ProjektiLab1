package Autoshkolla.Autoshkolla.Controller;

import Autoshkolla.Autoshkolla.Model.Instruktori;
import Autoshkolla.Autoshkolla.Service.InstruktoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instruktore")
@CrossOrigin(origins = "*")
public class InstruktoriController {

    @Autowired
    private InstruktoriService instruktoriService;

    @GetMapping
    public List<Instruktori> getAll() {
        return instruktoriService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instruktori> getById(@PathVariable Long id) {
        return instruktoriService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Instruktori create(@RequestBody Instruktori instruktori) {
        return instruktoriService.save(instruktori);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instruktori> update(@PathVariable Long id, @RequestBody Instruktori instruktori) {
        try {
            return ResponseEntity.ok(instruktoriService.update(id, instruktori));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        instruktoriService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
