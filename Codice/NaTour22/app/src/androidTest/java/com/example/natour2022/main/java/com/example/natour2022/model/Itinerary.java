package com.example.natour2022.model;

public class Itinerary {

    private String itineraryId;
    private String titleItinerary;
    private String descriptionItinerary;
    private String difficultyLevel;
    private int hours;
    private int minutes;
    private int seconds;
    private String userId;
    private String image_itinerary;
    private String email;


    public Itinerary(String itineraryId,
                     String titleItinerary,
                     String descriptionItinerary,
                     String difficultyLevel,
                     String hours,
                     String minutes,
                     String seconds,
                     String image_itinerary) {

        this.itineraryId = itineraryId;
        this.titleItinerary = titleItinerary;
        this.descriptionItinerary = descriptionItinerary;
        this.difficultyLevel = difficultyLevel;
        this.hours = Integer.parseInt(hours);
        this.minutes = Integer.parseInt(minutes);
        this.seconds = Integer.parseInt(seconds);
        this.image_itinerary = image_itinerary;

    }

    public Itinerary() {
    }

    public String getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(String itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getTitleItinerary() {
        return titleItinerary;
    }

    public void setTitleItinerary(String titleItinerary) {
        this.titleItinerary = titleItinerary;
    }

    public void setDescriptionItinerary(String descriptionItinerary) {
        this.descriptionItinerary = descriptionItinerary;
    }

    public String getDescriptionItinerary() {
        return descriptionItinerary;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage_itinerary() {
        return image_itinerary;
    }

    public void setImage_itinerary(String image_itinerary) {
        this.image_itinerary = image_itinerary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
