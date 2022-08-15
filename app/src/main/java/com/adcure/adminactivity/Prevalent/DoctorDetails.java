package com.adcure.adminactivity.Prevalent;

public class DoctorDetails{
    private String City,Name,Email,Address,Date,Country,Experience,
    Hospital_Address,Hospital_Name,Id,Image,Number,Price,Qualification,Specialist,State,Time,Available_From,Available_to,No_of_patients;

    public String getAvailable_From() {
        return Available_From;
    }

    public void setAvailable_From(String available_From) {
        Available_From = available_From;
    }

    public String getAvailable_to() {
        return Available_to;
    }

    public void setAvailable_to(String available_to) {
        Available_to = available_to;
    }

    public String getNo_of_patients() {
        return No_of_patients;
    }

    public void setNo_of_patients(String no_of_patients) {
        No_of_patients = no_of_patients;
    }

    public DoctorDetails(){}

    public DoctorDetails(String city, String name, String email, String address, String date, String country, String experience, String hospital_Address, String hospital_Name, String id, String image, String number, String price, String qualification, String specialist, String state, String time, String available_From, String available_to, String no_of_patients) {
        City = city;
        Name = name;
        Email = email;
        Address = address;
        Date = date;
        Country = country;
        Experience = experience;
        Hospital_Address = hospital_Address;
        Hospital_Name = hospital_Name;
        Id = id;
        Image = image;
        Number = number;
        Price = price;
        Qualification = qualification;
        Specialist = specialist;
        State = state;
        Time = time;
        Available_From = available_From;
        Available_to = available_to;
        No_of_patients = no_of_patients;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getName() {
        return Name;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getHospital_Address() {
        return Hospital_Address;
    }

    public void setHospital_Address(String hospital_Address) {
        Hospital_Address = hospital_Address;
    }

    public String getHospital_Name() {
        return Hospital_Name;
    }

    public void setHospital_Name(String hospital_Name) {
        Hospital_Name = hospital_Name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getSpecialist() {
        return Specialist;
    }

    public void setSpecialist(String specialist) {
        Specialist = specialist;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}