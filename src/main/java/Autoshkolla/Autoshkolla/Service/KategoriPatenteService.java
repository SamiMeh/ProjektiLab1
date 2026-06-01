package Autoshkolla.Autoshkolla.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Autoshkolla.Autoshkolla.Model.KategoriPatente;
import Autoshkolla.Autoshkolla.repository.KategoriPatenteRepository;

@Service
public class KategoriPatenteService {

    @Autowired
    private KategoriPatenteRepository kategoriPatenteRepository;

    public List<KategoriPatente> getAll() {
        return kategoriPatenteRepository.findAll();
    }

    public Optional<KategoriPatente> getById(Long id) {
        return kategoriPatenteRepository.findById(id);
    }

    public KategoriPatente save(KategoriPatente kategoriPatente) {
        return kategoriPatenteRepository.save(kategoriPatente);
    }

    public KategoriPatente update(Long id, KategoriPatente updated) {
        return kategoriPatenteRepository.findById(id).map(k -> {
            k.setEmriKategorise(updated.getEmriKategorise());   // emri_kategorise
            k.setPershkrimi(updated.getPershkrimi());           // pershkrimi
            k.setMoshaMinimale(updated.getMoshaMinimale());     // mosha_minimale
            k.setOreTeori(updated.getOreTeori());               // ore_teori
            k.setOrePraktike(updated.getOrePraktike());         // ore_praktike
            k.setCmimi(updated.getCmimi());                     // cmimi
            return kategoriPatenteRepository.save(k);
        }).orElseThrow(() -> new RuntimeException("KategoriPatente nuk u gjet: " + id));
    }

    public void delete(Long id) {
        kategoriPatenteRepository.deleteById(id);
    }
}
