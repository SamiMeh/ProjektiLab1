package Autoshkolla.Autoshkolla.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Autoshkolla.Autoshkolla.Model.Kandidati;

public interface KandidatiRepository extends JpaRepository<Kandidati, Long> {
    List<Kandidati> findByEmriContainingIgnoreCase(String emri);
    Optional<Kandidati> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNumriPersonal(String numriPersonal);
}