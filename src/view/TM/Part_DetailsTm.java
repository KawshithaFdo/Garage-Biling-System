package view.TM;

public class Part_DetailsTm {
    private String reg_No;
    private String p_Id;
    private String booking_Id;
    private Double price;
    private int qty_forUse;
    private String bill_No;

    public Part_DetailsTm(String reg_No, String p_Id, String booking_Id, Double price, int qty_forUse, String bill_No) {
        this.reg_No = reg_No;
        this.p_Id = p_Id;
        this.booking_Id = booking_Id;
        this.price = price;
        this.qty_forUse = qty_forUse;
        this.bill_No = bill_No;
    }

    public Part_DetailsTm() {
    }

    public String getReg_No() {
        return reg_No;
    }

    public void setReg_No(String reg_No) {
        this.reg_No = reg_No;
    }

    public String getP_Id() {
        return p_Id;
    }

    public void setP_Id(String p_Id) {
        this.p_Id = p_Id;
    }

    public String getBooking_Id() {
        return booking_Id;
    }

    public void setBooking_Id(String booking_Id) {
        this.booking_Id = booking_Id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty_forUse() {
        return qty_forUse;
    }

    public void setQty_forUse(int qty_forUse) {
        this.qty_forUse = qty_forUse;
    }

    public String getBill_No() {
        return bill_No;
    }

    public void setBill_No(String bill_No) {
        this.bill_No = bill_No;
    }
}
