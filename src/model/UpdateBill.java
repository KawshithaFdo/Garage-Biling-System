package model;

import java.sql.Date;
import java.util.ArrayList;

public class UpdateBill {
    private String bill_No;
    private Date date;
    private String time;
    private Double part_Cost;
    private Double mechanic_Cost;
    private Double discount;
    private Double amount;
    private String user_Id;
    private String booking_Id;
    private ArrayList<Part_Details> parts;
    private int oldQty;

    public UpdateBill(String bill_No, Date date, String time, Double part_Cost, Double mechanic_Cost, Double discount, Double amount, String user_Id, String booking_Id, ArrayList<Part_Details> parts, int oldQty) {
        this.bill_No = bill_No;
        this.date = date;
        this.time = time;
        this.part_Cost = part_Cost;
        this.mechanic_Cost = mechanic_Cost;
        this.discount = discount;
        this.amount = amount;
        this.user_Id = user_Id;
        this.booking_Id = booking_Id;
        this.parts = parts;
        this.oldQty = oldQty;
    }

    public UpdateBill() {
    }

    public String getBill_No() {
        return bill_No;
    }

    public void setBill_No(String bill_No) {
        this.bill_No = bill_No;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPart_Cost() {
        return part_Cost;
    }

    public void setPart_Cost(Double part_Cost) {
        this.part_Cost = part_Cost;
    }

    public Double getMechanic_Cost() {
        return mechanic_Cost;
    }

    public void setMechanic_Cost(Double mechanic_Cost) {
        this.mechanic_Cost = mechanic_Cost;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getBooking_Id() {
        return booking_Id;
    }

    public void setBooking_Id(String booking_Id) {
        this.booking_Id = booking_Id;
    }

    public ArrayList<Part_Details> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Part_Details> parts) {
        this.parts = parts;
    }

    public int getOldQty() {
        return oldQty;
    }

    public void setOldQty(int oldQty) {
        this.oldQty = oldQty;
    }
}
