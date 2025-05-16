package org.finalproject.movierenting.dto;

import lombok.Data;


@Data
public class ConsumerDTO {
    private String email;
    private String firstName;
    private String lastName;
    private int bonusPoints;
}
