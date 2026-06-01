package Autoshkolla.Autoshkolla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Autoshkolla.Autoshkolla.Model.Automjeti;

public interface AutomjetiRepository extends JpaRepository<Automjeti, Long> {
    List<Automjeti> findByAktivTrue();
}

