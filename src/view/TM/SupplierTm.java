package view.TM;

public class SupplierTm {
    private String sup_Id;
    private String name;
    private String contact;
    private String nic;
    private String company;
    private String admin_Id;

    public SupplierTm(String sup_Id, String name, String contact, String nic, String company,String admin_Id) {
        this.sup_Id = sup_Id;
        this.name = name;
        this.contact = contact;
        this.nic = nic;
        this.company = company;
        this.setAdmin_Id(admin_Id);

    }

    public SupplierTm() {
    }

    public String getSup_Id() {
        return sup_Id;
    }

    public void setSup_Id(String sup_Id) {
        this.sup_Id = sup_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getAdmin_Id() {
        return admin_Id;
    }

    public void setAdmin_Id(String admin_Id) {
        this.admin_Id = admin_Id;
    }
}
