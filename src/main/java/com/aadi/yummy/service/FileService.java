package com.aadi.yummy.service;

import com.aadi.yummy.dto.FileData;
import com.aadi.yummy.dto.RestaurantDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file);
    RestaurantDto uploadBanner(MultipartFile file, String restaurantId);
    void deleteFile(String path);
    Resource loadFile(String path);
}
