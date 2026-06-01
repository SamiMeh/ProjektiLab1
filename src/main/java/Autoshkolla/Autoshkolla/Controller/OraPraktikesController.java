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

import Autoshkolla.Autoshkolla.Model.OraPraktikes;
import Autoshkolla.Autoshkolla.Service.OraPraktikesService;

@RestController
@RequestMapping("/api/oret-praktikes")
@CrossOrigin(origins = "*")
public class OraPraktikesController {

    @Autowired
    private OraPraktikesService oraPraktikesService;

    @GetMapping
    public List<OraPraktikes> getAll() {
        return oraPraktikesService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OraPraktikes> getById(@PathVariable Long id) {
        return oraPraktikesService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OraPraktikes create(@RequestBody OraPraktikes oraPraktikes) {
        return oraPraktikesService.save(oraPraktikes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OraPraktikes> update(@PathVariable Long id, @RequestBody OraPraktikes oraPraktikes) {
        try {
            return ResponseEntity.ok(oraPraktikesService.update(id, oraPraktikes));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        oraPraktikesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
