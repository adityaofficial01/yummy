package com.aadi.yummy.repository;

import com.aadi.yummy.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface    RestaurantRepo extends JpaRepository<Restaurant,String> {
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    Page<Restaurant> findByOpen(boolean open, Pageable pageable);
}
