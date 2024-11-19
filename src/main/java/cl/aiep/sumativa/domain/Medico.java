package cl.aiep.sumativa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Medico.
 */
@Entity
@Table(name = "medico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Medico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "medico_id", nullable = false, unique = true)
    private Integer medicoId;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "especialidad")
    private String especialidad;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "correo", nullable = false)
    private String correo;

    @NotNull
    @Column(name = "centro_salud_id", nullable = false)
    private Integer centroSaludId;

    @JsonIgnoreProperties(value = { "medico" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private CentrosSalud centrosSalud;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Medico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMedicoId() {
        return this.medicoId;
    }

    public Medico medicoId(Integer medicoId) {
        this.setMedicoId(medicoId);
        return this;
    }

    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Medico nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public Medico apellidoPaterno(String apellidoPaterno) {
        this.setApellidoPaterno(apellidoPaterno);
        return this;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    public Medico apellidoMaterno(String apellidoMaterno) {
        this.setApellidoMaterno(apellidoMaterno);
        return this;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public Medico especialidad(String especialidad) {
        this.setEspecialidad(especialidad);
        return this;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Medico telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public Medico correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getCentroSaludId() {
        return this.centroSaludId;
    }

    public Medico centroSaludId(Integer centroSaludId) {
        this.setCentroSaludId(centroSaludId);
        return this;
    }

    public void setCentroSaludId(Integer centroSaludId) {
        this.centroSaludId = centroSaludId;
    }

    public CentrosSalud getCentrosSalud() {
        return this.centrosSalud;
    }

    public void setCentrosSalud(CentrosSalud centrosSalud) {
        this.centrosSalud = centrosSalud;
    }

    public Medico centrosSalud(CentrosSalud centrosSalud) {
        this.setCentrosSalud(centrosSalud);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medico)) {
            return false;
        }
        return getId() != null && getId().equals(((Medico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medico{" +
            "id=" + getId() +
            ", medicoId=" + getMedicoId() +
            ", nombre='" + getNombre() + "'" +
            ", apellidoPaterno='" + getApellidoPaterno() + "'" +
            ", apellidoMaterno='" + getApellidoMaterno() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", centroSaludId=" + getCentroSaludId() +
            "}";
    }
}
