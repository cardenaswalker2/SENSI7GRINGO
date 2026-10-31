package com.sensi7gringo.repositories;

import com.sensi7gringo.models.Sensibilidad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensibilidadRepository extends MongoRepository<Sensibilidad, String> {
    List<Sensibilidad> findBySessionIdOrderByFechaCreacionDesc(String sessionId);
    List<Sensibilidad> findTop20ByOrderByPuntuacionPromedioDescValoracionesDesc();
    List<Sensibilidad> findBySessionIdAndFavoritoTrue(String sessionId);
}
