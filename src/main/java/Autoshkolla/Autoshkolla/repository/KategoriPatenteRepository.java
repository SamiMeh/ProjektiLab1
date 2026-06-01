package Autoshkolla.Autoshkolla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Autoshkolla.Autoshkolla.Model.KategoriPatente;

@Repository
public interface KategoriPatenteRepository extends JpaRepository<KategoriPatente, Long> {
    // Add custom query methods if needed
}

