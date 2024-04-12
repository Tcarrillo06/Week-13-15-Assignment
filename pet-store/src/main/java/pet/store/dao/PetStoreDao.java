package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.PetStore;

//create the data layer interface. This interface extends JpaRepository,

public interface PetStoreDao extends JpaRepository<PetStore, Long> {

}
