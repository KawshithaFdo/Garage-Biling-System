package model;

public class Mechanic {
    private String m_Id;
    private String name;
    private String address;
    private String contact;
    private String nic;
    private Double salary;
    private String admin_id;

    public Mechanic(String m_Id, String name, String address, String contact, String nic, Double salary, String admin_id) {
        this.m_Id = m_Id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nic = nic;
        this.salary = salary;
        this.admin_id = admin_id;
    }

    public Mechanic() {
    }

    public String getM_Id() {
        return m_Id;
    }

    public void setM_Id(String m_Id) {
        this.m_Id = m_Id;
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

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }
}
