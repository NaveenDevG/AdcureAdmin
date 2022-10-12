package com.adcure.adminactivity.Prevalent;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Medicine implements Serializable {
    private String Img1,Img2,Img3,Pid,No,Name,Category,Description
            ,Company,Pdate,Price,Stock,Subcategory,Edate,
    Flat_discount,Extra_discount,Discount_price,Prescription,Date,Time;

    public Medicine() {

    }

    public String getImg1() {
        return Img1;
    }

    public void setImg1(String img1) {
        Img1 = img1;
    }

    public String getImg2() {
        return Img2;
    }

    public void setImg2(String img2) {
        Img2 = img2;
    }

    public String getImg3() {
        return Img3;
    }

    public void setImg3(String img3) {
        Img3 = img3;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getPdate() {
        return Pdate;
    }

    public void setPdate(String pdate) {
        Pdate = pdate;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getSubcategory() {
        return Subcategory;
    }

    public void setSubcategory(String subcategory) {
        Subcategory = subcategory;
    }

    public String getEdate() {
        return Edate;
    }

    public void setEdate(String edate) {
        Edate = edate;
    }

    public String getFlat_discount() {
        return Flat_discount;
    }

    public void setFlat_discount(String flat_discount) {
        Flat_discount = flat_discount;
    }

    public String getExtra_discount() {
        return Extra_discount;
    }

    public void setExtra_discount(String extra_discount) {
        Extra_discount = extra_discount;
    }

    public String getDiscount_price() {
        return Discount_price;
    }

    public void setDiscount_price(String discount_price) {
        Discount_price = discount_price;
    }

    public String getPrescription() {
        return Prescription;
    }

    public void setPrescription(String prescription) {
        Prescription = prescription;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
