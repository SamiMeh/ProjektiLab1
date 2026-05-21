package Autoshkolla.Autoshkolla.Model;



import java.time.LocalDate;
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
@Table(name = "Kandidatet")
public class Kandidati {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kandidat_id")
    private Long kandidatId;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "role", length = 50)
    private String role;
    @Column(name = "emri", nullable = false, length = 100)
    private String emri;

    @Column(name = "mbiemri", nullable = false, length = 100)
    private String mbiemri;

    @Column(name = "numri_personal", unique = true, nullable = false, length = 20)
    private String numriPersonal;

    @Column(name = "data_lindjes", nullable = false)
    private LocalDate dataLindjes;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "telefoni", length = 20)
    private String telefoni;

    @Column(name = "adresa", length = 255)
    private String adresa;

    @Column(name = "data_regjistrimit")
    private LocalDate dataRegjistrimit;

    @OneToMany(mappedBy = "kandidati", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Regjistrimi> regjistrimet;

    public Kandidati() {}
    public String getPassword() { return password; }
    public void setPassword(String v) { this.password = v; }
    public String getRole() { return role; }
public void setRole(String v) { this.role = v; }

    public Long getKandidatId() { return kandidatId; }
    public void setKandidatId(Long kandidatId) { this.kandidatId = kandidatId; }
    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }
    public String getMbiemri() { return mbiemri; }
    public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }
    public String getNumriPersonal() { return numriPersonal; }
    public void setNumriPersonal(String numriPersonal) { this.numriPersonal = numriPersonal; }
    public LocalDate getDataLindjes() { return dataLindjes; }
    public void setDataLindjes(LocalDate dataLindjes) { this.dataLindjes = dataLindjes; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefoni() { return telefoni; }
    public void setTelefoni(String telefoni) { this.telefoni = telefoni; }
    public String getAdresa() { return adresa; }
    public void setAdresa(String adresa) { this.adresa = adresa; }
    public LocalDate getDataRegjistrimit() { return dataRegjistrimit; }
    public void setDataRegjistrimit(LocalDate dataRegjistrimit) { this.dataRegjistrimit = dataRegjistrimit; }
    public List<Regjistrimi> getRegjistrimet() { return regjistrimet; }
    public void setRegjistrimet(List<Regjistrimi> regjistrimet) { this.regjistrimet = regjistrimet; }
}

