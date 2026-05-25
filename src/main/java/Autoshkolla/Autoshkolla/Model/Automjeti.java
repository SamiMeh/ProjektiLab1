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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Automjetet")
public class Automjeti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "automjet_id")
    private Long automjetId;

    @Column(name = "marka", length = 100)
    private String marka;

    @Column(name = "modeli", length = 100)
    private String modeli;

    @Column(name = "targa", unique = true, nullable = false, length = 20)
    private String targa;

    @Column(name = "viti_prodhimit")
    private Integer vitiProdhimit;

    @Column(name = "kategoria_patentes", length = 50)
    private String kategoriaPatentes;

    @Column(name = "data_regjistrimit_teknik")
    private LocalDate dataRegjistrimitTeknik;

    @Column(name = "aktiv")
    private Boolean aktiv = true;

    @OneToMany(mappedBy = "automjeti", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("automjeti")
    private List<OraPraktikes> oretPraktikes;

    public Automjeti() {}

    public Long getAutomjetId() { return automjetId; }
    public void setAutomjetId(Long automjetId) { this.automjetId = automjetId; }
    public String getMarka() { return marka; }
    public void setMarka(String marka) { this.marka = marka; }
    public String getModeli() { return modeli; }
    public void setModeli(String modeli) { this.modeli = modeli; }
    public String getTarga() { return targa; }
    public void setTarga(String targa) { this.targa = targa; }
    public Integer getVitiProdhimit() { return vitiProdhimit; }
    public void setVitiProdhimit(Integer vitiProdhimit) { this.vitiProdhimit = vitiProdhimit; }
    public String getKategoriaPatentes() { return kategoriaPatentes; }
    public void setKategoriaPatentes(String kategoriaPatentes) { this.kategoriaPatentes = kategoriaPatentes; }
    public LocalDate getDataRegjistrimitTeknik() { return dataRegjistrimitTeknik; }
    public void setDataRegjistrimitTeknik(LocalDate d) { this.dataRegjistrimitTeknik = d; }
    public Boolean getAktiv() { return aktiv; }
    public void setAktiv(Boolean aktiv) { this.aktiv = aktiv; }
    public List<OraPraktikes> getOretPraktikes() { return oretPraktikes; }
    public void setOretPraktikes(List<OraPraktikes> oretPraktikes) { this.oretPraktikes = oretPraktikes; }
}