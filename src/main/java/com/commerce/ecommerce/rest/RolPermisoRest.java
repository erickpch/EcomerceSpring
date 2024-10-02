package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.DevolucionDTO;
import com.commerce.ecommerce.DTO.RolPermisoDTO;
import com.commerce.ecommerce.model.*;
import com.commerce.ecommerce.service.PermisoService;
import com.commerce.ecommerce.service.RolPermisoService;
import com.commerce.ecommerce.service.RolService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.commerce.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rol_permiso")
public class RolPermisoRest {
    @Autowired
    private RolPermisoService rolPermisoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PermisoService permisoService;

    @PostMapping(path = "/crear")
    public ResponseEntity<List<RolPermiso>> store(@RequestBody List<RolPermisoDTO> lista){
        try {

            List<RolPermiso> enviado = new ArrayList<>();
            lista.forEach(c->{
                Rol id_rol = null;
                try {
                    id_rol = rolService.findById(c.getId_rol()).orElseThrow(() -> new Exception("no encontrado"));
                    Permiso id_permiso = permisoService.findById(c.getId_permiso()).orElseThrow(() -> new Exception("no encontrado"));
                    Optional<RolPermiso> existing = rolPermisoService.findByIdRolAndIdPermiso(c.getId_permiso(), c.getId_rol());
                    if (!existing.isPresent()) {
                        RolPermiso nuevo = new RolPermiso();
                        nuevo.setId_rol(id_rol);
                        nuevo.setId_permiso(id_permiso);
                        nuevo = rolPermisoService.save(nuevo);
                        enviado.add(nuevo);
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            );

            return ResponseEntity.ok(enviado);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<List<Permiso>> getAsistencia(@PathVariable Long id){
        try{
            Usuario usuario = usuarioService.findById(id).orElseThrow(() -> new Exception("no encontrado"));
            Rol rol = usuario.getId_rol();
            List<Permiso> seleccionados = new ArrayList<>();
            List<RolPermiso> todos = rolPermisoService.findAll();
            todos.forEach(c->{
                if(c.getId_rol()==rol) {
                    seleccionados.add(c.getId_permiso());
                }
            });
            return ResponseEntity.ok(seleccionados);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}
