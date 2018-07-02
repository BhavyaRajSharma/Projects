package com.example.bhavyarajsharma.chitchat;

/**
 * Created by Bhavya Raj Sharma on 30-06-2017.
 */

public class friendly_list {


  //  private String text;
    private String name;
    private String number;

//    private String photoUrl;
//    private String myurl;

    public friendly_list() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public friendly_list(String name, String number) {
      //  this.text = text;
        this.name = name;
        this.number=number;

//        this.photoUrl = photoUrl;
//        this.myurl=myurl;
    }

//    public String getMyurl() {
//        return myurl;
//    }
//
//    public void setMyurl(String myurl) {
//        this.myurl = myurl;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getPhotoUrl() {
//        return photoUrl;
//    }
//
//    public void setPhotoUrl(String photoUrl) {
//        this.photoUrl = photoUrl;
//    }
}
