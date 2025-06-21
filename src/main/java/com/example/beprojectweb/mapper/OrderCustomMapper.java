package com.example.beprojectweb.mapper;

import com.example.beprojectweb.dto.response.order.OrderCustomResponse;
import com.example.beprojectweb.entity.OrderCustom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderCustomMapper {

    @Mapping(target = "designFileUrls", expression = "java(splitUrls(order.getDesignFileUrl()))")
    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    OrderCustomResponse toOrderResponse(OrderCustom order);

    default List<String> splitUrls(String csv) {
        if (csv == null || csv.isEmpty()) return new ArrayList<>();
        return Arrays.asList(csv.split(","));
    }

}
