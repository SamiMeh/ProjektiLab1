package Autoshkolla.Autoshkolla.Model;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pagesat")
public class Pagesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pagese_id")
    private Long pageseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regjistrim_id", nullable = false)
    @JsonIgnoreProperties({"oretTeorise", "oretPraktikes", "provimet", "pagesat"})
    private Regjistrimi regjistrimi;

    @Column(name = "shuma")
    private Double shuma;

    @Column(name = "data_pageses")
    private LocalDate dataPageses;

    @Column(name = "metoda", length = 50)
    private String metoda;

    @Column(name = "pershkrimi", length = 255)
    private String pershkrimi;

    @Column(name = "statusi", length = 50)
    private String statusi;

    public Pagesa() {}

    public Long getPageseId() { return pageseId; }
    public void setPageseId(Long pageseId) { this.pageseId = pageseId; }
    public Regjistrimi getRegjistrimi() { return regjistrimi; }
    public void setRegjistrimi(Regjistrimi regjistrimi) { this.regjistrimi = regjistrimi; }
    public Double getShuma() { return shuma; }
    public void setShuma(Double shuma) { this.shuma = shuma; }
    public LocalDate getDataPageses() { return dataPageses; }
    public void setDataPageses(LocalDate dataPageses) { this.dataPageses = dataPageses; }
    public String getMetoda() { return metoda; }
    public void setMetoda(String metoda) { this.metoda = metoda; }
    public String getPershkrimi() { return pershkrimi; }
    public void setPershkrimi(String pershkrimi) { this.pershkrimi = pershkrimi; }
    public String getStatusi() { return statusi; }
    public void setStatusi(String statusi) { this.statusi = statusi; }
}
