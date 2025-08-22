package EdwinAlfaro_20210300.EdwinAlfaro_20210300.Repository;

import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Extencion del JPA al reppositorio para hacer uso de sus metodos
public interface LibroRepository extends JpaRepository<LibroEntity, Long> {
}
