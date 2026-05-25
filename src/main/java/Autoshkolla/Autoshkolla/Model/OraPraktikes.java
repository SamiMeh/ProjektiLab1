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
@Table(name = "Oret_Praktikes")
public class OraPraktikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ore_praktike_id")
    private Long orePraktikeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regjistrim_id", nullable = false)
    @JsonIgnoreProperties({"oretTeorise", "oretPraktikes", "provimet", "pagesat"})
    private Regjistrimi regjistrimi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instruktor_id", nullable = false)
    @JsonIgnoreProperties({"oretTeorise", "oretPraktikes", "oraret"})
    private Instruktori instruktori;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "automjet_id", nullable = false)
    @JsonIgnoreProperties("oretPraktikes")
    private Automjeti automjeti;

    @Column(name = "data_ores")
    private LocalDate dataOres;

    @Column(name = "ora_fillimit")
    private LocalTime oraFillimit;

    @Column(name = "ora_mbarimit")
    private LocalTime oraMbarimit;

    @Column(name = "vleresimi")
    private Integer vleresimi;

    public OraPraktikes() {}

    public Long getOrePraktikeId() { return orePraktikeId; }
    public void setOrePraktikeId(Long orePraktikeId) { this.orePraktikeId = orePraktikeId; }
    public Regjistrimi getRegjistrimi() { return regjistrimi; }
    public void setRegjistrimi(Regjistrimi regjistrimi) { this.regjistrimi = regjistrimi; }
    public Instruktori getInstruktori() { return instruktori; }
    public void setInstruktori(Instruktori instruktori) { this.instruktori = instruktori; }
    public Automjeti getAutomjeti() { return automjeti; }
    public void setAutomjeti(Automjeti automjeti) { this.automjeti = automjeti; }
    public LocalDate getDataOres() { return dataOres; }
    public void setDataOres(LocalDate dataOres) { this.dataOres = dataOres; }
    public LocalTime getOraFillimit() { return oraFillimit; }
    public void setOraFillimit(LocalTime oraFillimit) { this.oraFillimit = oraFillimit; }
    public LocalTime getOraMbarimit() { return oraMbarimit; }
    public void setOraMbarimit(LocalTime oraMbarimit) { this.oraMbarimit = oraMbarimit; }
    public Integer getVleresimi() { return vleresimi; }
    public void setVleresimi(Integer vleresimi) { this.vleresimi = vleresimi; }
}
