package Autoshkolla.Autoshkolla.Model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Regjistrime")
public class Regjistrimi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regjistrim_id")
    private Long regjistrimId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kandidat_id", nullable = false)
    @JsonIgnoreProperties("regjistrimet")
    private Kandidati kandidati;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kategori_id", nullable = false)
    @JsonIgnoreProperties("regjistrimet")
    private KategoriPatente kategoriPatente;

    @Column(name = "data_regjistrimit")
    private LocalDate dataRegjistrimit;

    @Column(name = "statusi", length = 50)
    private String statusi;

    @Column(name = "cmimi_total")
    private Double cimiTotal;

    @Column(name = "shuma_paguar")
    private Double shumaPaguar;

    @OneToMany(mappedBy = "regjistrimi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("regjistrimi")
    private List<OraTeories> oretTeorise;

    @OneToMany(mappedBy = "regjistrimi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("regjistrimi")
    private List<OraPraktikes> oretPraktikes;

    @OneToMany(mappedBy = "regjistrimi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("regjistrimi")
    private List<Provimi> provimet;

    @OneToMany(mappedBy = "regjistrimi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("regjistrimi")
    private List<Pagesa> pagesat;

    public Regjistrimi() {}

    public Long getRegjistrimId() { return regjistrimId; }
    public void setRegjistrimId(Long regjistrimId) { this.regjistrimId = regjistrimId; }
    public Kandidati getKandidati() { return kandidati; }
    public void setKandidati(Kandidati kandidati) { this.kandidati = kandidati; }
    public KategoriPatente getKategoriPatente() { return kategoriPatente; }
    public void setKategoriPatente(KategoriPatente kategoriPatente) { this.kategoriPatente = kategoriPatente; }
    public LocalDate getDataRegjistrimit() { return dataRegjistrimit; }
    public void setDataRegjistrimit(LocalDate dataRegjistrimit) { this.dataRegjistrimit = dataRegjistrimit; }
    public String getStatusi() { return statusi; }
    public void setStatusi(String statusi) { this.statusi = statusi; }
    public Double getCimiTotal() { return cimiTotal; }
    public void setCimiTotal(Double cimiTotal) { this.cimiTotal = cimiTotal; }
    public Double getShumaPaguar() { return shumaPaguar; }
    public void setShumaPaguar(Double shumaPaguar) { this.shumaPaguar = shumaPaguar; }
    public List<OraTeories> getOretTeorise() { return oretTeorise; }
    public void setOretTeorise(List<OraTeories> oretTeorise) { this.oretTeorise = oretTeorise; }
    public List<OraPraktikes> getOretPraktikes() { return oretPraktikes; }
    public void setOretPraktikes(List<OraPraktikes> oretPraktikes) { this.oretPraktikes = oretPraktikes; }
    public List<Provimi> getProvimet() { return provimet; }
    public void setProvimet(List<Provimi> provimet) { this.provimet = provimet; }
    public List<Pagesa> getPagesat() { return pagesat; }
    public void setPagesat(List<Pagesa> pagesat) { this.pagesat = pagesat; }

}