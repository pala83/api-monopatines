package microservicio.asistente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
	DataSourceAutoConfiguration.class,
	HibernateJpaAutoConfiguration.class
})
public class MvcAsistenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcAsistenteApplication.class, args);
	}

}
