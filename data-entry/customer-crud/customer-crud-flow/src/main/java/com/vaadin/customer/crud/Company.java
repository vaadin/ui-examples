package com.vaadin.customer.crud;

public class Company {
    private String companyName;
    private String contactName;
    private String contactEmail;

    private String address;
    private String city;
    private String zip;

    private String region;
    private String country;
    private String phone;
    private String fax;

    public Company() {

    }

    public Company(String companyName, String contactName, String contactEmail,
            String address, String city, String zip, String region,
            String country, String phone, String fax) {
        setCompanyName(companyName);
        setContactName(contactName);
        setContactEmail(contactEmail);
        setAddress(address);
        setCity(city);
        setZip(zip);
        setRegion(region);
        setCountry(country);
        setPhone(phone);
        setFax(fax);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

}
