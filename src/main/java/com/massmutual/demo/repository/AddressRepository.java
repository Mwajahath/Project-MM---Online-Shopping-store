package com.massmutual.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.massmutual.demo.entity.Address;

import javax.transaction.Transactional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `sprint`.`address` WHERE (`user_id`=:id)", nativeQuery = true)
    int deleteAddressByAddressID(@Param("id") long id);

}
