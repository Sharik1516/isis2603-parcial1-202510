package co.edu.uniandes.dse.parcialprueba.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialprueba.entities.HistoriaClinicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.HistoriaClinicaRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HistoriaClinicaService {

    @Autowired
	HistoriaClinicaRepository historiaClinicaRepository;

	@Autowired
	PacienteRepository pacienteRepository;

    @Transactional
	public HistoriaClinicaEntity createHistoria(Long pacienteId, HistoriaClinicaEntity historiaClinicaEntity) throws EntityNotFoundException {
		log.info("Inicia proceso de crear una historia");
		Optional<PacienteEntity> pacienteEntity = pacienteRepository.findById(pacienteId);
		if (pacienteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el paciente");

		historiaClinicaEntity.setPaciente(pacienteEntity.get());

		log.info("Termina proceso de creación de la historia");
		return historiaClinicaRepository.save(historiaClinicaEntity);
	}

}
