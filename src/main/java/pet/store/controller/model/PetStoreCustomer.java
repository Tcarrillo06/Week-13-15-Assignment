package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;

//new class in this package
//Add the @Data and @NoArgsConstructor as class-level annotations. 

@Data
@NoArgsConstructor
public class PetStoreCustomer {
	
//Copy the fields from the Customer entity into this class. Do not copy the petStores field, which will remove the recursion required by JPA. 
	
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
//add a constructor that takes a Customer object.	

	PetStoreCustomer(Customer customer) {
		customerId = customer.getCustomerId();
		customerFirstName = customer.getCustomerFirstName();
		customerLastName = customer.getCustomerLastName();
		customerEmail = customer.getCustomerEmail();
	}
	
}
