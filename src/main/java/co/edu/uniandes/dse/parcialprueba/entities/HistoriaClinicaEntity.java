package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class HistoriaClinicaEntity extends BaseEntity {
    private String diagnostico;
	private String tratamiento;
	private String fechaDeCreacion;

    @PodamExclude
	@ManyToOne
	private PacienteEntity paciente;

}
