package EdwinAlfaro_20210300.EdwinAlfaro_20210300.Service;

import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Entity.AutoresEntity;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Exception.ExceptionLibroNoEncontrado;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.MODEL.DTO.AutoresDTO;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Repository.AutoresRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AutoresService {
    @Autowired
    AutoresRepository repo;

    public List<AutoresDTO> Obtener(){
        List<AutoresEntity> lista = repo.findAll();
        return lista.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    public AutoresDTO ObtenerPorID(Long id) {
        AutoresEntity entity = repo.findById(id)
                .orElseThrow(() -> new ExceptionLibroNoEncontrado("El libro con id: " + id + "no fue encontrado"));
        return ConvertirADTO(entity);
    }

    public AutoresDTO Insertar(AutoresDTO dto){
        if(dto == null || dto.getTitulo() == null || dto.getTitulo().isEmpty()){
            throw new IllegalArgumentException("El libro no tiene titulo");
        }
        try{
            AutoresEntity entity = ConvertirAEntity(dto);
            AutoresEntity guardado = repo.save(entity);
            return ConvertirADTO(guardado);
        }catch (Exception e){
            log.error(("El libro no fue encontrado" + e.getMessage()));
            throw new ExceptionLibroNoEncontrado("El libro no fue encontrado, intente de nuevo");
        }
    }

    public AutoresDTO Actualizar(Long id, @Valid AutoresDTO dto){
        AutoresEntity existente = repo.findById(id).orElseThrow(()->new ExceptionLibroNoEncontrado("No se encnotro el libro"));
        existente.setTitulo(dto.getTitulo());
        existente.setIsbn(dto.getIsbn());
        existente.setAño_publicaion(dto.getAño_publicacion());
        existente.setGenero(dto.getGenero());
        existente.setId_autor(dto.getId_autor());
        AutoresEntity Actualizado = repo.save(existente);
        return ConvertirADTO(Actualizado);
    }

    public boolean Remover(Long id){
        try {
            AutoresEntity Existe = repo.findById(id).orElse(null);
            if(Existe != null){
                return true;
            } else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se pudo eliminar el libro con id: " + id + "", 1);
        }
    }

    private AutoresDTO ConvertirADTO(AutoresEntity entity){
        AutoresDTO dto = new AutoresDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setIsbn(entity.getIsbn());
        dto.setAño_publicacion(entity.getAño_publicaion());
        dto.setGenero(entity.getGenero());
        dto.setId_autor(entity.getId_autor());
        return dto;
    }

    private AutoresEntity ConvertirAEntity(AutoresDTO dto){
        AutoresEntity entity = new AutoresEntity();
        entity.setId(dto.getId());
        entity.setTitulo(dto.getTitulo());
        entity.setIsbn(dto.getIsbn());
        entity.setAño_publicaion(dto.getAño_publicacion());
        entity.setGenero(dto.getGenero());
        entity.setId_autor(dto.getId_autor());
        return entity;
    }
}