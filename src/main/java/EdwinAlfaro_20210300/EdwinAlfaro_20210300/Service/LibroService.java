package EdwinAlfaro_20210300.EdwinAlfaro_20210300.Service;

import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Entity.LibroEntity;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Exception.ExceptionLibroNoEncontrado;
import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Exception.ExceptionLibroNoRegistrado;
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

    /***
     *Metodo para obtener todos los registros previamente insertados en la base de datos por medio haciendo uso del Entity y conviertiendolos a tipo DTO
     * @return
     */
    public List<LibroDTO> Obtener(){
        List<LibroEntity> lista = repo.findAll();
        return lista.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    /***
     *Metodo para obtener en base el ID el registro previamente insertado en la base de datos por medio del Entity y conviertiendolos a tipo DTO
     * @return
     */
    public LibroDTO ObtenerPorID(Long id) {
        LibroEntity entity = repo.findById(id)
                //Generar un error al no encontrar el Libro en base al ID y retornando el Entity
                .orElseThrow(() -> new ExceptionLibroNoEncontrado("El libro con id: " + id + "no fue encontrado"));
        return ConvertirADTO(entity);
    }

    /***
     *Metodo para Insertar haciendo eso del Entity guardando en una variable llamada "guardado"
     * @return
     */
    public LibroDTO Insertar(LibroDTO dto){
        //Si al momento de ingresar el titulo es nulo o esta vacio mandara un mensaje de error
        if(dto == null || dto.getTitulo() == null || dto.getTitulo().isEmpty()){
            throw new IllegalArgumentException("El libro no tiene titulo");
        }
        //Si al insertar se guarda el registro de forma correcta retornar la variable "guardado" convertida a DTO
        try{
            LibroEntity entity = ConvertirAEntity(dto);
            LibroEntity guardado = repo.save(entity);
            return ConvertirADTO(guardado);
        }catch (Exception e){
            log.error(("El libro no fue registrado" + e.getMessage()));
            throw new ExceptionLibroNoRegistrado("El libro no fue registrado, intente de nuevo.");
        }
    }

    //Metodo actualizar en base al ID y validando con uso del DTO
    public LibroDTO Actualizar(Long id, @Valid LibroDTO dto){
        //Buscar el id y en caso de no encontrar mandar un mensaje de error y actualizar todos los datos menos el ID
        LibroEntity existente = repo.findById(id).orElseThrow(()->new ExceptionLibroNoEncontrado("No se encontro el libro"));
        existente.setTitulo(dto.getTitulo());
        existente.setIsbn(dto.getIsbn());
        existente.setAño_publicaion(dto.getAño_publicacion());
        existente.setGenero(dto.getGenero());
        existente.setId_autor(dto.getId_autor());
        LibroEntity Actualizado = repo.save(existente);
        return ConvertirADTO(Actualizado);
    }

    //Metodo para eliminar segun el ID
    public boolean Remover(Long id){
        //Verificar si el ID existe y guardalo en la variable "Existe"
        try {
            LibroEntity Existe = repo.findById(id).orElse(null);
            //Si el ID existe se mandara un true y en caso contrario un false
            if(Existe != null){
                return true;
            } else{
                return false;
            }
            //en caso de la variable "Existe" ser nula mandar un mensaje
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