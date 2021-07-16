package com.example.restaurantwishlist.restaurant.repository;

import com.example.restaurantwishlist.db.MemoryDbRepositoryAbstract;
import com.example.restaurantwishlist.restaurant.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepository extends MemoryDbRepositoryAbstract<RestaurantEntity> {

}
