package com.example.processor_service.service;

import com.example.processor_service.entity.CarEntity;
import com.example.processor_service.model.CarRecordDto;
import com.example.processor_service.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository repository;

    @InjectMocks
    private CarService service;

    private CarEntity createCarEntity(String vin, String nReg) {
        CarEntity entity = new CarEntity();
        entity.setVin(vin);
        entity.setLicenceNumber(nReg);
        entity.setMakeYear(2020);
        entity.setBrand("Toyota");
        entity.setRegAddr("Kyiv");
        entity.setOper("Operator1");
        entity.setExtractionDate(LocalDate.now());
        return entity;
    }

    @Test
    void findByBrandYearAndRegion_returnsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        CarEntity entity = createCarEntity("VIN123", "AA1234BB");
        Page<CarEntity> page = new PageImpl<>(List.of(entity), pageable, 1);

        when(repository.findAllByBrandAndMakeYearBetweenAndRegAddr(
                anyString(), anyInt(), anyInt(), anyString(), any(Pageable.class)))
                .thenReturn(page);

        Page<CarRecordDto> result = service.findByBrandYearAndRegion(
                "Toyota", 2019, 2021, "Kyiv", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("VIN123", result.getContent().get(0).getVin());
        verify(repository).findAllByBrandAndMakeYearBetweenAndRegAddr(
                "Toyota", 2019, 2021, "Kyiv", pageable);
    }

    @Test
    void findByVin_existing_returnsDto() {
        CarEntity entity = createCarEntity("VIN123", "AA1234BB");
        when(repository.findByVin("VIN123")).thenReturn(Optional.of(entity));

        CarRecordDto dto = service.findByVin("VIN123");

        assertEquals("VIN123", dto.getVin());
    }

    @Test
    void findByVin_notFound_throws() {
        when(repository.findByVin("VIN999")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.findByVin("VIN999"));
        assertTrue(ex.getMessage().contains("VIN999"));
    }

    @Test
    void findByLicensePlate_foundInNReg_returnsDto() {
        CarEntity entity = createCarEntity("VIN123", "AA1234BB");
        when(repository.findByLicenceNumber("AA1234BB")).thenReturn(Optional.of(entity));

        CarRecordDto dto = service.findByLicensePlate("AA1234BB");

        assertEquals("AA1234BB", dto.getNumberPlate());
    }

    @Test
    void findByLicensePlate_foundInNRegOld_returnsDto() {
        CarEntity entity = createCarEntity("VIN123", "AA1234BB");
        when(repository.findByLicenceNumber("AA1234BB")).thenReturn(Optional.empty());
        when(repository.findByOldLicenceNumber("AA1234BB")).thenReturn(Optional.of(entity));

        CarRecordDto dto = service.findByLicensePlate("AA1234BB");

        assertEquals("AA1234BB", dto.getNumberPlate());
    }

    @Test
    void findByLicensePlate_notFound_throws() {
        when(repository.findByLicenceNumber("AA9999")).thenReturn(Optional.empty());
        when(repository.findByOldLicenceNumber("AA9999")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.findByLicensePlate("AA9999"));
        assertTrue(ex.getMessage().contains("AA9999"));
    }

    @Test
    void findByOperLike_returnsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        CarEntity entity = createCarEntity("VIN123", "AA1234BB");
        Page<CarEntity> page = new PageImpl<>(List.of(entity), pageable, 1);

        when(repository.findAllByOperLike("Operator1", pageable)).thenReturn(page);

        Page<CarRecordDto> result = service.findByOperLike("Operator1", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Operator1", result.getContent().get(0).getOper());
    }

    @Test
    void findByRegistrationDate_returnsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        CarEntity entity = createCarEntity("VIN123", "AA1234BB");
        Page<CarEntity> page = new PageImpl<>(List.of(entity), pageable, 1);

        LocalDate from = LocalDate.of(2020, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);

        when(repository.findAllByRegistrationDateBetween(from, to, pageable)).thenReturn(page);

        Page<CarRecordDto> result = service.findByRegistrationDate(from, to, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(LocalDate.now(), result.getContent().get(0).getExtractionDate());
    }

}
