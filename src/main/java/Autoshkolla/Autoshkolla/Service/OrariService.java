package Autoshkolla.Autoshkolla.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Autoshkolla.Autoshkolla.Model.Orari;
import Autoshkolla.Autoshkolla.repository.OrariRepository;

@Service
public class OrariService {

    @Autowired
    private OrariRepository orariRepository;

    public List<Orari> getAll() {
        return orariRepository.findAll();
    }

    public Optional<Orari> getById(Long id) {
        return orariRepository.findById(id);
    }

    public Orari save(Orari orari) {
        return orariRepository.save(orari);
    }

    public Orari update(Long id, Orari updated) {
        return orariRepository.findById(id).map(o -> {
            o.setInstruktori(updated.getInstruktori());     // instruktor_id (FK)
            o.setDitaJaves(updated.getDitaJaves());         // dita_javes
            o.setOraFillimit(updated.getOraFillimit());     // ora_fillimit
            o.setOraMbarimit(updated.getOraMbarimit());     // ora_mbarimit
            o.setLloji(updated.getLloji());                 // lloji
            o.setAktiv(updated.getAktiv());                 // aktiv
            return orariRepository.save(o);
        }).orElseThrow(() -> new RuntimeException("Orari nuk u gjet: " + id));
    }

    public void delete(Long id) {
        orariRepository.deleteById(id);
    }
}
