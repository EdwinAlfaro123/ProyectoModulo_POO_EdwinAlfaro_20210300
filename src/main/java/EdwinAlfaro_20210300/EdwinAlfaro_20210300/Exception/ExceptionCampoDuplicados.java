package EdwinAlfaro_20210300.EdwinAlfaro_20210300.Exception;

import lombok.Getter;

public class ExceptionCampoDuplicados extends RuntimeException {
    @Getter
    private String CampoDuplicado;

    public ExceptionCampoDuplicados(String message) {
        super(message);
    }

    //Si un campo sea duplicado mandar un error
    public ExceptionCampoDuplicados(String message, String CampoDuplicado){
        super(message);
        this.CampoDuplicado = CampoDuplicado;
    }
}
