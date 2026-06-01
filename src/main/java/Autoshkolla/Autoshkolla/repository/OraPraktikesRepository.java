package Autoshkolla.Autoshkolla.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Autoshkolla.Autoshkolla.Model.OraPraktikes;
public interface OraPraktikesRepository extends JpaRepository<OraPraktikes, Long> {
    List<OraPraktikes> findByRegjistrimiRegjistrimId(Long regjistrimId);
}