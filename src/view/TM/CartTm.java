package view.TM;

public class CartTm {
    private String p_Id;
    private String p_Name;
    private int qty;
    private int qty_avilable;
    private Double unitprice;
    private Double discount;
    private Double price;

    public CartTm(String p_Id, String p_Name, int qty,int qty_avilable, Double unitprice, Double discount, Double price ) {
        this.p_Id = p_Id;
        this.p_Name = p_Name;
        this.qty = qty;
        this.unitprice = unitprice;
        this.discount = discount;
        this.price = price;
        this.qty_avilable=(qty_avilable);
    }

    public CartTm() {
    }

    public String getP_Id() {
        return p_Id;
    }

    public void setP_Id(String p_Id) {
        this.p_Id = p_Id;
    }

    public String getP_Name() {
        return p_Name;
    }

    public void setP_Name(String p_Name) {
        this.p_Name = p_Name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty_avilable() {
        return qty_avilable;
    }

    public void setQty_avilable(int qty_avilable) {
        this.qty_avilable = qty_avilable;
    }
}
