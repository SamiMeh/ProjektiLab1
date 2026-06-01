package Autoshkolla.Autoshkolla.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Autoshkolla.Autoshkolla.Model.Instruktori;
import Autoshkolla.Autoshkolla.repository.InstruktoriRepository;

@Service
public class InstruktoriService {

    @Autowired
    private InstruktoriRepository instruktoriRepository;

    public List<Instruktori> getAll() {
        return instruktoriRepository.findAll();
    }

    public Optional<Instruktori> getById(Long id) {
        return instruktoriRepository.findById(id);
    }

    public Instruktori save(Instruktori instruktori) {
        return instruktoriRepository.save(instruktori);
    }

    public Instruktori update(Long id, Instruktori updated) {
        return instruktoriRepository.findById(id).map(i -> {
            i.setEmri(updated.getEmri());                           // emri
            i.setMbiemri(updated.getMbiemri());                     // mbiemri
            i.setTelefoni(updated.getTelefoni());                   // telefoni
            i.setEmail(updated.getEmail());                         // email
            i.setLicencaNr(updated.getLicencaNr());                 // licenca_nr
            i.setKategoriteLeJuara(updated.getKategoriteLeJuara()); // kategorite_lejuara
            i.setAktiv(updated.getAktiv());                         // aktiv
            return instruktoriRepository.save(i);
        }).orElseThrow(() -> new RuntimeException("Instruktori nuk u gjet: " + id));
    }

    public void delete(Long id) {
        instruktoriRepository.deleteById(id);
    }
}
