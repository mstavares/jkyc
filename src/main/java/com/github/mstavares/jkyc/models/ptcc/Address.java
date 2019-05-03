package com.github.mstavares.jkyc.models.ptcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("District")
    @Expose
    private String district;
    @SerializedName("DistrictCode")
    @Expose
    private String districtCode;
    @SerializedName("Municipality")
    @Expose
    private String municipality;
    @SerializedName("MunicipalityCode")
    @Expose
    private String municipalityCode;
    @SerializedName("Parish")
    @Expose
    private String parish;
    @SerializedName("ParishCode")
    @Expose
    private String parishCode;
    @SerializedName("Streettype")
    @Expose
    private String streetType;
    @SerializedName("AbbrStreetType")
    @Expose
    private String abbrStreetType;
    @SerializedName("Streetname")
    @Expose
    private String streetName;
    @SerializedName("Buildingtype")
    @Expose
    private String buildingType;
    @SerializedName("AbbrBuildingType")
    @Expose
    private String abbrBuildingType;
    @SerializedName("Doorno")
    @Expose
    private String doorNo;
    @SerializedName("Floor")
    @Expose
    private String floor;
    @SerializedName("Place")
    @Expose
    private String place;
    @SerializedName("Side")
    @Expose
    private String side;
    @SerializedName("Locality")
    @Expose
    private String locality;
    @SerializedName("Zip4")
    @Expose
    private String zip4;
    @SerializedName("Zip3")
    @Expose
    private String zip3;
    @SerializedName("PostalLocality")
    @Expose
    private String postalLocality;
    @SerializedName("GeneratedAddressCode")
    @Expose
    private String generatedAddressCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public String getParish() {
        return parish;
    }

    public void setParish(String parish) {
        this.parish = parish;
    }

    public String getParishCode() {
        return parishCode;
    }

    public void setParishCode(String parishCode) {
        this.parishCode = parishCode;
    }

    public String getStreetType() {
        return streetType;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public String getAbbrStreetType() {
        return abbrStreetType;
    }

    public void setAbbrStreetType(String abbrStreetType) {
        this.abbrStreetType = abbrStreetType;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getAbbrBuildingType() {
        return abbrBuildingType;
    }

    public void setAbbrBuildingType(String abbrBuildingType) {
        this.abbrBuildingType = abbrBuildingType;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getZip4() {
        return zip4;
    }

    public void setZip4(String zip4) {
        this.zip4 = zip4;
    }

    public String getZip3() {
        return zip3;
    }

    public void setZip3(String zip3) {
        this.zip3 = zip3;
    }

    public String getPostalLocality() {
        return postalLocality;
    }

    public void setPostalLocality(String postalLocality) {
        this.postalLocality = postalLocality;
    }

    public String getGeneratedAddressCode() {
        return generatedAddressCode;
    }

    public void setGeneratedAddressCode(String generatedAddressCode) {
        this.generatedAddressCode = generatedAddressCode;
    }

}
