package org.finalproject.movierenting.dto;

import lombok.Data;
import org.finalproject.movierenting.enums.ConsumerPermission;


@Data
public class ConsumerDTO {
    private String email;
    private String firstName;
    private String lastName;
    private int bonusPoints;
    private ConsumerPermission permission;
}
