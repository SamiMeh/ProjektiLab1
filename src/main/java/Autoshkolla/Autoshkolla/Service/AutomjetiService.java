package Autoshkolla.Autoshkolla.Service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Autoshkolla.Autoshkolla.Model.Automjeti;
import Autoshkolla.Autoshkolla.repository.AutomjetiRepository;

@Service
public class AutomjetiService {

    @Autowired
    private AutomjetiRepository automjetiRepository;

    public List<Automjeti> getAll() {
        return automjetiRepository.findAll();
    }

    public Optional<Automjeti> getById(Long id) {
        return automjetiRepository.findById(id);
    }

    public Automjeti save(Automjeti automjeti) {
        return automjetiRepository.save(automjeti);
    }

    public Automjeti update(Long id, Automjeti updated) {
        return automjetiRepository.findById(id).map(a -> {
            a.setMarka(updated.getMarka());                                   // marka
            a.setModeli(updated.getModeli());                                 // modeli
            a.setTarga(updated.getTarga());                                   // targa
            a.setVitiProdhimit(updated.getVitiProdhimit());                   // viti_prodhimit
            a.setKategoriaPatentes(updated.getKategoriaPatentes());           // kategoria_patentes
            a.setDataRegjistrimitTeknik(updated.getDataRegjistrimitTeknik()); // data_regjistrimit_teknik
            a.setAktiv(updated.getAktiv());                                   // aktiv
            return automjetiRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Automjeti nuk u gjet: " + id));
    }

    public void delete(Long id) {
        automjetiRepository.deleteById(id);
    }
}
