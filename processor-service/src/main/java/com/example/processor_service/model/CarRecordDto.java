package com.example.processor_service.model;

import com.example.processor_service.entity.CarEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CarRecordDto {

    private UUID extractionId;

    private LocalDate extractionDate;

    private Long registrationId;

    private String numberPlate;

    private LocalDate registrationDate;

    private String oldNumberPlate;

    private String issuingAuthority;

    private String nDoc;

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

    public static CarRecordDto toDto(CarEntity entity) {
        return CarRecordDto.builder()
                .extractionId(entity.getExtractionId())
                .extractionDate(entity.getExtractionDate())
                .registrationId(entity.getRegistrationId())
                .numberPlate(entity.getLicenceNumber())
                .registrationDate(entity.getRegistrationDate())
                .oldNumberPlate(entity.getOldLicenceNumber())
                .issuingAuthority(entity.getIssuingAuthority())
                .nDoc(entity.getNumberDoc())
                .brandId(entity.getBrandId())
                .brand(entity.getBrand())
                .modelId(entity.getModelId())
                .model(entity.getModel())
                .rankId(entity.getRankId())
                .rank(entity.getRank())
                .kindId(entity.getKindId())
                .kind(entity.getKind())
                .bodyId(entity.getBodyId())
                .body(entity.getBody())
                .purposeId(entity.getPurposeId())
                .purpose(entity.getPurpose())
                .makeYear(entity.getMakeYear())
                .colorId(entity.getColorId())
                .color(entity.getColor())
                .vin(entity.getVin())
                .nBody(entity.getNBody())
                .nChassis(entity.getNChassis())
                .nEngine(entity.getNEngine())
                .capacity(entity.getCapacity())
                .fuelId(entity.getFuelId())
                .fuel(entity.getFuel())
                .totalWeight(entity.getTotalWeight())
                .ownWeight(entity.getOwnWeight())
                .cylinder(entity.getCylinder())
                .nDoors(entity.getNDoors())
                .nSeating(entity.getNSeating())
                .operId(entity.getOperId())
                .oper(entity.getOper())
                .regAddressR(entity.getRegAddressR())
                .note(entity.getNote())
                .ecologyId(entity.getEcologyId())
                .ecology(entity.getEcology())
                .depId(entity.getDepId())
                .dep(entity.getDep())
                .car(entity.getCar())
                .clnt(entity.getClnt())
                .regAddr(entity.getRegAddr())
                .regAddrKoatuu(entity.getRegAddrKoatuu())
                .person(entity.getPerson())
                .build();
    }
}
