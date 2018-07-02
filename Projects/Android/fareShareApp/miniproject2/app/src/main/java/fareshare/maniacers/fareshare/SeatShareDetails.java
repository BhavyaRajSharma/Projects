package fareshare.maniacers.fareshare;

import java.util.List;

/**
 * Created by Bhavya Raj Sharma on 22-04-2018.
 */

public class SeatShareDetails {
    public SeatShareDetails() {
    }



    public String getTrain_name() {
        return train_name;
    }

    public void setTrain_name(String train_name) {
        this.train_name = train_name;
    }

    public String getTrain_number() {
        return train_number;
    }

    public void setTrain_number(String train_number) {
        this.train_number = train_number;
    }

    public String getBoarding_name() {
        return boarding_name;
    }

    public void setBoarding_name(String boarding_name) {
        this.boarding_name = boarding_name;
    }

    public String getBoarding_code() {
        return boarding_code;
    }

    public void setBoarding_code(String boarding_code) {
        this.boarding_code = boarding_code;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public void setDestination_name(String destination_name) {
        this.destination_name = destination_name;
    }

    public String getDestination_code() {
        return destination_code;
    }

    public void setDestination_code(String destination_code) {
        this.destination_code = destination_code;
    }

    public int getTotal_person() {
        return total_person;
    }

    public void setTotal_person(int total_person) {
        this.total_person = total_person;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

   // public String[] getCurrent_status() {
     //   return current_status;
//    }
//
//    public void setCurrent_status(String[] current_status) {
//        this.current_status = current_status;
//    }
//
//    public String[] getBooking_status() {
//        return booking_status;
//    }
//
//    public void setBooking_status(String[] booking_status) {
//        this.booking_status = booking_status;
//    }
//
//    public String[] getTrain_route() {
//        return train_route;
//    }
//
//    public void setTrain_route(String[] train_route) {
//        this.train_route = train_route;
//    }

    String train_name;
    String train_number;
    String boarding_name;
    String boarding_code;
    String destination_name;
    String destination_code;
    String class_code;
    String doj;
    String user_name;
    String user_email;
    String user_id;

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    List<String> current,booking;

    public List<String> getCurrent() {
        return current;
    }

    public void setCurrent(List<String> current) {
        this.current = current;
    }

    public List<String> getBooking() {
        return booking;
    }

    public void setBooking(List<String> booking) {
        this.booking = booking;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price;
    int total_person;

    //String[] train_route,current_status,booking_status;




}
