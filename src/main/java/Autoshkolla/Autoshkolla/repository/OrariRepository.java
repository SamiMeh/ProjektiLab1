package Autoshkolla.Autoshkolla.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Autoshkolla.Autoshkolla.Model.Orari;
public interface OrariRepository extends JpaRepository<Orari, Long> {
    List<Orari> findByInstruktoriInstruktorId(Long instruktorId);
    List<Orari> findByAktivTrue();
}