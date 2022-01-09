package model;

public class Customer {
    private String cust_ID;
    private String name;
    private String address;
    private String contact;
    private String nIC;
    private String user_ID;

    public Customer(String cust_ID, String name, String address, String contact, String nIC, String user_ID) {
        this.cust_ID = cust_ID;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nIC = nIC;
        this.user_ID = user_ID;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cust_ID='" + getCust_ID() + '\'' +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", contact='" + getContact() + '\'' +
                ", nIC='" + getnIC() + '\'' +
                ", user_ID='" + getUser_ID() + '\'' +
                '}';
    }



    public Customer() {
    }

    public String getCust_ID() {
        return cust_ID;
    }

    public void setCust_ID(String cust_ID) {
        this.cust_ID = cust_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getnIC() {
        return nIC;
    }

    public void setnIC(String nIC) {
        this.nIC = nIC;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }
}
