package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Create the class with a main() method.
//Add the @SpringBootApplication (org.springframework.boot.autoconfigure) annotation.

	@SpringBootApplication
	public class PetStoreApplication {
	
//Start Spring Boot from the main() method 
//n this step you will create the application configuration. This allows the application to login to the database and gives instructions to JPA. 	

	public static void main(String[] args) {
		SpringApplication.run(PetStoreApplication.class, args);

	}

}
