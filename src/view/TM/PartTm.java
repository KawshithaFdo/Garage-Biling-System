package view.TM;

public class PartTm {
    private String p_Id;
    private String name;
    private String country;
    private String brand;
    private int qty;
    private Double unitprice;
    private Double discount;
    private String S_Id;

    public PartTm(String p_Id, String name, String country, String brand, int qty, Double unitprice, Double discount,String s_Id) {
        this.p_Id = p_Id;
        this.name = name;
        this.country = country;
        this.brand = brand;
        this.qty = qty;
        this.unitprice = unitprice;
        this.discount = discount;
        this.setS_Id(s_Id);
    }

    public PartTm() {
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

    public String getS_Id() {
        return S_Id;
    }

    public void setS_Id(String s_Id) {
        S_Id = s_Id;
    }
}
