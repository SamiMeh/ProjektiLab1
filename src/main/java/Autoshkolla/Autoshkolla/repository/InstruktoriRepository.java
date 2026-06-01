package Autoshkolla.Autoshkolla.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Autoshkolla.Autoshkolla.Model.Instruktori;

@Repository
public interface InstruktoriRepository extends JpaRepository<Instruktori, Long> {

    Optional<Instruktori> findByEmail(String email);

}