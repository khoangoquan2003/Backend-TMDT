package com.example.beprojectweb.service;


import com.example.beprojectweb.dto.request.order.AddressRequest;
import com.example.beprojectweb.entity.Address;
import com.example.beprojectweb.entity.User;
import com.example.beprojectweb.repository.AddressRepository;
import com.example.beprojectweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@
        Service
public class AddressService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;


    public Address createAddress(AddressRequest addressRequest, Principal principal){
        User user= (User) userDetailsService.loadUserByUsername(principal.getName());
        Address address = Address.builder()
                .name(addressRequest.getName())
                .street(addressRequest.getStreet())
                .city(addressRequest.getCity())
                .user(user)
                .build();
        return addressRepository.save(address);
    }
    public void deleteAddress(UUID id) {
        addressRepository.deleteById(id);
    }

    public List<Address> getAddressesByUser(Principal principal) {
        if (principal == null) throw new RuntimeException("User not authenticated");
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Lấy danh sách Address của user
        return addressRepository.findAllByUser(user);
    }

    public Address getAddressById(UUID addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ với ID: " + addressId));
    }

}

