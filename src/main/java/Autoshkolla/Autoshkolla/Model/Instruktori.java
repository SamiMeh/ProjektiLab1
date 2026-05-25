package Autoshkolla.Autoshkolla.Model;

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
@Table(name = "Instruktoret")
public class Instruktori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instruktor_id")
    private Long instruktorId;

    @Column(name = "emri", nullable = false, length = 100)
    private String emri;

    @Column(name = "mbiemri", nullable = false, length = 100)
    private String mbiemri;

    @Column(name = "telefoni", length = 20)
    private String telefoni;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "licenca_nr", unique = true, length = 50)
    private String licencaNr;

    @Column(name = "kategorite_lejuara", length = 100)
    private String kategoriteLeJuara;

    @Column(name = "aktiv")
    private Boolean aktiv = true;

    @Column(name = "password", length = 255)
     private String password;

    @Column(name = "role", length = 50)
     private String role = "INSTRUKTOR";

    @OneToMany(mappedBy = "instruktori", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("instruktori")
    private List<OraTeories> oretTeorise;

    @OneToMany(mappedBy = "instruktori", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("instruktori")
    private List<OraPraktikes> oretPraktikes;

    @OneToMany(mappedBy = "instruktori", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("instruktori")
    private List<Orari> oraret;

    public Instruktori() {}

    public Long getInstruktorId() { return instruktorId; }
    public void setInstruktorId(Long instruktorId) { this.instruktorId = instruktorId; }
    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }
    public String getMbiemri() { return mbiemri; }
    public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }
    public String getTelefoni() { return telefoni; }
    public void setTelefoni(String telefoni) { this.telefoni = telefoni; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLicencaNr() { return licencaNr; }
    public void setLicencaNr(String licencaNr) { this.licencaNr = licencaNr; }
    public String getKategoriteLeJuara() { return kategoriteLeJuara; }
    public void setKategoriteLeJuara(String kategoriteLeJuara) { this.kategoriteLeJuara = kategoriteLeJuara; }
    public Boolean getAktiv() { return aktiv; }
    public void setAktiv(Boolean aktiv) { this.aktiv = aktiv; }
    public String getPassword() { return password; }
   public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<OraTeories> getOretTeorise() { return oretTeorise; }
    public void setOretTeorise(List<OraTeories> oretTeorise) { this.oretTeorise = oretTeorise; }
    public List<OraPraktikes> getOretPraktikes() { return oretPraktikes; }
    public void setOretPraktikes(List<OraPraktikes> oretPraktikes) { this.oretPraktikes = oretPraktikes; }
    public List<Orari> getOraret() { return oraret; }
    public void setOraret(List<Orari> oraret) { this.oraret = oraret; }
}