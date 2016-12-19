package com.example.jayjay.informer;

/**
 * Created by JayJay on 12/11/2016.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Parties {
    private String abr;
    private String name;
    private String symbol;
    private String website;

    public Parties() {
      /*Blank default constructor essential for Firebase*/
    }

    public String getAbr() {
        return abr;
    }

    public void setAbr(String abr) {
        this.abr = abr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Parties(String abr, String name, String symbol, String website) {
        this.abr = abr;
        this.name = name;
        this.symbol = symbol;
        this.website = website;
    }
}
