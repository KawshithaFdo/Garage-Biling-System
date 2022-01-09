package model;

public class User {
    private String user_ID;
    private String  name;
    private String  address;
    private String  contact;
    private String  nIC;
    private String  password;

    @Override
    public String toString() {
        return "User{" +
                "user_ID='" + getUser_ID() + '\'' +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", contact='" + getContact() + '\'' +
                ", nIC='" + getnIC() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }

    public User() {
    }

    public User(String user_ID, String name, String address, String contact, String nIC, String password) {
        this.user_ID = user_ID;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nIC = nIC;
        this.password = password;
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

    public String getnIC() {
        return nIC;
    }

    public void setnIC(String nIC) {
        this.nIC = nIC;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
