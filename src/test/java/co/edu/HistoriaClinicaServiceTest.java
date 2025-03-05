package co.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.HistoriaClinicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.services.HistoriaClinicaService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(HistoriaClinicaService.class)
class HistoriaClinicaServiceTest {

    @Autowired
	private HistoriaClinicaService historiaClinicaService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<HistoriaClinicaEntity> historiaList = new ArrayList<>();
	private PacienteEntity pacienteEntity;

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from HistoriaClinicaEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PacienteEntity").executeUpdate();
	}

    private void insertData() {

		pacienteEntity = factory.manufacturePojo(PacienteEntity.class);
		entityManager.persist(pacienteEntity);
		
		for (int i = 0; i < 3; i++) {
			HistoriaClinicaEntity entity = factory.manufacturePojo(HistoriaClinicaEntity.class);
			entity.setPaciente(pacienteEntity);
			entityManager.persist(entity);
			historiaList.add(entity);
		}
		
		pacienteEntity.setHistoriasClinicas(historiaList);
	}

    @Test
	void testCreateHistoria() throws EntityNotFoundException {
		HistoriaClinicaEntity newEntity = factory.manufacturePojo(HistoriaClinicaEntity.class);
				
		HistoriaClinicaEntity result = historiaClinicaService.createHistoria(pacienteEntity.getId(), newEntity);
		assertNotNull(result);
		HistoriaClinicaEntity entity = entityManager.find(HistoriaClinicaEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getDiagnostico(), entity.getDiagnostico());
		assertEquals(newEntity.getFechaDeCreacion(), entity.getFechaDeCreacion());
        assertEquals(newEntity.getTratamiento(), entity.getTratamiento());
		assertEquals(newEntity.getPaciente(), entity.getPaciente());
	}

    @Test
	void testCreateHistoriaInvalidPaciente() {
		assertThrows(EntityNotFoundException.class, () -> {
			HistoriaClinicaEntity newEntity = factory.manufacturePojo(HistoriaClinicaEntity.class);
			historiaClinicaService.createHistoria(0L, newEntity);
		});
	}


}
