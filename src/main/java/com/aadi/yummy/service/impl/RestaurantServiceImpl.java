package com.aadi.yummy.service.impl;

import com.aadi.yummy.dto.FileData;
import com.aadi.yummy.dto.RestaurantDto;
import com.aadi.yummy.entities.Restaurant;
import com.aadi.yummy.exception.ResourceNotFoundException;
import com.aadi.yummy.repository.RestaurantRepo;
import com.aadi.yummy.service.FileService;
import com.aadi.yummy.service.RestaurantService;
import com.aadi.yummy.utils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Beans;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Value("${restaurant.file.path}")
    private String bannerFolderPath;
    RestaurantRepo restaurantRepo;
    ModelMapper modelMapper;
    private FileService fileService;

    public RestaurantServiceImpl(RestaurantRepo restaurantRepo, ModelMapper modelMapper, FileService fileService) {
        this.restaurantRepo = restaurantRepo;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    }

    @Override
    public RestaurantDto save(RestaurantDto restaurantDto) {
        restaurantDto.setId(Helper.generateRandomId());
        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
        Restaurant savedRestaurant = restaurantRepo.save(restaurant);
        return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }


    @Override
    public RestaurantDto update(RestaurantDto restaurantDto, String id) {
        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(("Resaturant nto fount")));
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setOpen(restaurantDto.getOpen());
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setOpenTime(restaurantDto.getOpenTime());
        restaurant.setCloseTime(restaurantDto.getCloseTime());
        restaurant.setBanner(restaurantDto.getBanner());
        Restaurant updatedRestaurant = restaurantRepo.save(restaurant);
        return modelMapper.map(updatedRestaurant, RestaurantDto.class);

    }

    @Override
    public void delete(String id) {
        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(("Resaturant not fount")));
        restaurantRepo.delete(restaurant);
    }

    @Override
    public RestaurantDto getById(String id) {
        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(("Resaturant not fount")));
        return modelMapper.map(restaurant, RestaurantDto.class);
    }

    @Override
    public Page<RestaurantDto> getAll(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepo.findAll(pageable);
        return restaurantPage.map((rest -> modelMapper.map(rest, RestaurantDto.class)));
    }

    @Override
    public List<RestaurantDto> searchByName(String name) {
        return restaurantRepo.findByNameContainingIgnoreCase(name)
                .stream()
                .map((rest) -> modelMapper.map(rest, RestaurantDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<RestaurantDto> getOpenRestaurants(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepo.findByOpen(true, pageable);
        return restaurantPage.map((rast) -> modelMapper.map(rast, RestaurantDto.class));
    }

    @Override
    public Page<RestaurantDto> getOpenRestaurantByTiming(Pageable pageable) {
        return null;
    }

    @Override
    public RestaurantDto uploadBanner(MultipartFile banner, String restaurantId) {

        // 1. get restaurant
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // 2. upload to cloudinary
        String imageUrl = fileService.uploadFile(banner);

        // 3. set URL in banner field
        restaurant.setBanner(imageUrl);

        // 4. save updated restaurant
        Restaurant saved = restaurantRepo.save(restaurant);

        // 5. return dto
        return modelMapper.map(saved, RestaurantDto.class); // ya jo bhi mapping use kar rahe ho
    }


//    @Override
//    public RestaurantDto uploadBanner(MultipartFile file, String id) {}

        //upload file
//        String fileName = file.getOriginalFilename();
//        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
//        String newFIleName = new Date().getTime() + fileExtension;
//        FileData fileData = fileService.uploadFile(file, bannerFolderPath + newFIleName);
//
//        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(("Resaturant not fount")));
//        restaurant.setBanner(fileData.getFileName());
//        restaurantRepo.save(restaurant);
//
//        return modelMapper.map(restaurant,RestaurantDto.class);

//    }


// instead of these function we will use a library called model mapper
//    private Restaurant convertRestaurantDtoToRestaurant(RestaurantDto restaurantDto) {
//        Restaurant restaurant = new Restaurant();
//        BeanUtils.copyProperties(restaurantDto, restaurant);
//        return restaurant;
//    }
//
//    private RestaurantDto convertRestaurantToRestaurantDto(Restaurant restaurant) {
//        RestaurantDto restaurantDto = new RestaurantDto();
//        BeanUtils.copyProperties(restaurant,restaurantDto);
//        return restaurantDto;
//    }
    }
