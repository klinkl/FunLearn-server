package com.funlearn.server;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import com.funlearn.server.model.ModelUser;
import com.funlearn.server.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
@OpenAPIDefinition(
		info = @Info(
				title = "Funlearn Server API",
				version = "1.0",
				description = "API documentation for Funlearn Server"
		)
)
@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo (UserRepository userRepository){
		return (args -> {
			userRepository.save(new ModelUser(null,"User", 0, 0, null,1,25,0));
			List<ModelUser> users = userRepository.findAll();
			for (ModelUser user : users){
				System.out.println(user.toString());
			}
		});
	}


}
