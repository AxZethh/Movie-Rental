package org.finalproject.movierenting.controller;

import org.finalproject.movierenting.dto.RentRequestDTO;
import org.finalproject.movierenting.dto.RentalDTO;
import org.finalproject.movierenting.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("getRentals")
    public List<RentalDTO> getRentals() {
        return this.rentalService.getRentals();
    }


    @PostMapping
    public ResponseEntity<List<String>> saveRental(@RequestBody RentRequestDTO rentRequestDTO) {
        return rentalService.saveRental(rentRequestDTO);
    }

}
