package Autoshkolla.Autoshkolla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Autoshkolla.Autoshkolla.Model.Pagesa;
public interface PagesaRepository extends JpaRepository<Pagesa, Long> {
    List<Pagesa> findByregjistrimiRegjistrimId(Long regjistrimId);
}