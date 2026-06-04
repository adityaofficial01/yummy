package com.aadi.yummy.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "yummy_restaurant")
@Getter
@Setter
public class Restaurant {

    @Id
    private String id;
    private String name;
    @Lob
    private String description;
    private String banner;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    @Column(name = "is_open")
    private Boolean open = true;

    @ManyToOne
    private User user;
}
