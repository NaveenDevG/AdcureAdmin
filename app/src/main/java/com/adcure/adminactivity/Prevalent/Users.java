package com.adcure.adminactivity.Prevalent;

public class Users {
    private String name, password;

    public Users(){}
    public Users(String name,  String password) {
        this.name = name;

        this.password = password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
