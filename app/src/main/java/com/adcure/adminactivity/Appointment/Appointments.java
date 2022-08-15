package com.adcure.adminactivity.Appointment;

public class Appointments {
    private String amount,consulted,payment_id,date,doctor_Name,doctor_Number,email_id,hospital_Address,name,number,specialist_Type,time,gender,user_id;

    public Appointments(String amount, String consulted,String date, String doctor_Name, String email_id, String hospital_Address, String name, String number,String doctor_Number, String specialist_Type, String time, String gender, String user_id) {
        this.amount = amount;
        this.date = date;
        this.doctor_Name = doctor_Name;
        this.email_id = email_id;
        this.hospital_Address = hospital_Address;
        this.name = name;
        this.number = number;
        this.specialist_Type = specialist_Type;
        this.time = time;
        this.gender = gender;
        this.user_id = user_id;
        this.doctor_Number=doctor_Number;
        this.consulted=consulted;
    }

    public String getConsulted() {
        return consulted;
    }

    public void setConsulted(String consulted) {
        this.consulted = consulted;
    }

    public Appointments() {
    }
    public String getPayment_id(){
        return payment_id;
    }
    public void setPayment_id(String payment_id){
        this.payment_id=payment_id;
    }
    public String getAmount() {
        return amount;
    }

    public String getDoctor_Number() {
        return doctor_Number;
    }

    public void setDoctor_Number(String doctor_Number) {
        this.doctor_Number = doctor_Number;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor_Name() {
        return doctor_Name;
    }

    public void setDoctor_Name(String doctor_Name) {
        this.doctor_Name = doctor_Name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getHospital_Address() {
        return hospital_Address;
    }

    public void setHospital_Address(String hospital_Address) {
        this.hospital_Address = hospital_Address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSpecialist_Type() {
        return specialist_Type;
    }

    public void setSpecialist_Type(String specialist_Type) {
        this.specialist_Type = specialist_Type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
