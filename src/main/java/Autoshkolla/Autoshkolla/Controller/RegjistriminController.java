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

import Autoshkolla.Autoshkolla.Model.Regjistrimi;
import Autoshkolla.Autoshkolla.Service.RegjistriminService;

@RestController
@RequestMapping("/api/regjistrime")
@CrossOrigin(origins = "*")
public class RegjistriminController {

    @Autowired
    private RegjistriminService regjistriminService;

    @GetMapping
    public List<Regjistrimi> getAll() {
        return regjistriminService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Regjistrimi> getById(@PathVariable Long id) {
        return regjistriminService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Regjistrimi create(@RequestBody Regjistrimi regjistrimi) {
        return regjistriminService.save(regjistrimi);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Regjistrimi> update(@PathVariable Long id, @RequestBody Regjistrimi regjistrimi) {
        try {
            return ResponseEntity.ok(regjistriminService.update(id, regjistrimi));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regjistriminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
