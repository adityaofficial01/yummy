package com.aadi.yummy.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name","di0zy5ggo",
                "api_key","928633359214895",
                "api_secret","udjXq37R0Y2zqv4CKhIvkXSBFdQ"

        ));
    }
}
