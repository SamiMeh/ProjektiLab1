package Autoshkolla.Autoshkolla.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Autoshkolla.Autoshkolla.Model.OraTeories;
import Autoshkolla.Autoshkolla.repository.OraTeorisesRepository;

@Service
public class OraTeorisesService {

    @Autowired
    private OraTeorisesRepository oraTeorisesRepository;

    public List<OraTeories> getAll() {
        return oraTeorisesRepository.findAll();
    }

    public Optional<OraTeories> getById(Long id) {
        return oraTeorisesRepository.findById(id);
    }

    public OraTeories save(OraTeories oraTeorises) {
        return oraTeorisesRepository.save(oraTeorises);
    }

    public OraTeories update(Long id, OraTeories updated) {
        return oraTeorisesRepository.findById(id).map(o -> {
            o.setRegjistrimi(updated.getRegjistrimi());
            o.setInstruktori(updated.getInstruktori());
            o.setDataOres(updated.getDataOres());
            o.setOraFillimit(updated.getOraFillimit());
            o.setOraMbarimit(updated.getOraMbarimit());
            o.setTema(updated.getTema());
            o.setPrezent(updated.getPrezent());
            return oraTeorisesRepository.save(o);
        }).orElseThrow(() -> new RuntimeException("OraTeorises nuk u gjet: " + id));
    }

    public void delete(Long id) {
        oraTeorisesRepository.deleteById(id);
    }
}