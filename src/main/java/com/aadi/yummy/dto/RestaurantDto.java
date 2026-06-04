package com.aadi.yummy.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private String name;
    private String description;
    private String banner;
    private String address; 
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean open;

    private String ownerName = "Aditya Rajput";

}
