package com.example.processor_service.controller;

import com.example.processor_service.model.CarRecordDto;
import com.example.processor_service.service.CarService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/search")
    public Page<CarRecordDto> findCarsByBrandYearAndRegion(
            @RequestParam @NotBlank String brand,
            @RequestParam Integer makeYearFrom,
            @RequestParam Integer makeYearTo,
            @RequestParam @NotBlank String region,
            @PageableDefault(size = 20, sort = "makeYear") Pageable pageable
    ) {
        if (makeYearFrom > makeYearTo) {
            throw new IllegalArgumentException("makeYearFrom must be <= makeYearTo");
        }
        return carService.findByBrandYearAndRegion(
                brand,
                makeYearFrom,   
                makeYearTo,
                region,
                pageable
        );
    }

    @GetMapping("/vin")
    public CarRecordDto findByVin(@RequestParam @NotBlank String vin) {
        return carService.findByVin(vin);
    }

    @GetMapping("/number-plate")
    public CarRecordDto findByNumberPlate(@RequestParam @NotBlank String numberPlate) {
        return carService.findByLicensePlate(numberPlate);
    }

    @GetMapping("/oper")
    public Page<CarRecordDto> findByOper(@RequestParam @NotBlank String oper, @PageableDefault(size = 20, sort = "makeYear") Pageable pageable) {
        return carService.findByOperLike(oper, pageable);
    }

    @GetMapping("/registration-date")
    public Page<CarRecordDto> findByRegistrationDate(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to,
            @PageableDefault(size = 20, sort = "makeYear") Pageable pageable) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Registration dates range invalid");
        }
        return carService.findByRegistrationDate(from, to, pageable);
    }


}
