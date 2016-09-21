package com.example.jayjay.informer;

/**
 * Created by JayJay on 9/21/2016.
 */
public class ReportPOJO {
    //name and address string
    private String firstname;
    private String lastname;
    private String county;
    private String description;
    private String perpetrator;

    public ReportPOJO() {
      /*Blank default constructor essential for Firebase*/
    }

    //Getters and setters
    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerpetrator() {
        return perpetrator;
    }

    public void setPerpetrator(String perpetrator) {
        this.perpetrator = perpetrator;
    }
}
