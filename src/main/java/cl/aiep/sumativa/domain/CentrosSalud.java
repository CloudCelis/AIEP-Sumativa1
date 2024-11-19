package cl.aiep.sumativa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CentrosSalud.
 */
@Entity
@Table(name = "centros_salud")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentrosSalud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "centro_salud_id", nullable = false, unique = true)
    private Integer centroSaludID;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Size(max = 1)
    @Column(name = "vigente", length = 1)
    private String vigente;

    @JsonIgnoreProperties(value = { "centrosSalud" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "centrosSalud")
    private Medico medico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CentrosSalud id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCentroSaludID() {
        return this.centroSaludID;
    }

    public CentrosSalud centroSaludID(Integer centroSaludID) {
        this.setCentroSaludID(centroSaludID);
        return this;
    }

    public void setCentroSaludID(Integer centroSaludID) {
        this.centroSaludID = centroSaludID;
    }

    public String getNombre() {
        return this.nombre;
    }

    public CentrosSalud nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public CentrosSalud direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public CentrosSalud telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getVigente() {
        return this.vigente;
    }

    public CentrosSalud vigente(String vigente) {
        this.setVigente(vigente);
        return this;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public Medico getMedico() {
        return this.medico;
    }

    public void setMedico(Medico medico) {
        if (this.medico != null) {
            this.medico.setCentrosSalud(null);
        }
        if (medico != null) {
            medico.setCentrosSalud(this);
        }
        this.medico = medico;
    }

    public CentrosSalud medico(Medico medico) {
        this.setMedico(medico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentrosSalud)) {
            return false;
        }
        return getId() != null && getId().equals(((CentrosSalud) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentrosSalud{" +
            "id=" + getId() +
            ", centroSaludID=" + getCentroSaludID() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", vigente='" + getVigente() + "'" +
            "}";
    }
}
