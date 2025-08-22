package EdwinAlfaro_20210300.EdwinAlfaro_20210300.Service;

import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Entity.LibroEntity;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Exception.ExceptionLibroNoEncontrado;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.MODEL.DTO.LibroDTO;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Repository.LibroRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LibroService {
    //Inyeccion del repositorio
    @Autowired
    LibroRepository repo;

    //Metodo obtener todos los registros

    /***
     *
     * @return
     */
    public List<LibroDTO> Obtener(){
        List<LibroEntity> lista = repo.findAll();
        return lista.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    public LibroDTO ObtenerPorID(Long id) {
        LibroEntity entity = repo.findById(id)
                .orElseThrow(() -> new ExceptionLibroNoEncontrado("El libro con id: " + id + "no fue encontrado"));
        return ConvertirADTO(entity);
    }

    public LibroDTO Insertar(LibroDTO dto){
        if(dto == null || dto.getTitulo() == null || dto.getTitulo().isEmpty()){
            throw new IllegalArgumentException("El libro no tiene titulo");
        }
        try{
            LibroEntity entity = ConvertirAEntity(dto);
            LibroEntity guardado = repo.save(entity);
            return ConvertirADTO(guardado);
        }catch (Exception e){
            log.error(("El libro no fue encontrado" + e.getMessage()));
            throw new ExceptionLibroNoEncontrado("El libro no fue encontrado, intente de nuevo");
        }
    }

    public LibroDTO Actualizar(Long id, @Valid LibroDTO dto){
        LibroEntity existente = repo.findById(id).orElseThrow(()->new ExceptionLibroNoEncontrado("No se encnotro el libro"));
        existente.setTitulo(dto.getTitulo());
        existente.setIsbn(dto.getIsbn());
        existente.setAño_publicaion(dto.getAño_publicacion());
        existente.setGenero(dto.getGenero());
        existente.setId_autor(dto.getId_autor());
        LibroEntity Actualizado = repo.save(existente);
        return ConvertirADTO(Actualizado);
    }

    public boolean Remover(Long id){
        try {
            LibroEntity Existe = repo.findById(id).orElse(null);
            if(Existe != null){
                return true;
            } else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se pudo eliminar el libro con id: " + id + "", 1);
        }
    }

    private LibroDTO ConvertirADTO(LibroEntity entity){
        LibroDTO dto = new LibroDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setIsbn(entity.getIsbn());
        dto.setAño_publicacion(entity.getAño_publicaion());
        dto.setGenero(entity.getGenero());
        dto.setId_autor(entity.getId_autor());
        return dto;
    }

    private LibroEntity ConvertirAEntity(LibroDTO dto){
        LibroEntity entity = new LibroEntity();
        entity.setId(dto.getId());
        entity.setTitulo(dto.getTitulo());
        entity.setIsbn(dto.getIsbn());
        entity.setAño_publicaion(dto.getAño_publicacion());
        entity.setGenero(dto.getGenero());
        entity.setId_autor(dto.getId_autor());
        return entity;
    }
}