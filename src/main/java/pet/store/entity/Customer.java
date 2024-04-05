package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//Add the @Entity (jakarta.persistence) and @Data (lombok) class-level annotations.

	@Entity
	@Data
	public class Customer {
		
//Add the annotations @Id (jakarta.persistence) and @GeneratedValue (jakarta.persistence) to each ID field
//Add the relationship variables into each class		
//Add @EqualsAndHashCode.Exclude and @ToString.Exclude to all of the recursive relationship variables. This will prevent recursion from occurring when the .toString(), .equals(), or .hashCode() methods are called.		

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	private String customerFirstName;
	
	private String customerLastName;

	private String customerEmail;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<PetStore> petStores = new HashSet<>();
	
}
