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

import Autoshkolla.Autoshkolla.Model.Provimi;
import Autoshkolla.Autoshkolla.Service.ProvimiService;

@RestController
@RequestMapping("/api/provimet")
@CrossOrigin(origins = "*")
public class ProvimiController {

    @Autowired
    private ProvimiService provimiService;

    @GetMapping
    public List<Provimi> getAll() {
        return provimiService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provimi> getById(@PathVariable Long id) {
        return provimiService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Provimi create(@RequestBody Provimi provimi) {
        return provimiService.save(provimi);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Provimi> update(@PathVariable Long id, @RequestBody Provimi provimi) {
        try {
            return ResponseEntity.ok(provimiService.update(id, provimi));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        provimiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}










