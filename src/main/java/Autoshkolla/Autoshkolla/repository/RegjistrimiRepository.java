package Autoshkolla.Autoshkolla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Autoshkolla.Autoshkolla.Model.Regjistrimi; // Importo këtë

@Repository 
public interface RegjistrimiRepository extends JpaRepository<Regjistrimi, Long> {
    
    List<Regjistrimi> findByKandidatiKandidatId(Long kandidatId);
    List<Regjistrimi> findByStatusi(String statusi);
}