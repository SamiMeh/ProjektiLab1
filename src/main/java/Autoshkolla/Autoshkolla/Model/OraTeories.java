package Autoshkolla.Autoshkolla.Model;

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "Oret_Teorise")
public class OraTeories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ore_teori_id")
    private Long oreTeoriId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regjistrim_id", nullable = false)
    @JsonIgnoreProperties({"oretTeorise", "oretPraktikes", "provimet", "pagesat"})
    private Regjistrimi regjistrimi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instruktor_id", nullable = false)
    @JsonIgnoreProperties({"oretTeorise", "oretPraktikes", "oraret"})
    private Instruktori instruktori;

    @Column(name = "data_ores")
    private LocalDate dataOres;

    @Column(name = "ora_fillimit")
    private LocalTime oraFillimit;

    @Column(name = "ora_mbarimit")
    private LocalTime oraMbarimit;

    @Column(name = "tema", length = 255)
    private String tema;

    @Column(name = "prezent")
    private Boolean prezent = false;

    public OraTeories() {}

    public Long getOreTeoriId() { return oreTeoriId; }
    public void setOreTeoriId(Long oreTeoriId) { this.oreTeoriId = oreTeoriId; }
    public Regjistrimi getRegjistrimi() { return regjistrimi; }
    public void setRegjistrimi(Regjistrimi regjistrimi) { this.regjistrimi = regjistrimi; }
    public Instruktori getInstruktori() { return instruktori; }
    public void setInstruktori(Instruktori instruktori) { this.instruktori = instruktori; }
    public LocalDate getDataOres() { return dataOres; }
    public void setDataOres(LocalDate dataOres) { this.dataOres = dataOres; }
    public LocalTime getOraFillimit() { return oraFillimit; }
    public void setOraFillimit(LocalTime oraFillimit) { this.oraFillimit = oraFillimit; }
    public LocalTime getOraMbarimit() { return oraMbarimit; }
    public void setOraMbarimit(LocalTime oraMbarimit) { this.oraMbarimit = oraMbarimit; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public Boolean getPrezent() { return prezent; }
    public void setPrezent(Boolean prezent) { this.prezent = prezent; }
}