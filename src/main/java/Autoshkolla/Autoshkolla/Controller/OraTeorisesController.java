package Autoshkolla.Autoshkolla.Controller;

import Autoshkolla.Autoshkolla.Model.OraTeories;
import Autoshkolla.Autoshkolla.Service.OraTeorisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oret-teorise")
@CrossOrigin(origins = "*")
public class OraTeorisesController {

    @Autowired
    private OraTeorisesService oraTeorisesService;

    @GetMapping
    public List<OraTeories> getAll() {
        return oraTeorisesService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OraTeories> getById(@PathVariable Long id) {
        return oraTeorisesService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OraTeories create(@RequestBody OraTeories oraTeories) {
        return oraTeorisesService.save(oraTeories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OraTeories> update(@PathVariable Long id, @RequestBody OraTeories oraTeories) {
        try {
            return ResponseEntity.ok(oraTeorisesService.update(id, oraTeories));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        oraTeorisesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
