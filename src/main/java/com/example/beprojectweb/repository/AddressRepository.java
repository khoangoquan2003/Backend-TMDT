package com.example.beprojectweb.repository;


import com.example.beprojectweb.entity.Address;
import com.example.beprojectweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findAllByUser(User user);
}


