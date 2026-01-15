package com.funlearn.server;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import com.funlearn.server.model.ModelUser;
import com.funlearn.server.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@OpenAPIDefinition(
		info = @Info(
				title = "Funlearn Server API",
				version = "1.0",
				description = "API documentation for Funlearn Server"
		)
)
@SpringBootApplication
@EnableScheduling
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo (UserRepository userRepository){
		return (args -> {
			List<UUID> friends = new ArrayList<>();
			UUID uuid = UUID.randomUUID();
			userRepository.save(new ModelUser(uuid,"User", 0, 0, null,1,25,0,friends));
			friends.add(uuid);
			userRepository.save(new ModelUser(UUID.randomUUID(),"User", 0, 0, null,1,25,0,friends));

			List<ModelUser> users = userRepository.findAll();
			for (ModelUser user : users){
				System.out.println(user.toString());
			}
		});
	}


}
