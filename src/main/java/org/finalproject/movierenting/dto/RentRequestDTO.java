package org.finalproject.movierenting.dto;

import lombok.Data;
import org.finalproject.movierenting.enums.PaymentType;

import java.util.List;
import java.util.UUID;

@Data
public class RentRequestDTO {
    private List<UUID> filmIds;
    private PaymentType paymentType;
}
