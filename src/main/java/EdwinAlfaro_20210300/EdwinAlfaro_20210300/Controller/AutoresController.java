package EdwinAlfaro_20210300.EdwinAlfaro_20210300.Controller;

import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Exception.ExceptionCampoDuplicados;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Exception.ExceptionLibroNoEncontrado;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.MODEL.DTO.AutoresDTO;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Service.AutoresService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoresController {
    @Autowired
    AutoresService service;

    @GetMapping("/GET")
    public List<AutoresDTO> Obtener(){
        return service.Obtener();
    }

    @GetMapping("/GET/{id}")
    public ResponseEntity<AutoresDTO> ObtenerPorID(@PathVariable Long id){
        try{
            AutoresDTO usuario = service.ObtenerPorID(id);
            return ResponseEntity.ok(usuario);
        }catch (ExceptionLibroNoEncontrado e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/POST")
    public ResponseEntity<?> Nuevo(@Valid @RequestBody AutoresDTO dto , HttpServletRequest request){
        try {
            AutoresDTO respuesta = service.Insertar(dto);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "inserccion fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Los datos no se insertaron"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", "respuesta"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", "Error no controlado",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/PUT/{id}")
    public ResponseEntity<?> Modificar(@PathVariable Long id, @Valid @RequestBody AutoresDTO json, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            AutoresDTO dto = service.Actualizar(id, json);
            return ResponseEntity.ok(dto);
        }catch (ExceptionLibroNoEncontrado e){
            return ResponseEntity.notFound().build();
        } catch (ExceptionCampoDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "Error", "Datos duplicados",
                    "Campo", e.getCampoDuplicado()
            ));
        }
    }

    @DeleteMapping("/DELETE/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id){
        try {
            if(!service.Remover(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("error", "Libro No encontrado")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensaje", "El libro No Encontrado",
                                "timestap", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "completo",
                    "message", "eliminado exitosamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar",
                    "detail", e.getMessage()
            ));
        }
    }
}
