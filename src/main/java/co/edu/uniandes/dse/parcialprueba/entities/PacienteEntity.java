package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class PacienteEntity extends BaseEntity {
    private String nombre;
	private String correo;
	private String telefono;

    @PodamExclude
	@OneToMany(mappedBy = "paciente", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<HistoriaClinicaEntity> historiasClinicas = new ArrayList<>();

    @PodamExclude
	@OneToOne
	private PacienteEntity acudiente;

}
