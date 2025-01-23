package org.example.libreriakamel;

import org.example.libreriakamel.DTO.Ejemplar;
import org.example.libreriakamel.DTO.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioImp {
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    public UsuarioImp(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario(){
        List<Usuario> lista = this.usuarioRepositorio.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioJson(@PathVariable int id){
        Usuario l = this.usuarioRepositorio.findById(id).get();
        return ResponseEntity.ok(l);
    }

    //POST --> INSERT
    @Transactional
    @PostMapping("/addUsuario")
    public ResponseEntity<Usuario> addUsuario(@Validated @RequestBody Usuario Usuario){
        System.out.println("Entra aqui");
        try {
            Usuario e = this.usuarioRepositorio.save(Usuario);
            return ResponseEntity.ok().body(e);
        } catch (Exception ex) {
            ex.printStackTrace();  // Esto te mostrará detalles del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //POST con Form normal, se trabajará con JSONs normalmente...
    @PostMapping(value = "/UsuarioForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Usuario> addUsuarioForm(
            @RequestParam String dni,
            @RequestParam String nombre,
            @RequestParam String gmail,
            @RequestParam String contraseña,
            @RequestParam String tipo,
            @RequestParam LocalDate penalizacionHasta)
    {
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setEmail(gmail);
        usuario.setPassword(contraseña);
        usuario.setTipo(tipo);
        usuario.setPenalizacionHasta(penalizacionHasta);
        this.usuarioRepositorio.save(usuario);
        return ResponseEntity.created(null).body(usuario);
    }

    //PUT --> UPDATE
    //falta actualizar ficheros
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario, @PathVariable int id) {
        // Buscar el Usuario por ID
        Optional<Usuario> UsuarioExistenteOpt = usuarioRepositorio.findById(id);
        if (UsuarioExistenteOpt.isPresent()) {
            Usuario UsuarioExistente = UsuarioExistenteOpt.get();

            // Actualizar los campos del Usuario existente

            UsuarioExistente.setNombre(usuario.getNombre());
            UsuarioExistente.setEmail(usuario.getEmail());
            UsuarioExistente.setPassword(usuario.getPassword());
            UsuarioExistente.setTipo(usuario.getTipo());
            UsuarioExistente.setDni(usuario.getDni());
            UsuarioExistente.setPenalizacionHasta(usuario.getPenalizacionHasta());

            // Si hay otros campos en Usuario, agrégales actualizaciones aquí

            // Guardar el Usuario actualizado
            Usuario UsuarioPersistido = usuarioRepositorio.save(UsuarioExistente);

            // Devolver el Usuario actualizado
            return ResponseEntity.ok().body(UsuarioPersistido);
        } else {
            // Si el Usuario no se encuentra, devolver un 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable int id){
        usuarioRepositorio.deleteById(id);
        String mensaje = "Usuario con id: "+id+" borrado";
        return ResponseEntity.ok().body(mensaje);
    }



}
