package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.order.AddressRequest;
import com.example.beprojectweb.entity.Address;
import com.example.beprojectweb.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Thêm địa chỉ mới cho user hiện tại
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressRequest addressRequest, Principal principal) {
        Address address = addressService.createAddress(addressRequest, principal);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    // Xóa địa chỉ theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") UUID addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // Lấy danh sách địa chỉ của user hiện tại
    @GetMapping("/mine")
    public ResponseEntity<List<Address>> getMyAddresses(Principal principal) {
        List<Address> addresses = addressService.getAddressesByUser(principal);
        return ResponseEntity.ok(addresses);
    }
}
