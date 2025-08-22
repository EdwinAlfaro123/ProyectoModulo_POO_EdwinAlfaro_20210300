package EdwinAlfaro_20210300.EdwinAlfaro_20210300;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoModuloEdwinAlfaro20210300Application {

	public static void main(String[] args) {
		//Enviar datos del .env al applicaation.propierties
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(dotenvEntry ->
				System.setProperty(dotenvEntry.getKey(), dotenvEntry.getValue()));

		SpringApplication.run(ProyectoModuloEdwinAlfaro20210300Application.class, args);
	}

}
