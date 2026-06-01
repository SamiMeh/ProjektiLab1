package Autoshkolla.Autoshkolla.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Autoshkolla.Autoshkolla.Model.Provimi;
import Autoshkolla.Autoshkolla.repository.ProvimiRepository;

@Service
public class ProvimiService {

    @Autowired
    private ProvimiRepository provimiRepository;

    public List<Provimi> getAll() {
        return provimiRepository.findAll();
    }

    public Optional<Provimi> getById(Long id) {
        return provimiRepository.findById(id);
    }

    public Provimi save(Provimi provimi) {
        return provimiRepository.save(provimi);
    }

    public Provimi update(Long id, Provimi updated) {
        return provimiRepository.findById(id).map(p -> {
            p.setRegjistrimi(updated.getRegjistrimi());     // regjistrim_id (FK)
            p.setLlojiProvimit(updated.getLlojiProvimit()); // lloji_provimit
            p.setDataProvimit(updated.getDataProvimit());   // data_provimit
            p.setRezultati(updated.getRezultati());         // rezultati
            p.setPiket(updated.getPiket());                 // piket
            p.setKalues(updated.getKalues());               // kalues
            p.setShenimet(updated.getShenimet());           // shenimet
            return provimiRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Provimi nuk u gjet: " + id));
    }

    public void delete(Long id) {
        provimiRepository.deleteById(id);
    }
}
