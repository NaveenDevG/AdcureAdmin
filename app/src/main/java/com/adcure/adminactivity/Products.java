package com.adcure.adminactivity;

public class Products {
    private String Category,Company,Date,Time,
            Description,Img1,img2,Img3,Name,No,Pdate,Edate,Price,Prescription,Stock,Pid;
    public Products() {
    }

    public Products(String category, String company, String date, String time, String description, String img1, String img2, String img3, String name, String no, String pdate, String edate, String price, String prescription, String stock) {
        Category = category;
        Company = company;
        Date = date;
        Time = time;
        Description = description;
        Img1 = img1;
        this.img2 = img2;
        Img3 = img3;
        Name = name;
        No = no;
        Pdate = pdate;
        Edate = edate;
        Price = price;
        Prescription = prescription;
        Stock = stock;
    }

    public String getCategory() {
        return Category;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImg1() {
        return Img1;
    }

    public void setImg1(String img1) {
        Img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return Img3;
    }

    public void setImg3(String img3) {
        Img3 = img3;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getPdate() {
        return Pdate;
    }

    public void setPdate(String pdate) {
        Pdate = pdate;
    }

    public String getEdate() {
        return Edate;
    }

    public void setEdate(String edate) {
        Edate = edate;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPrescription() {
        return Prescription;
    }

    public void setPrescription(String prescription) {
        Prescription = prescription;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }
}