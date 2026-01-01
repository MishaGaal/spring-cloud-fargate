package com.example.processor_service.entity;


import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class CarEntity {
    private UUID extractionId;

    private LocalDate extractionDate;

    @Id
    @JsonAlias("docR")
    private Long registrationId;

    @JsonAlias("nReg")
    private String licenceNumber;

    @JsonAlias("dReg")
    private LocalDate registrationDate;

    @JsonAlias("nRegOld")
    private String oldLicenceNumber;

    @JsonAlias("sDoc")
    private String issuingAuthority;

    @JsonAlias("nDoc")
    private String numberDoc;

    private Long brandId;

    private String brand;

    private Long modelId;

    private String model;

    private Long rankId;

    private String rank;

    private Long kindId;

    private String kind;

    private Long bodyId;

    private String body;

    private Long purposeId;

    private String purpose;

    private Integer makeYear;

    private Long colorId;

    private String color;

    private String vin;

    private String nBody;

    private String nChassis;

    private String nEngine;

    private Integer capacity;

    private Long fuelId;

    private String fuel;

    private Integer totalWeight;

    private Integer ownWeight;

    private Integer cylinder;

    private Integer nDoors;

    private Integer nSeating;

    private Long operId;

    private String oper;

    private Long regAddressR;

    private String note;

    private Long ecologyId;

    private String ecology;

    private Long depId;

    private String dep;

    private Long car;

    private Long clnt;

    private String regAddr;

    private String regAddrKoatuu;

    private String person;
}