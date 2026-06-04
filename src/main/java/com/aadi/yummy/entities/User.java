package com.aadi.yummy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "yummy_users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String address;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, CUSTOMER, DELIVERY_BOY
    private boolean isAvailable = true; // will use only for delivery boy to check is available to assign delivery

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurant> restaurants =  new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roleEntity = new ArrayList<>();


}
