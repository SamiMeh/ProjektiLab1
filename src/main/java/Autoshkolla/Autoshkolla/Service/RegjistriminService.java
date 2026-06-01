package Autoshkolla.Autoshkolla.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Autoshkolla.Autoshkolla.Model.Kandidati;
import Autoshkolla.Autoshkolla.Model.KategoriPatente;
import Autoshkolla.Autoshkolla.Model.Regjistrimi;
import Autoshkolla.Autoshkolla.repository.KandidatiRepository;
import Autoshkolla.Autoshkolla.repository.KategoriPatenteRepository;
import Autoshkolla.Autoshkolla.repository.RegjistrimiRepository;

@Service
public class RegjistriminService {

    @Autowired
    private RegjistrimiRepository regjistriminRepository;

    @Autowired
    private KandidatiRepository kandidatiRepository;

    @Autowired
    private KategoriPatenteRepository kategoriPatenteRepository;

    public List<Regjistrimi> getAll() {
        return regjistriminRepository.findAll();
    }

    public Optional<Regjistrimi> getById(Long id) {
        return regjistriminRepository.findById(id);
    }

    public Regjistrimi save(Regjistrimi regjistrimi) {
        // Gjej kandidatin nga DB
        if (regjistrimi.getKandidati() != null && regjistrimi.getKandidati().getKandidatId() != null) {
            Kandidati k = kandidatiRepository.findById(regjistrimi.getKandidati().getKandidatId())
                .orElseThrow(() -> new RuntimeException("Kandidati nuk u gjet"));
            regjistrimi.setKandidati(k);
        }

        // Gjej kategorine nga DB
        if (regjistrimi.getKategoriPatente() != null && regjistrimi.getKategoriPatente().getKategoriId() != null) {
            KategoriPatente kp = kategoriPatenteRepository.findById(regjistrimi.getKategoriPatente().getKategoriId())
                .orElseThrow(() -> new RuntimeException("Kategoria nuk u gjet"));
            regjistrimi.setKategoriPatente(kp);
        }

        return regjistriminRepository.save(regjistrimi);
    }

    public Regjistrimi update(Long id, Regjistrimi updated) {
        return regjistriminRepository.findById(id).map(r -> {
            // Gjej kandidatin nga DB
            if (updated.getKandidati() != null && updated.getKandidati().getKandidatId() != null) {
                Kandidati k = kandidatiRepository.findById(updated.getKandidati().getKandidatId())
                    .orElseThrow(() -> new RuntimeException("Kandidati nuk u gjet"));
                r.setKandidati(k);
            }

            // Gjej kategorine nga DB
            if (updated.getKategoriPatente() != null && updated.getKategoriPatente().getKategoriId() != null) {
                KategoriPatente kp = kategoriPatenteRepository.findById(updated.getKategoriPatente().getKategoriId())
                    .orElseThrow(() -> new RuntimeException("Kategoria nuk u gjet"));
                r.setKategoriPatente(kp);
            }

            r.setDataRegjistrimit(updated.getDataRegjistrimit());
            r.setStatusi(updated.getStatusi());
            r.setCimiTotal(updated.getCimiTotal());
            r.setShumaPaguar(updated.getShumaPaguar());
            return regjistriminRepository.save(r);
        }).orElseThrow(() -> new RuntimeException("Regjistrimi nuk u gjet: " + id));
    }

    public void delete(Long id) {
        regjistriminRepository.deleteById(id);
    }

    public List<Regjistrimi> findByKandidatiId(Long kandidatId) {
        return regjistriminRepository.findByKandidatiKandidatId(kandidatId);
    }

    public List<Regjistrimi> findByStatusi(String statusi) {
        return regjistriminRepository.findByStatusi(statusi);
    }
}