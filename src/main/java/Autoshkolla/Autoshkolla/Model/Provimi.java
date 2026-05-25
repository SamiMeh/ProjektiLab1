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
@Table(name = "Provimet")
public class Provimi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provim_id")
    private Long provimId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regjistrim_id", nullable = false)
    @JsonIgnoreProperties({"oretTeorise", "oretPraktikes", "provimet", "pagesat"})
    private Regjistrimi regjistrimi;

    @Column(name = "lloji_provimit", length = 50)
    private String llojiProvimit;

    @Column(name = "data_provimit")
    private LocalDate dataProvimit;

    @Column(name = "rezultati", length = 50)
    private String rezultati;

    @Column(name = "piket")
    private Integer piket;

    @Column(name = "kalues")
    private Boolean kalues;

    @Column(name = "shenimet", columnDefinition = "TEXT")
    private String shenimet;

    public Provimi() {}

    public Long getProvimId() { return provimId; }
    public void setProvimId(Long provimId) { this.provimId = provimId; }
    public Regjistrimi getRegjistrimi() { return regjistrimi; }
    public void setRegjistrimi(Regjistrimi regjistrimi) { this.regjistrimi = regjistrimi; }
    public String getLlojiProvimit() { return llojiProvimit; }
    public void setLlojiProvimit(String llojiProvimit) { this.llojiProvimit = llojiProvimit; }
    public LocalDate getDataProvimit() { return dataProvimit; }
    public void setDataProvimit(LocalDate dataProvimit) { this.dataProvimit = dataProvimit; }
    public String getRezultati() { return rezultati; }
    public void setRezultati(String rezultati) { this.rezultati = rezultati; }
    public Integer getPiket() { return piket; }
    public void setPiket(Integer piket) { this.piket = piket; }
    public Boolean getKalues() { return kalues; }
    public void setKalues(Boolean kalues) { this.kalues = kalues; }
    public String getShenimet() { return shenimet; }
    public void setShenimet(String shenimet) { this.shenimet = shenimet; }
}