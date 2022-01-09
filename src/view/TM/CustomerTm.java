package view.TM;

public class CustomerTm {
    private String cust_Id;
    private String  name;
    private String  address;
    private String  contact;
    private String  nIC;
    private  String user_Id;

    public CustomerTm(String cust_Id, String name, String address, String contact, String nIC,String user_Id) {
        this.cust_Id = cust_Id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nIC = nIC;
        this.setUser_Id(user_Id);
    }



    public CustomerTm() {
    }

    public String getCust_Id() {
        return cust_Id;
    }

    public void setCust_Id(String cust_Id) {
        this.cust_Id = cust_Id;
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

    public String getNIC() {
        return nIC;
    }

    public void setNIC(String nIC) {
        this.nIC = nIC;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }
}
