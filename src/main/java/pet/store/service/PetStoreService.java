package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

//create a new class
//Add the class-level annotation 

@Service
public class PetStoreService {
	
	
//Add a PetStoreDao object named petStoreDao as a private instance variable. 
//Annotate the instance variable with so that Spring can inject the DAO object into the variable. 
	
	@Autowired
	private PetStoreDao petStoreDao;
	
//Inject the EmployeeDao object into the pet store service class using the @Autowired annotation.
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;
	
//fill in the save method in the service class	

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
	}

//This method takes a PetStore object and a PetStoreData object as parameters.	
	
	public void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
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

	public PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore;
		
		if(Objects.isNull(petStoreId)) {
			petStore = new PetStore();
			
		}
		else {
			petStore = findPetStoreById(petStoreId);
		}
		
		return petStore;
	}

	public PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("Pet Store with ID=" + petStoreId + " was not found."));
	}
	
	
	
//write the saveEmployee() method in the service class.
//Add the @Transactional annotation as a method-level annotation 	
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		
		 PetStore petStore = findPetStoreById(petStoreId);
		 Long employeeId = petStoreEmployee.getEmployeeId();
		 Employee employee = findOrCreateEmployee(petStoreId, employeeId);
		 
		 copyEmployeeFields(employee, petStoreEmployee);
		 employee.setPetStore(petStore);
		 petStore.getEmployees().add(employee);
		 
		 Employee dbEmployee = employeeDao.save(employee);
		 
		 return new PetStoreEmployee(dbEmployee);
		
		
	}
	
//Create a new method copyEmployeeFields().
	
	public void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
	}

//Create a new method findOrCreateEmployee().
	
	public Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		Employee employee;
		
		if(Objects.isNull(employeeId)) {
			 employee  = new Employee();
			
		}
		else {
			 employee  = findEmployeeById(petStoreId, employeeId);
		}
		
		return employee;
		
	}

//Create a method named findEmployeeById().
	
	public Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId 		+ " does not exist."));
			
			if(employee.getPetStore().getPetStoreId() == petStoreId) {
				
				return employee;
				
			} else {
				
				throw new IllegalArgumentException("Pet store with ID=" + petStoreId + " does not have an employee with ID=" + employeeId);
			}
	}
	
////write the saveCustomer() method in the service class.	

	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Customer customer = findOrCreateCustomer(petStoreId, petStoreCustomer.getCustomerId()); 
		copyCustomerFields(customer, petStoreCustomer);
		customer.getPetStores().add(petStore);
		petStore.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new PetStoreCustomer(dbCustomer);
	}

//Create a new method copyCustomerFields().
	
	public void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
		
		
	}
	
//Create a new method findOrCreateCustomer().	

	public Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
			Customer customer;
		
		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(petStoreId, customerId);
		}
		
		return customer;
	}

//Create a method named findEmployeeById().
	
	public Customer findCustomerById(Long petStoreId, Long customerId) {
		boolean IdsMatch = false;
		
		Customer customer = customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId 		+ " does not exist."));
		
		Set<PetStore> petStores = customer.getPetStores();
		for (PetStore petStore : petStores) {
			if (petStore.getPetStoreId() == petStoreId) {
				IdsMatch = true;
			}
		}
		
		if(IdsMatch) {
			return customer;
		} else {
			throw new IllegalArgumentException("Pet store with ID=" + petStoreId + " does not have a customer with ID=" + customerId);
		}
	}
	
	
//In the service class method retrieveAllPetStores():
//Add the @Transactional annotation.	
	@Transactional
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> results = new LinkedList<>();
		
		for (PetStore petStore : petStores) {
			PetStoreData petStoreData = new PetStoreData(petStore);
			
			petStoreData.getEmployees().clear();
			petStoreData.getCustomers().clear();
			
			results.add(petStoreData);
		}
		
		return results;
	}
	
//In the service class method, add an @Transactional annotation.
	
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return new PetStoreData(petStore);
}

//Call the deletePetStoreById() method in the service, passing the pet store ID as a parameter.
	
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	
}
}
