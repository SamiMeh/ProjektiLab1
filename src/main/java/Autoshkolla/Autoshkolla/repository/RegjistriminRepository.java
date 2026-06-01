package Autoshkolla.Autoshkolla.repository;

import Autoshkolla.Autoshkolla.Model.Regjistrimi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Importo këtë
import java.util.List;

@Repository 
public interface RegjistriminRepository extends JpaRepository<Regjistrimi, Long> {

    List<Regjistrimi> findByKandidatiKandidatId(Long kandidatId);
    List<Regjistrimi> findByStatusi(String statusi);
}