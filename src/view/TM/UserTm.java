package view.TM;

public class UserTm {
    private String user_ID;
    private String  name;
    private String  address;
    private String  contact;
    private String  nIC;

    public UserTm(String user_ID, String name, String address, String contact, String nIC) {
        this.user_ID = user_ID;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nIC = nIC;
    }

    public UserTm() {
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
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
}
