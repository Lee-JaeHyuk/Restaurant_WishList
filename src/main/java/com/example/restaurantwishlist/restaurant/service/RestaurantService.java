package com.example.restaurantwishlist.restaurant.service;

import com.example.restaurantwishlist.naver.NaverClient;
import com.example.restaurantwishlist.naver.dto.SearchImageReq;
import com.example.restaurantwishlist.naver.dto.SearchLocalReq;
import com.example.restaurantwishlist.restaurant.dto.RestaurantDto;
import com.example.restaurantwishlist.restaurant.entity.RestaurantEntity;
import com.example.restaurantwishlist.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final NaverClient naverClient;
    private final RestaurantRepository restaurantRepository;

    public RestaurantDto search(String query) {
        // 지역 검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);
        var searchLocalRes = naverClient.searchLocal(searchLocalReq);

        if (searchLocalRes.getTotal() > 0) {
            var localItem = searchLocalRes.getItems().stream().findFirst().get();
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>", "");
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);

            // 이미지 검색
            var searchImageRes = naverClient.searchImage(searchImageReq);

            if (searchImageRes.getTotal() > 0) {
                var imageItem = searchImageRes.getItems().stream().findFirst().get();

                // 결과를 리턴

                var result = new RestaurantDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());
                return result;
            }
        }
        return new RestaurantDto();
    }

    public RestaurantDto add(RestaurantDto restaurantDto){
        var entity = dtoToEntity(restaurantDto);
        var saveEntity = restaurantRepository.save(entity);
        return entityToDto(saveEntity);
    }

    public void delete(int id){
        restaurantRepository.deleteById(id);
    }

    public List<RestaurantDto> findAll(){
        return restaurantRepository.findAll()
                .stream()
                .map(it->entityToDto(it))
                .collect(Collectors.toList());
    }

    public void addVisit(int id){
        var wishlist = restaurantRepository.findById(id);
        if(wishlist.isPresent()){
            var wish = wishlist.get();
            wish.setVisit(true);
            wish.setVisitCount(wish.getVisitCount()+1);
        }

    }

    private RestaurantEntity dtoToEntity(RestaurantDto restaurantDto){
        var entity = new RestaurantEntity();
        entity.setIndex(restaurantDto.getIndex());
        entity.setTitle(restaurantDto.getTitle());
        entity.setCategory(restaurantDto.getCategory());
        entity.setAddress(restaurantDto.getAddress());
        entity.setRoadAddress(restaurantDto.getRoadAddress());
        entity.setHomePageLink(restaurantDto.getHomePageLink());
        entity.setImageLink(restaurantDto.getImageLink());
        entity.setVisit(restaurantDto.isVisit());
        entity.setVisitCount(restaurantDto.getVisitCount());
        entity.setLastVisitDate(restaurantDto.getLastVisitDate());
        return entity;

    }

    private RestaurantDto entityToDto(RestaurantEntity restaurantEntity){
        var dto = new RestaurantDto();
        dto.setIndex(restaurantEntity.getIndex());
        dto.setTitle(restaurantEntity.getTitle());
        dto.setCategory(restaurantEntity.getCategory());
        dto.setAddress(restaurantEntity.getAddress());
        dto.setRoadAddress(restaurantEntity.getRoadAddress());
        dto.setHomePageLink(restaurantEntity.getHomePageLink());
        dto.setImageLink(restaurantEntity.getImageLink());
        dto.setVisit(restaurantEntity.isVisit());
        dto.setVisitCount(restaurantEntity.getVisitCount());
        dto.setLastVisitDate(restaurantEntity.getLastVisitDate());
        return dto;

    }

}
