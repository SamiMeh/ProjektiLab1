package Autoshkolla.Autoshkolla.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Autoshkolla.Autoshkolla.Model.OraPraktikes;
import Autoshkolla.Autoshkolla.repository.OraPraktikesRepository;

@Service
public class OraPraktikesService {

    @Autowired
    private OraPraktikesRepository oraPraktikesRepository;

    public List<OraPraktikes> getAll() {
        return oraPraktikesRepository.findAll();
    }

    public Optional<OraPraktikes> getById(Long id) {
        return oraPraktikesRepository.findById(id);
    }

    public OraPraktikes save(OraPraktikes oraPraktikes) {
        return oraPraktikesRepository.save(oraPraktikes);
    }

    public OraPraktikes update(Long id, OraPraktikes updated) {
        return oraPraktikesRepository.findById(id).map(o -> {
            o.setRegjistrimi(updated.getRegjistrimi());     // regjistrim_id (FK)
            o.setInstruktori(updated.getInstruktori());     // instruktor_id (FK)
            o.setAutomjeti(updated.getAutomjeti());         // automjet_id (FK)
            o.setDataOres(updated.getDataOres());           // data_ores
            o.setOraFillimit(updated.getOraFillimit());     // ora_fillimit
            o.setOraMbarimit(updated.getOraMbarimit());     // ora_mbarimit
            o.setVleresimi(updated.getVleresimi());         // vleresimi
            return oraPraktikesRepository.save(o);
        }).orElseThrow(() -> new RuntimeException("OraPraktikes nuk u gjet: " + id));
    }

    public void delete(Long id) {
        oraPraktikesRepository.deleteById(id);
    }
}
