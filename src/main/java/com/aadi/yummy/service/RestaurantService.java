package com.aadi.yummy.service;

import com.aadi.yummy.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantService {

    //create
    RestaurantDto save(RestaurantDto restaurantDto);
    RestaurantDto update(RestaurantDto restaurantDto, String id);
    void delete(String id);
    RestaurantDto getById(String id);
    Page<RestaurantDto> getAll(Pageable pageable);
    List<RestaurantDto> searchByName(String name);
    Page<RestaurantDto> getOpenRestaurants(Pageable pageable);
    Page<RestaurantDto> getOpenRestaurantByTiming(Pageable pageable);
    RestaurantDto uploadBanner(MultipartFile file, String id);
}
