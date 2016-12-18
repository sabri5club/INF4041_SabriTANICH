package com.sabri.inf4042_sabritanich;


public class ContactDetail {
    private int id;
    private String number;
    private String lastName;
    private String firstName;
    private String photo;
    //private String email;


    public ContactDetail(int id, String lastName, String firstName, String number, String photo) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.number = number;
        this.photo=photo;
     //   this.email = email;
    }

    public ContactDetail(String lastName, String firstName, String number, String photo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.number = number;
        this.photo=photo;
        //   this.email = email;
    }

    public ContactDetail(){

    }

    public String getPhoto()
    {
        return this.photo;
    }

    public void setPhoto(String p)
    {
        this.photo=p;
    }

   /* public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return lastName;
    }

    public void setName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getFirstName(){
        return  this.firstName;
    }

/*    public void setEmail(String email)
    {
        this.email = email;
    }

    public  String getEmail()
    {
        return  this.email;
    }*/
}
