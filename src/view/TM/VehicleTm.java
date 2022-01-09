package view.TM;

public class VehicleTm {
    private String reg_No;
    private String name;
    private String brand;
    private String yom;
    private String fuel;
    private String cust_Id;

    public VehicleTm(String reg_No, String name, String brand, String yom, String fuel, String cust_Id) {
        this.reg_No = reg_No;
        this.name = name;
        this.brand = brand;
        this.yom = yom;
        this.fuel = fuel;
        this.cust_Id = cust_Id;
    }

    public VehicleTm() {
    }

    public String getReg_No() {
        return reg_No;
    }

    public void setReg_No(String reg_No) {
        this.reg_No = reg_No;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYom() {
        return yom;
    }

    public void setYom(String yom) {
        this.yom = yom;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getCust_Id() {
        return cust_Id;
    }

    public void setCust_Id(String cust_Id) {
        this.cust_Id = cust_Id;
    }
}
