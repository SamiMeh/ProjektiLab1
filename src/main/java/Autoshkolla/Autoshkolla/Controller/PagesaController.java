package Autoshkolla.Autoshkolla.Controller;

import Autoshkolla.Autoshkolla.Model.Pagesa;
import Autoshkolla.Autoshkolla.Service.PagesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagesa")
@CrossOrigin(origins = "*")
public class PagesaController {

    @Autowired
    private PagesaService pagesaService;

    @GetMapping
    public List<Pagesa> getAll() {
        return pagesaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagesa> getById(@PathVariable Long id) {
        return pagesaService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pagesa create(@RequestBody Pagesa pagesa) {
        return pagesaService.save(pagesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagesa> update(@PathVariable Long id, @RequestBody Pagesa pagesa) {
        try {
            return ResponseEntity.ok(pagesaService.update(id, pagesa));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
