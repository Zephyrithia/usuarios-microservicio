package com.ejemplo.usuarios_microservicio.controller;

import com.ejemplo.usuarios_microservicio.entity.Usuario;
import com.ejemplo.usuarios_microservicio.service.UsuarioService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private final ObjectMapper mapper = new ObjectMapper();

    // ✅ Guardar uno o varios usuarios
    @PostMapping
    public ResponseEntity<?> guardarUnoOVarios(@RequestBody JsonNode body) {
        try {
            List<Usuario> toSave = new ArrayList<>();

            if (body.isArray()) {
                for (JsonNode node : body) {
                    Usuario u = mapper.convertValue(node, Usuario.class);
                    toSave.add(u);
                }
            } else if (body.isObject()) {
                Usuario u = mapper.convertValue(body, Usuario.class);
                toSave.add(u);
            } else {
                return ResponseEntity.badRequest().body("Cuerpo inválido: envía un objeto o una lista de objetos JSON");
            }

            List<Usuario> saved = usuarioService.guardarUsuarios(toSave);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    // ✅ Listar todos los usuarios
    @GetMapping
    public ResponseEntity<Iterable<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // ✅ Eliminar un usuario por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable String id) {
        try {
            usuarioService.eliminarUsuarioPorId(id);
            return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al eliminar, Usuario no encontrado: " + e.getMessage());
        }
    }

    // 🚨 Eliminar toda la base de datos (usar con precaución)
    @DeleteMapping("/eliminarBase")
    public ResponseEntity<String> eliminarBaseDeDatos(@RequestParam(required = false) String confirmar) {
        try {
            if (!"SI".equalsIgnoreCase(confirmar)) {
                return ResponseEntity.badRequest().body("Para eliminar la base de datos, usa el parámetro ?confirmar=SI");
            }

            usuarioService.eliminarBaseDeDatos();
            return ResponseEntity.ok("Base de datos eliminada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al eliminar la base de datos: " + e.getMessage());
        }
    }
}
