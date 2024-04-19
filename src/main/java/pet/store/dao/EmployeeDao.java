package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.Employee;

// Create a new DAO interface named EmployeeDao.
//The interface should extend JpaRepository. 

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
