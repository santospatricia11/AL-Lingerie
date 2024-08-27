package back.ecommerce_AL_Lingerie.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "back.ecommerce_AL_Lingerie.back.repository")

public class ECommerceAlLingerieApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceAlLingerieApplication.class, args);
	}

}
