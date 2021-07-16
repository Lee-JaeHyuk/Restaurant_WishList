package com.example.restaurantwishlist.controller;

import com.example.restaurantwishlist.restaurant.dto.RestaurantDto;
import com.example.restaurantwishlist.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class ApiController {

    private final RestaurantService restaurantService;

    @GetMapping("/search")
    public RestaurantDto search(@RequestParam String query){
        return restaurantService.search(query);
    }

    @PostMapping("")
    public RestaurantDto add(@RequestBody RestaurantDto wishListDto){
        log.info("{}", wishListDto);
        return restaurantService.add(wishListDto);
    }

    @GetMapping("/all")
    public List<RestaurantDto> findAll(){
        return restaurantService.findAll();
    }

    @DeleteMapping("/{index}")
    public void delete(@PathVariable int index){
        restaurantService.delete(index);
    }

    @PostMapping("/{index}")
    public void addVisit(@PathVariable int index){
        restaurantService.addVisit(index);
    }



}
