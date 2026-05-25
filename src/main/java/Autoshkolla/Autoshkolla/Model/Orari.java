package Autoshkolla.Autoshkolla.Model;


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
@Table(name = "Oraret")
public class Orari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orar_id")
    private Long orarId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instruktor_id", nullable = false)
    @JsonIgnoreProperties({"oretTeorise", "oretPraktikes", "oraret"})
    private Instruktori instruktori;

    @Column(name = "dita_javes", length = 20)
    private String ditaJaves;

    @Column(name = "ora_fillimit")
    private LocalTime oraFillimit;

    @Column(name = "ora_mbarimit")
    private LocalTime oraMbarimit;

    @Column(name = "lloji", length = 50)
    private String lloji;

    @Column(name = "aktiv")
    private Boolean aktiv = true;

    public Orari() {}

    public Long getOrarId() { return orarId; }
    public void setOrarId(Long orarId) { this.orarId = orarId; }
    public Instruktori getInstruktori() { return instruktori; }
    public void setInstruktori(Instruktori instruktori) { this.instruktori = instruktori; }
    public String getDitaJaves() { return ditaJaves; }
    public void setDitaJaves(String ditaJaves) { this.ditaJaves = ditaJaves; }
    public LocalTime getOraFillimit() { return oraFillimit; }
    public void setOraFillimit(LocalTime oraFillimit) { this.oraFillimit = oraFillimit; }
    public LocalTime getOraMbarimit() { return oraMbarimit; }
    public void setOraMbarimit(LocalTime oraMbarimit) { this.oraMbarimit = oraMbarimit; }
    public String getLloji() { return lloji; }
    public void setLloji(String lloji) { this.lloji = lloji; }
    public Boolean getAktiv() { return aktiv; }
    public void setAktiv(Boolean aktiv) { this.aktiv = aktiv; }
}
