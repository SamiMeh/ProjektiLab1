package Autoshkolla.Autoshkolla.Service;

import Autoshkolla.Autoshkolla.Model.Kandidati;
import Autoshkolla.Autoshkolla.repository.KandidatiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KandidatiService {

    @Autowired
    private KandidatiRepository kandidatiRepository;

    public List<Kandidati> getAll() {
        return kandidatiRepository.findAll();
    }

    public Optional<Kandidati> getById(Long id) {
        return kandidatiRepository.findById(id);
    }

    public Kandidati save(Kandidati kandidati) {
        return kandidatiRepository.save(kandidati);
    }

    public Kandidati update(Long id, Kandidati updated) {
        return kandidatiRepository.findById(id).map(kandidati -> {
            kandidati.setEmri(updated.getEmri());
            kandidati.setMbiemri(updated.getMbiemri());
            kandidati.setNumriPersonal(updated.getNumriPersonal());
            kandidati.setDataLindjes(updated.getDataLindjes());
            kandidati.setEmail(updated.getEmail());
            kandidati.setTelefoni(updated.getTelefoni());
            kandidati.setAdresa(updated.getAdresa());
            kandidati.setDataRegjistrimit(updated.getDataRegjistrimit());
            return kandidatiRepository.save(kandidati);
        }).orElseThrow(() -> new RuntimeException("Kandidati nuk u gjet: " + id));
    }

    public void delete(Long id) {
        kandidatiRepository.deleteById(id);
    }
}
