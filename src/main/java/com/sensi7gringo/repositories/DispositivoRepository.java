package com.sensi7gringo.repositories;

import com.sensi7gringo.models.Dispositivo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DispositivoRepository extends MongoRepository<Dispositivo, String> {
    List<Dispositivo> findBySistemaOperativoIgnoreCaseAndDisponibleTrue(String sistemaOperativo);
    List<Dispositivo> findByMarcaIgnoreCaseAndDisponibleTrue(String marca);
    Optional<Dispositivo> findByMarcaIgnoreCaseAndModeloIgnoreCase(String marca, String modelo);
}
