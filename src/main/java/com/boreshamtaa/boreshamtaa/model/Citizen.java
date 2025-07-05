package com.boreshamtaa.boreshamtaa.model;

import java.time.LocalDate;
import java.util.Base64;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "citizens")
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String zanId;
    private int age;
    private String phoneNumber;
    private String houseNo;
    private String ward;
    private String gender;
    private String password;
    private String profileFileName;
    private LocalDate birthDate;

    @Lob
    private byte[] profile;
    

    // @Transient
    // private String profileBase64;

    // No-arg constructor (required by JPA)
    public Citizen() {
    }

    // All-arg constructor
    public Citizen(Long id, String name, String address, String zanId, int age,
                   String phoneNumber, String houseNo, String ward, String gender,
                   String password, byte[] profile, LocalDate birthDate,String profileFileName) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zanId = zanId;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.houseNo = houseNo;
        this.ward = ward;
        this.gender = gender;
        this.password = password;
        this.profile = profile;
        this.birthDate = birthDate;
        this.profileFileName=profileFileName;
    }

    // Getters and Setters for all fields
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZanId() {
        return zanId;
    }

    public void setZanId(String zanId) {
        this.zanId = zanId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfileBase64() {
        if (profile != null) {
            return Base64.getEncoder().encodeToString(profile);
        }
        return null;
    }

    public String getProfileFileName() {
        return profileFileName;
    }

    public void setProfileFileName(String profileFileName) {
        this.profileFileName = profileFileName;
    }
}