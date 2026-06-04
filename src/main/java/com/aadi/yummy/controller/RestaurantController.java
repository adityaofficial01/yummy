package com.aadi.yummy.controller;

import com.aadi.yummy.dto.FileData;
import com.aadi.yummy.dto.RestaurantDto;
import com.aadi.yummy.service.FileService;
import com.aadi.yummy.service.RestaurantService;
import com.aadi.yummy.service.impl.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;
    @Autowired
    private FileUploadService fileUploadService;
    @Value(("${restaurant.file.path}"))
    private String bannerFolderPath;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    private Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    //create
    @PostMapping
    public ResponseEntity<RestaurantDto> create(@RequestBody RestaurantDto restaurantDto) {
        RestaurantDto savedRestaurant = restaurantService.save(restaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
    }

    //update
    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> update(@RequestBody RestaurantDto restaurantDto, @PathVariable String restaurantId) {
        RestaurantDto updatedRestaurant = restaurantService.update(restaurantDto, restaurantId);
        return ResponseEntity.ok(updatedRestaurant);
    }

    //get all restaurants
    @GetMapping
    public ResponseEntity<Page<RestaurantDto>> getAllRestaurants(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir
    ){
        // Validate sortBy field exists in entity (optional but good practice)
        List<String> allowedFields = Arrays.asList("name", "openTime", "closeTime", "address");
        if (!allowedFields.contains(sortBy)) {
            sortBy = "name"; // fallback to default
        }

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC :
                Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(restaurantService.getAll(pageable));
    }


    // find by name
//    @GetMapping("/search")
public ResponseEntity<List<RestaurantDto>> getAllByName(@RequestParam String name){
    List<RestaurantDto> restaurantDtosList = restaurantService.searchByName(name);
    return ResponseEntity.status(HttpStatus.OK).body(restaurantDtosList);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        restaurantService.delete(id);
 return ResponseEntity.noContent().build();
    }

    //upload banner api to local folder
    @PostMapping("/upload-banner/{restaurantId}")
    public ResponseEntity<?> uploadBanner(
            @RequestParam("banner") MultipartFile banner,
            @PathVariable String restaurantId
            ){
        RestaurantDto restaurantDto = restaurantService.uploadBanner(banner, restaurantId);
        return ResponseEntity.ok(restaurantDto);
    }

    // upload file to cloudnary
    @PostMapping("/fileUpload")
    public String uploadFileToCloudnary(@RequestParam("file") MultipartFile file){
        return fileUploadService.uploadFile(file).toString();
    }


    //api to serve or load banner image
    @GetMapping("/{restaurantId}/banner")
    public ResponseEntity<Resource> serveFile(@PathVariable String restaurantId) throws MalformedURLException, FileNotFoundException {
        RestaurantDto restaurantDto = restaurantService.getById(restaurantId);
        String fullPath = bannerFolderPath + restaurantDto.getBanner();
        Path filePath = Paths.get(fullPath);
        Resource resource = new UrlResource(filePath.toUri());
       if(resource.exists()){
           return ResponseEntity.ok()
//                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + restaurantDto.getBanner() + "\"")
                   .contentType(MediaType.IMAGE_PNG)
                   .body(resource);
       }
       else{
           throw new FileNotFoundException("File not found on this path");
       }
    }

}
