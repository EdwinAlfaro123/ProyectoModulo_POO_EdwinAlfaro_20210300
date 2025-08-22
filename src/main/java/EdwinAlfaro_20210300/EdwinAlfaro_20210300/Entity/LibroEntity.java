package EdwinAlfaro_20210300.EdwinAlfaro_20210300.Entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
//Haciendo referencia a la tabla para probar la API
@Table(name = "LIBROS")
@Getter @Setter @EqualsAndHashCode
public class LibroEntity {
    @Id
    //Generacion del valor del ID por medio de una secuencia creada en la base de datos
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_libro")
    //Generador de la secuencia del ID
    @SequenceGenerator(name = "seq_libro", sequenceName = "seq_libro", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "AÑO_PUBLICACION")
    private Long año_publicaion;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "ID_AUTOR")
    private Long id_autor;
}
