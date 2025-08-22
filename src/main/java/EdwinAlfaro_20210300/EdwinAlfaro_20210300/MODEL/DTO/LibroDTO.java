package EdwinAlfaro_20210300.EdwinAlfaro_20210300.MODEL.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @EqualsAndHashCode @ToString
public class LibroDTO {
    /**
     * Campos de la tabla libros
     */
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String isbn;

    @NotNull
    private Long año_publicacion;

    private String genero;

    private Long id_autor;
}
