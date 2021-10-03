package com.ashisrath.tdm;

public class UserProfile {
    public String Name;
    public String Address;
    public String State;
    public String City;
    public String Phone_number;
    public String Category;
    public String E_mail;

    public UserProfile(){
        // Empty Constructor
    }

    public UserProfile(String Category){
        this.Category = Category;
    }

    public UserProfile(String Name, String Address, String State, String City, String Phone_number, String Category, String E_mail) {
        this.Name = Name;
        this.Address = Address;
        this.State = State;
        this.City = City;
        this.Phone_number = Phone_number;
        this.Category = Category;
        this.E_mail = E_mail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getE_mail() {
        return E_mail;
    }

    public void setE_mail(String e_mail) {
        E_mail = e_mail;
    }
}
