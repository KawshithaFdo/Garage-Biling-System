package model;

public class Admin extends Object {
    private String admin_ID;
    private String  name;
    private String  address;
    private String  contact;
    private String  nIC;
    private String  password;

    public Admin(String admin_ID, String name, String address, String contact, String nIC, String password) {
        this.admin_ID = admin_ID;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nIC = nIC;
        this.password = password;
    }

    public Admin() {
    }

    public String getAdmin_ID() {
        return admin_ID;
    }

    public void setAdmin_ID(String admin_ID) {
        this.admin_ID = admin_ID;
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

    @Override
    public String toString() {
        return "Admin{" +
                "admin_ID='" + admin_ID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", nIC='" + nIC + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
