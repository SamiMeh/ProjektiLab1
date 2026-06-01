package Autoshkolla.Autoshkolla.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Autoshkolla.Autoshkolla.Model.Pagesa;
import Autoshkolla.Autoshkolla.repository.PagesaRepository;

@Service
public class PagesaService {

    @Autowired
    private PagesaRepository pagesaRepository;

    public List<Pagesa> getAll() {
        return pagesaRepository.findAll();
    }

    public Optional<Pagesa> getById(Long id) {
        return pagesaRepository.findById(id);
    }

    public Pagesa save(Pagesa pagesa) {
        return pagesaRepository.save(pagesa);
    }

    public Pagesa update(Long id, Pagesa updated) {
        return pagesaRepository.findById(id).map(p -> {
            p.setRegjistrimi(updated.getRegjistrimi());     // regjistrim_id (FK)
            p.setShuma(updated.getShuma());                 // shuma
            p.setDataPageses(updated.getDataPageses());     // data_pageses
            p.setMetoda(updated.getMetoda());               // metoda
            p.setPershkrimi(updated.getPershkrimi());       // pershkrimi
            p.setStatusi(updated.getStatusi());             // statusi
            return pagesaRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Pagesa nuk u gjet: " + id));
    }

    public void delete(Long id) {
        pagesaRepository.deleteById(id);
    }
}
