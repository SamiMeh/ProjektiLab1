package Autoshkolla.Autoshkolla.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Kategorite_Patentes")
public class KategoriPatente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kategori_id")
    private Long kategoriId;

    @Column(name = "emri_kategorise", nullable = false, length = 50)
    private String emriKategorise;

    @Column(name = "pershkrimi", length = 255)
    private String pershkrimi;

    @Column(name = "mosha_minimale", nullable = false)
    private Integer moshaMinimale;

    @Column(name = "ore_teori", nullable = false)
    private Integer oreTeori;

    @Column(name = "ore_praktike", nullable = false)
    private Integer orePraktike;

    @Column(name = "cmimi", nullable = false)
    private Double cmimi;

    @OneToMany(mappedBy = "kategoriPatente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Regjistrimi> regjistrimet;

    public KategoriPatente() {}

    public Long getKategoriId() { return kategoriId; }
    public void setKategoriId(Long kategoriId) { this.kategoriId = kategoriId; }
    public String getEmriKategorise() { return emriKategorise; }
    public void setEmriKategorise(String emriKategorise) { this.emriKategorise = emriKategorise; }
    public String getPershkrimi() { return pershkrimi; }
    public void setPershkrimi(String pershkrimi) { this.pershkrimi = pershkrimi; }
    public Integer getMoshaMinimale() { return moshaMinimale; }
    public void setMoshaMinimale(Integer moshaMinimale) { this.moshaMinimale = moshaMinimale; }
    public Integer getOreTeori() { return oreTeori; }
    public void setOreTeori(Integer oreTeori) { this.oreTeori = oreTeori; }
    public Integer getOrePraktike() { return orePraktike; }
    public void setOrePraktike(Integer orePraktike) { this.orePraktike = orePraktike; }
    public Double getCmimi() { return cmimi; }
    public void setCmimi(Double cmimi) { this.cmimi = cmimi; }
    public List<Regjistrimi> getRegjistrimet() { return regjistrimet; }
    public void setRegjistrimet(List<Regjistrimi> regjistrimet) { this.regjistrimet = regjistrimet; }
}