package com.example.processor_service.repository;

import com.example.processor_service.entity.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CarRepository extends JpaRepository<CarEntity, Long> {

    Page<CarEntity> findAllByBrandAndMakeYearBetweenAndRegAddr(String brand, Integer makeYear, Integer makeYear2, String regAddr, Pageable pageable);

    Optional<CarEntity> findByVin(String vin);
    Optional<CarEntity> findByLicenceNumber(String nReg);
    Optional<CarEntity> findByOldLicenceNumber(String nRegOld);
    Page<CarEntity> findAllByOperLike(String oper, Pageable pageable);
    Page<CarEntity> findAllByRegistrationDateBetween(LocalDate registrationDateAfter, LocalDate registrationDateBefore, Pageable pageable);



}