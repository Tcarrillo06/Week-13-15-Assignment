package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

//create a new class
//Add the class-level annotation 

@Service
public class PetStoreService {
	
	
//Add a PetStoreDao object named petStoreDao as a private instance variable. 
//Annotate the instance variable with @Autowired so that Spring can inject the DAO object into the variable. 
	
	@Autowired
	private PetStoreDao petStoreDao;
	
//fill in the save method in the service class	

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
	}

//This method takes a PetStore object and a PetStoreData object as parameters.	
	
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		
		
	}
	
//This method returns a new PetStore object if the pet store ID is null.
//If not null, the method should call findPetStoreById, which returns a PetStore object if a pet store with matching ID exists in the database.
//If no matching pet store is found, the method should throw a NoSuchElementException with an appropriate message. 	

	private PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore;
		
		if(Objects.isNull(petStoreId)) {
			petStore = new PetStore();
			
		}
		else {
			petStore = findPetStoreById(petStoreId);
		}
		
		return petStore;
	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("Pet Store with ID=" + petStoreId + " was not found."));
	}
		
	
}
