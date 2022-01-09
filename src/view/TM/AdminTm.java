package view.TM;

public class AdminTm {
    private String admin_ID;
    private String  name;
    private String  address;
    private String  contact;
    private String nIc;

    public AdminTm() {
    }

    public AdminTm(String admin_ID, String name, String address, String contact,String nIc) {
        this.admin_ID = admin_ID;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nIc=nIc;
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

    public String getNIc() {
        return nIc;
    }

    public void setNIc(String nIc) {
        this.nIc = nIc;
    }
}
