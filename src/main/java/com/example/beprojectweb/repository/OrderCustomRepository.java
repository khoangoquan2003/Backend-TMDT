package com.example.beprojectweb.repository;

import com.example.beprojectweb.entity.OrderCustom;
import com.example.beprojectweb.entity.User;
import com.example.beprojectweb.enums.OrderCustomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderCustomRepository extends JpaRepository<OrderCustom, UUID> {
    List<OrderCustom> findByUser(User user);
    List<OrderCustom> findByUserAndStatus(User user, OrderCustomStatus status);

}
