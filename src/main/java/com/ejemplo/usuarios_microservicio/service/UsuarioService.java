package com.ejemplo.usuarios_microservicio.service;

import com.ejemplo.usuarios_microservicio.entity.Usuario;
import com.ejemplo.usuarios_microservicio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    // ✅ Guardar uno o varios usuarios
    public List<Usuario> guardarUsuarios(List<Usuario> usuarios) {
        return usuarioRepository.saveAll(usuarios);
    }

    // ✅ Listar todos los usuarios
    public Iterable<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // ✅ Eliminar un usuario por su ID
    public void eliminarUsuarioPorId(String id) {
        usuarioRepository.deleteById(id);
    }

    // 🚨 Eliminar toda la base de datos MongoDB (usar con precaución)
    public void eliminarBaseDeDatos() {
        mongoTemplate.getDb().drop();
    }
}
