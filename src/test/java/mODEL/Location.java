package mODEL;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

//Gelen Datanin Class sablonunu olusturalim
public class Location {
    private  String postcode;
    private String countryabbreviation;
   private String country;
   private ArrayList<Place> places;


    public String getCountryabbreviation() {
        return countryabbreviation;
    }
    @JsonProperty("country abbreviation")
    public void setCountryabbreviation(String countryabbreviation) {
        this.countryabbreviation = countryabbreviation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public String getPostcode() {
        return postcode;
    }

    @Override
    public String toString() {
        return "Location{" +
                "postcode='" + postcode + '\'' +
                ", countryabbreviation='" + countryabbreviation + '\'' +
                ", country='" + country + '\'' +
                ", places=" + places +
                '}';
    }

    @JsonProperty("post code")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
