package cl.aiep.sumativa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reserva.
 */
@Entity
@Table(name = "reserva")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "medico")
    private Integer medico;

    @Column(name = "reserva")
    private Integer reserva;

    @Column(name = "paciente")
    private Integer paciente;

    @Column(name = "centro_salud")
    private Integer centroSalud;

    @Column(name = "fecha_hora")
    private LocalDate fechaHora;

    @Column(name = "estado")
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pacientes pacientes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "centrosSalud" }, allowSetters = true)
    private Medico fkMedico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "medico" }, allowSetters = true)
    private CentrosSalud centrosSalud;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reserva id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMedico() {
        return this.medico;
    }

    public Reserva medico(Integer medico) {
        this.setMedico(medico);
        return this;
    }

    public void setMedico(Integer medico) {
        this.medico = medico;
    }

    public Integer getReserva() {
        return this.reserva;
    }

    public Reserva reserva(Integer reserva) {
        this.setReserva(reserva);
        return this;
    }

    public void setReserva(Integer reserva) {
        this.reserva = reserva;
    }

    public Integer getPaciente() {
        return this.paciente;
    }

    public Reserva paciente(Integer paciente) {
        this.setPaciente(paciente);
        return this;
    }

    public void setPaciente(Integer paciente) {
        this.paciente = paciente;
    }

    public Integer getCentroSalud() {
        return this.centroSalud;
    }

    public Reserva centroSalud(Integer centroSalud) {
        this.setCentroSalud(centroSalud);
        return this;
    }

    public void setCentroSalud(Integer centroSalud) {
        this.centroSalud = centroSalud;
    }

    public LocalDate getFechaHora() {
        return this.fechaHora;
    }

    public Reserva fechaHora(LocalDate fechaHora) {
        this.setFechaHora(fechaHora);
        return this;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return this.estado;
    }

    public Reserva estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Pacientes getPacientes() {
        return this.pacientes;
    }

    public void setPacientes(Pacientes pacientes) {
        this.pacientes = pacientes;
    }

    public Reserva pacientes(Pacientes pacientes) {
        this.setPacientes(pacientes);
        return this;
    }

    public Medico getFkMedico() {
        return this.fkMedico;
    }

    public void setFkMedico(Medico medico) {
        this.fkMedico = medico;
    }

    public Reserva fkMedico(Medico medico) {
        this.setFkMedico(medico);
        return this;
    }

    public CentrosSalud getCentrosSalud() {
        return this.centrosSalud;
    }

    public void setCentrosSalud(CentrosSalud centrosSalud) {
        this.centrosSalud = centrosSalud;
    }

    public Reserva centrosSalud(CentrosSalud centrosSalud) {
        this.setCentrosSalud(centrosSalud);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reserva)) {
            return false;
        }
        return getId() != null && getId().equals(((Reserva) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reserva{" +
            "id=" + getId() +
            ", medico=" + getMedico() +
            ", reserva=" + getReserva() +
            ", paciente=" + getPaciente() +
            ", centroSalud=" + getCentroSalud() +
            ", fechaHora='" + getFechaHora() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
