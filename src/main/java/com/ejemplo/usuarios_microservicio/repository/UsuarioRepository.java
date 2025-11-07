package com.ejemplo.usuarios_microservicio.repository;

import com.ejemplo.usuarios_microservicio.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
