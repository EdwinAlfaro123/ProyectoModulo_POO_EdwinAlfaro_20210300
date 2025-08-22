package EdwinAlfaro_20210300.EdwinAlfaro_20210300.Repository;

import EdwinAlfaro_20210300.EdwinAlfaro_20210300.Entity.AutoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoresRepository extends JpaRepository<AutoresEntity, Long> {
}
