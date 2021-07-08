package com.example.orbital;

public class UserModel {
   String Name, Contact, Address, Gender, Status, Uid;

    public UserModel(String Name, String Contact, String Address, String Gender, String Status, String Uid) {
        this.Name = Name;
        this.Contact = Contact;
        this.Address = Address;
        this.Gender = Gender;
        this.Status = Status;
        this.Uid = Uid;

    }

    public UserModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid){
       this.Uid= Uid;
    }
}
