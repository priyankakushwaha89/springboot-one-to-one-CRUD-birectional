package com.project.springboot_one_to_one_CRUD_bi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer>{

	List<Address> findByCity(String city);

}
