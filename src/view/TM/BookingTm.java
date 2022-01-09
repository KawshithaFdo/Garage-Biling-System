package view.TM;

import java.util.Date;

public class BookingTm {
    private String booking_Id;
    private Date date;
    private String time;
    private String cust_Id;
    private String reg_No;
    private String user_Id;

    public BookingTm(String booking_Id, Date date, String time, String cust_Id, String reg_No, String user_Id) {
        this.booking_Id = booking_Id;
        this.date = date;
        this.time = time;
        this.cust_Id = cust_Id;
        this.reg_No = reg_No;
        this.user_Id = user_Id;
    }

    public BookingTm() {
    }

    public String getBooking_Id() {
        return booking_Id;
    }

    public void setBooking_Id(String booking_Id) {
        this.booking_Id = booking_Id;
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

    public String getCust_Id() {
        return cust_Id;
    }

    public void setCust_Id(String cust_Id) {
        this.cust_Id = cust_Id;
    }

    public String getReg_No() {
        return reg_No;
    }

    public void setReg_No(String reg_No) {
        this.reg_No = reg_No;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }
}
