package pt.mstavares.jkyc.models.ptcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Identity {

    @SerializedName("Surname")
    @Expose
    private String surname;
    @SerializedName("Givenname")
    @Expose
    private String givenName;
    @SerializedName("Sex")
    @Expose
    private String sex;
    @SerializedName("Height")
    @Expose
    private String height;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Birthdate")
    @Expose
    private String birthDate;
    @SerializedName("GivenNameFather")
    @Expose
    private String givenNameFather;
    @SerializedName("SurnameFather")
    @Expose
    private String surnameFather;
    @SerializedName("GivenNameMother")
    @Expose
    private String givenNameMother;
    @SerializedName("SurnameMother")
    @Expose
    private String surnameMother;
    @SerializedName("Documenttype")
    @Expose
    private String documentType;
    @SerializedName("Documentnum")
    @Expose
    private String documentNum;
    @SerializedName("CivilianIdNumber")
    @Expose
    private String civilianIdNumber;
    @SerializedName("Documentversion")
    @Expose
    private String documentVersion;
    @SerializedName("DocumentPAN")
    @Expose
    private String documentPAN;
    @SerializedName("Nationality")
    @Expose
    private String nationality;
    @SerializedName("Validityenddate")
    @Expose
    private String validityEndDate;
    @SerializedName("Validitybegindate")
    @Expose
    private String validityBeginDate;
    @SerializedName("PlaceOfRequest")
    @Expose
    private String placeOfRequest;
    @SerializedName("IssuingEntity")
    @Expose
    private String issuingEntity;
    @SerializedName("NISS")
    @Expose
    private String NISS;
    @SerializedName("NSNS")
    @Expose
    private String NSNS;
    @SerializedName("NIF")
    @Expose
    private String NIF;
    @SerializedName("Validation")
    @Expose
    private String validation;
    @SerializedName("MRZ1")
    @Expose
    private String mRZ1;
    @SerializedName("MRZ2")
    @Expose
    private String mRZ2;
    @SerializedName("MRZ3")
    @Expose
    private String mRZ3;
    @SerializedName("AccidentalIndications")
    @Expose
    private String accidentalIndications;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGivenNameFather() {
        return givenNameFather;
    }

    public void setGivenNameFather(String givenNameFather) {
        this.givenNameFather = givenNameFather;
    }

    public String getSurnameFather() {
        return surnameFather;
    }

    public void setSurnameFather(String surnameFather) {
        this.surnameFather = surnameFather;
    }

    public String getGivenNameMother() {
        return givenNameMother;
    }

    public void setGivenNameMother(String givenNameMother) {
        this.givenNameMother = givenNameMother;
    }

    public String getSurnameMother() {
        return surnameMother;
    }

    public void setSurnameMother(String surnameMother) {
        this.surnameMother = surnameMother;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNum() {
        return documentNum;
    }

    public void setDocumentNum(String documentNum) {
        this.documentNum = documentNum;
    }

    public String getCivilianIdNumber() {
        return civilianIdNumber;
    }

    public void setCivilianIdNumber(String civilianIdNumber) {
        this.civilianIdNumber = civilianIdNumber;
    }

    public String getDocumentVersion() {
        return documentVersion;
    }

    public void setDocumentVersion(String documentVersion) {
        this.documentVersion = documentVersion;
    }

    public String getDocumentPAN() {
        return documentPAN;
    }

    public void setDocumentPAN(String documentPAN) {
        this.documentPAN = documentPAN;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getValidityEndDate() {
        return validityEndDate;
    }

    public void setValidityEndDate(String validityEndDate) {
        this.validityEndDate = validityEndDate;
    }

    public String getValidityBeginDate() {
        return validityBeginDate;
    }

    public void setValidityBeginDate(String validityBeginDate) {
        this.validityBeginDate = validityBeginDate;
    }

    public String getPlaceOfRequest() {
        return placeOfRequest;
    }

    public void setPlaceOfRequest(String placeOfRequest) {
        this.placeOfRequest = placeOfRequest;
    }

    public String getIssuingEntity() {
        return issuingEntity;
    }

    public void setIssuingEntity(String issuingEntity) {
        this.issuingEntity = issuingEntity;
    }

    public String getNISS() {
        return NISS;
    }

    public void setNISS(String nISS) {
        this.NISS = nISS;
    }

    public String getNSNS() {
        return NSNS;
    }

    public void setNSNS(String nSNS) {
        this.NSNS = nSNS;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String nIF) {
        this.NIF = nIF;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getMRZ1() {
        return mRZ1;
    }

    public void setMRZ1(String mRZ1) {
        this.mRZ1 = mRZ1;
    }

    public String getMRZ2() {
        return mRZ2;
    }

    public void setMRZ2(String mRZ2) {
        this.mRZ2 = mRZ2;
    }

    public String getMRZ3() {
        return mRZ3;
    }

    public void setMRZ3(String mRZ3) {
        this.mRZ3 = mRZ3;
    }

    public String getAccidentalIndications() {
        return accidentalIndications;
    }

    public void setAccidentalIndications(String accidentalIndications) {
        this.accidentalIndications = accidentalIndications;
    }

}
