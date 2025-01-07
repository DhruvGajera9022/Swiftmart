package com.example.swiftmart.Model;

public class AddressModel {

    String addressId, fullName, phoneNumber, houseNo, roadName, city, state, pinCode, addressType;

    public AddressModel() {
    }

    public AddressModel(
            String addressId,
            String fullName,
            String phoneNumber,
            String houseNo,
            String roadName,
            String city,
            String state,
            String pinCode,
            String addressType) {
        this.addressId = addressId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.houseNo = houseNo;
        this.roadName = roadName;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.addressType = addressType;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}
