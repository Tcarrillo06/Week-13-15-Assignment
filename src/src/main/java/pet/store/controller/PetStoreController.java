package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

//Create a controller class
//Add the following class-level annotations

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	
//Autowire (inject) the PetStoreService as an instance variable. 	
	
	@Autowired
	private PetStoreService petStoreService;
	
//Create a public method that maps an HTTP POST request to "/pet_store". 	
	
	@PostMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {	
		
		log.info("Creating Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
		
	}
	
//modify one of the pet store objects added in the prior step. This involves sending an HTTP PUT request to the running application	
	
	@PutMapping("/pet_store/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		
//Log the request.
		
		log.info("Updating Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
//you will create a method in the controller class that allows an employee to be added to a pet store.	
//The method should be annotated with @PostMapping and @ResponseStatus. 	

	@PostMapping("/pet_store/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee insertEmployee (@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
		
//Log the request.		
		
		log.info("Creating Employee {} for petstore with ID={}", petStoreId, petStoreEmployee);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
		
	}
	
///you will create a method in the controller class that allows an customer to be added to a pet store.	
//The method should be annotated with @PostMapping and @ResponseStatus. 	
	
	@PostMapping("/pet_store/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer insertCustomer (@PathVariable Long petStoreId, @RequestBody PetStoreCustomer petStoreCustomer) {
		
//Log the request.
		
		log.info("Creating Customer {} for petstore with ID={}", petStoreId, petStoreCustomer);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	
	}
	
//Add a method to the controller that returns List<PetStoreData>. Remember to add the @GetMapping annotation.
	
	@GetMapping()
	public List<PetStoreData> retrieveAllPetStores() {
		
//Log the request.		
		
		log.info("Retrieve all pet stores.");
		return petStoreService.retrieveAllPetStores();
	}
	
//add a controller method to retrieve a single pet store given the pet store ID. 	
	
	@GetMapping("/{petStoreId}")
	public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {

//Log the request.

		log.info("Retrieving pet store by ID={}", petStoreId);
		return petStoreService.retrievePetStoreById(petStoreId);
	}
	
//Add the deletePetStoreById() method in the controller.	
//It should take the pet store ID as a parameter (remember to use @PathVariable).	
//Add the @DeleteMapping annotation. This should specify that the pet store ID is part of the URI. 
	
	@DeleteMapping("/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
		
//Log the request.		

		log.info("Deleting pet store with ID={}", petStoreId);
		petStoreService.deletePetStoreById(petStoreId);
		
		return Map.of("message", "Deletion of pet store with ID=" + petStoreId 
				+ " was successful.");
	}
	
}



