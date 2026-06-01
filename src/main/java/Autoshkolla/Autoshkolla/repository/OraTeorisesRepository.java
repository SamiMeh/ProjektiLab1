package Autoshkolla.Autoshkolla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Autoshkolla.Autoshkolla.Model.OraTeories;

public interface OraTeorisesRepository extends JpaRepository<OraTeories, Long> {
    List<OraTeories> findByregjistrimiRegjistrimId(Long regjistrimId);
}
