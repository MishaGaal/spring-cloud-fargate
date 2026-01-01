package com.example.parser_service.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Entry {

    private UUID extractionId;

    private LocalDate extractionDate;

    @ExcelProperty("DOC#R")
    private Long docR;

    @ExcelProperty("N_REG")
    private String nReg;

    @ExcelProperty(value = "D_REG")
    @DateTimeFormat("MM/dd/yyyy")
    private LocalDate dReg;

    @ExcelProperty("N_REG_OLD")
    private String nRegOld;

    @ExcelProperty("S_DOC")
    private String sDoc;

    @ExcelProperty("N_DOC")
    private String nDoc;

    @ExcelProperty("BRAND_ID")
    private Long brandId;

    @ExcelProperty("BRAND")
    private String brand;

    @ExcelProperty("MODEL_ID")
    private Long modelId;

    @ExcelProperty("MODEL")
    private String model;

    @ExcelProperty("RANK_ID")
    private Long rankId;

    @ExcelProperty("RANK")
    private String rank;

    @ExcelProperty("KIND_ID")
    private Long kindId;

    @ExcelProperty("KIND")
    private String kind;

    @ExcelProperty("BODY_ID")
    private Long bodyId;

    @ExcelProperty("BODY")
    private String body;

    @ExcelProperty("PURPOSE_ID")
    private Long purposeId;

    @ExcelProperty("PURPOSE")
    private String purpose;

    @ExcelProperty("MAKE_YEAR")
    private Integer makeYear;

    @ExcelProperty("COLOR_ID")
    private Long colorId;

    @ExcelProperty("COLOR")
    private String color;

    @ExcelProperty("VIN")
    private String vin;

    @ExcelProperty("N_BODY")
    private String nBody;

    @ExcelProperty("N_CHASSIS")
    private String nChassis;

    @ExcelProperty("N_ENGINE")
    private String nEngine;

    @ExcelProperty("CAPACITY")
    private Integer capacity;

    @ExcelProperty("FUEL_ID")
    private Long fuelId;

    @ExcelProperty("FUEL")
    private String fuel;

    @ExcelProperty("TOTAL_WEIGHT")
    private Integer totalWeight;

    @ExcelProperty("OWN_WEIGHT")
    private Integer ownWeight;

    @ExcelProperty("CYLINDER")
    private Integer cylinder;

    @ExcelProperty("N_DOORS")
    private Integer nDoors;

    @ExcelProperty("N_SEATING")
    private Integer nSeating;

    @ExcelProperty("OPER_ID")
    private Long operId;

    @ExcelProperty("OPER")
    private String oper;

    @ExcelProperty("REG_ADDRESS#R")
    private Long regAddressR;

    @ExcelProperty("NOTE")
    private String note;

    @ExcelProperty("ECOLOGY_ID")
    private Long ecologyId;

    @ExcelProperty("ECOLOGY")
    private String ecology;

    @ExcelProperty("DEP_ID")
    private Long depId;

    @ExcelProperty("DEP")
    private String dep;

    @ExcelProperty("CAR#")
    private Long car;

    @ExcelProperty("CLNT#")
    private Long clnt;

    @ExcelProperty("REGADDR")
    private String regAddr;

    @ExcelProperty("REGADDR_KOATUU")
    private String regAddrKoatuu;

    @ExcelProperty("PERSON")
    private String person;
}
