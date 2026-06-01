package Autoshkolla.Autoshkolla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Autoshkolla.Autoshkolla.Model.Provimi;
public interface ProvimiRepository extends JpaRepository<Provimi, Long> {
    List<Provimi> findByregjistrimiRegjistrimId(Long regjistrimId);
}
