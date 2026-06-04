package com.aadi.yummy.service.impl;

import com.aadi.yummy.dto.FileData;
import com.aadi.yummy.dto.RestaurantDto;
import com.aadi.yummy.entities.Restaurant;
import com.aadi.yummy.exception.InvalidFilePathException;
import com.aadi.yummy.repository.RestaurantRepo;
import com.aadi.yummy.service.FileService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
public class FileUploadService implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileUploadService.class);
    @Autowired
    private Cloudinary cloudinary;

    private FileUploadService fileUploadService;
    private RestaurantRepo  restaurantRepository;
    ModelMapper modelMapper;

    @Override
    public String uploadFile(MultipartFile file) {
        // Cloudnary implementation
        try{
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            e.printStackTrace(); // ADD THIS
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }



        // code for uploading to folder in project media upload to local folder
//        if(path.isBlank()){
//            throw  new InvalidFilePathException("Invalid path");
//        }
//        Path folderPath = Paths.get(path.substring(0, path.lastIndexOf('/') + 1));
//        logger.info(folderPath.toString());
//
//        // if the folder don't exist then create the folder else not
//        if(!Files.exists(folderPath)){
//            try {
//                Files.createDirectories(folderPath);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        String contentType = file.getContentType();
//        if(contentType.equals("image/jpeg") ||  contentType.equals("image/png") || contentType.equals("image/gif")) {
//
//        }
//        else{
//            throw new InvalidFilePathException("Invalid file content");
//        }
//        Path filePath = Paths.get(path);
//        try {
//            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
//            String fileName = path.substring(path.lastIndexOf('/') + 1);
//            FileData fileData = new FileData(fileName, path);
//            return fileData;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public RestaurantDto uploadBanner(MultipartFile banner, String restaurantId) {

        // 1. get restaurant
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // 2. upload to cloudinary
        String imageUrl = fileUploadService.uploadFile(banner);

        // 3. set URL in banner field
        restaurant.setBanner(imageUrl);

        // 4. save updated restaurant
        Restaurant saved = restaurantRepository.save(restaurant);

        // 5. return dto
        return modelMapper.map(saved, RestaurantDto.class);
    }



    @Override
    public void deleteFile(String path) {

    }

    @Override
    public Resource loadFile(String path) {
        return null;
    }
}
