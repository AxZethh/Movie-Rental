package org.finalproject.movierenting.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.finalproject.movierenting.enums.ConsumerPermission;


import java.util.UUID;

@Entity
@Data
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private ConsumerPermission permission;
    private int bonusPoints;
}
git