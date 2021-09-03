package jaykye.finalproject;

import jaykye.finalproject.model.ResponseContentJson;
import jaykye.finalproject.model.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class FinalprojectApplication {

	private static final Logger log = LoggerFactory.getLogger(FinalprojectApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(FinalprojectApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


}
