package com.example.jayjay.informer;

/**
 * Created by JayJay on 9/21/2016.
 */
public class ReportPOJO {
    //name and address string
    private String firstName;
    private String lastName;
    private String county;
    private String constituency;
    private String ward;
    private String pollingStation;
    private String description;
    private String perpetrator;

    public ReportPOJO() {
      /*Blank default constructor essential for Firebase*/
    }

    public ReportPOJO(String firstName, String lastName, String county, String constituency, String ward, String pollingStation, String description, String perpetrator) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.county = county;
        this.constituency = constituency;
        this.ward = ward;
        this.pollingStation = pollingStation;
        this.description = description;
        this.perpetrator = perpetrator;
    }
    //Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getPollingStation() {
        return pollingStation;
    }

    public void setPollingStation(String pollingStation) {
        this.pollingStation = pollingStation;
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
