package cl.aiep.sumativa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pacientes.
 */
@Entity
@Table(name = "pacientes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pacientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "paciente_id", nullable = false, unique = true)
    private Integer pacienteId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "rut")
    private String rut;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "genero")
    private String genero;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "direccion")
    private String direccion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pacientes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPacienteId() {
        return this.pacienteId;
    }

    public Pacientes pacienteId(Integer pacienteId) {
        this.setPacienteId(pacienteId);
        return this;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Pacientes nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public Pacientes apellidoPaterno(String apellidoPaterno) {
        this.setApellidoPaterno(apellidoPaterno);
        return this;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    public Pacientes apellidoMaterno(String apellidoMaterno) {
        this.setApellidoMaterno(apellidoMaterno);
        return this;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getRut() {
        return this.rut;
    }

    public Pacientes rut(String rut) {
        this.setRut(rut);
        return this;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public Pacientes fechaNacimiento(LocalDate fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return this.genero;
    }

    public Pacientes genero(String genero) {
        this.setGenero(genero);
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Pacientes telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public Pacientes email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Pacientes direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pacientes)) {
            return false;
        }
        return getId() != null && getId().equals(((Pacientes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pacientes{" +
            "id=" + getId() +
            ", pacienteId=" + getPacienteId() +
            ", nombre='" + getNombre() + "'" +
            ", apellidoPaterno='" + getApellidoPaterno() + "'" +
            ", apellidoMaterno='" + getApellidoMaterno() + "'" +
            ", rut='" + getRut() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", genero='" + getGenero() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
