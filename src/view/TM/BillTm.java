package view.TM;

import java.sql.Date;

public class BillTm {
    private String billNo;
    private Date date;
    private String time;
    private Double partCost;
    private Double mechanicCost;
    private Double discount;
    private Double amount;
    private String bookingId;

    public BillTm(String billNo, Date date, String time, Double partCost, Double mechanicCost, Double discount, Double amount,String bookingId) {
        this.billNo = billNo;
        this.date = date;
        this.time = time;
        this.partCost = partCost;
        this.mechanicCost = mechanicCost;
        this.discount = discount;
        this.amount = amount;
        this.setBookingId(bookingId);
    }

    public BillTm() {
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
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

    public Double getPartCost() {
        return partCost;
    }

    public void setPartCost(Double partCost) {
        this.partCost = partCost;
    }

    public Double getMechanicCost() {
        return mechanicCost;
    }

    public void setMechanicCost(Double mechanicCost) {
        this.mechanicCost = mechanicCost;
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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
