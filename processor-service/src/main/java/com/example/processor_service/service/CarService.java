package com.example.processor_service.service;

import com.example.processor_service.model.CarRecordDto;
import com.example.processor_service.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CarService {

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Page<CarRecordDto> findByBrandYearAndRegion(
            String brand,
            Integer makeYearFrom,
            Integer makeYearTo,
            String region,
            Pageable pageable
    ) {

        return repository.findAllByBrandAndMakeYearBetweenAndRegAddr(brand, makeYearFrom, makeYearTo, region, pageable)
                .map(CarRecordDto::toDto);
    }


    public CarRecordDto findByVin(String vin) {
        return repository.findByVin(vin).map(CarRecordDto::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Car with VIN %s not found".formatted(vin)));
    }

    public CarRecordDto findByLicensePlate(String plateNumber) {
        return repository.findByLicenceNumber(plateNumber)
                .or(() -> repository.findByOldLicenceNumber(plateNumber))
                .map(CarRecordDto::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("No car with plate " + plateNumber));

    }

    public Page<CarRecordDto> findByOperLike(String oper, Pageable pageable) {
        return repository.findAllByOperLike(oper, pageable).map(CarRecordDto::toDto);
    }

    public Page<CarRecordDto> findByRegistrationDate(LocalDate from, LocalDate to, Pageable pageable) {
        return repository.findAllByRegistrationDateBetween(from, to, pageable).map(CarRecordDto::toDto);
    }

}
