package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.Customer;

//// Create a new DAO interface named CustomerDao.
//The interface should extend JpaRepository. 

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
