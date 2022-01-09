package model;

public class Part {
    private String p_Id;
    private String name;
    private String country;
    private String brand;
    private int qty;
    private Double unitprice;
    private Double discount;
    private String sup_Id;

    public Part(String p_Id, String name, String country, String brand, int qty, Double unitprice, Double discount, String sup_Id) {
        this.p_Id = p_Id;
        this.name = name;
        this.country = country;
        this.brand = brand;
        this.qty = qty;
        this.unitprice = unitprice;
        this.discount = discount;
        this.sup_Id = sup_Id;
    }

    public Part() {
    }

    public String getP_Id() {
        return p_Id;
    }

    public void setP_Id(String p_Id) {
        this.p_Id = p_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getSup_Id() {
        return sup_Id;
    }

    public void setSup_Id(String sup_Id) {
        this.sup_Id = sup_Id;
    }
}
