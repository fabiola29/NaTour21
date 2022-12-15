package com.example.natour2022.model;

public class Review {

    private  String reviewId;
    private  String itineraryId;
    private  String emailUserReview;
    private  String titleReview;
    private  String descriptionReview;
    private  int voteItinerary;
    private String userId;
    private  String imageReview;


    public Review(String titleReview,
                  String email,
                  String imageReview,
                  String voteItinerary,
                  String descriptionReview) {

        this.titleReview = titleReview;
        this.emailUserReview = email;
        this.imageReview = imageReview;
        this.voteItinerary = Integer.parseInt(voteItinerary);
        this.descriptionReview = descriptionReview;
    }

    public Review() {
    }

    public Review(String titleReview, String descriptionReview, String voteItinerary, String email, String userId, String reviewId, String itineraryId) {
        this.titleReview = titleReview;
        this.emailUserReview = email;
        this.imageReview = imageReview;
        this.voteItinerary = Integer.parseInt(voteItinerary);
        this.descriptionReview = descriptionReview;
        this.reviewId = reviewId;
        this.userId = userId;
        this.itineraryId = itineraryId;


    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(String itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getEmailUserReview() {
        return emailUserReview;
    }

    public void setEmailUserReview(String emailUserReview) {
        this.emailUserReview = emailUserReview;
    }

    public String getTitleReview() {
        return titleReview;
    }

    public void setTitleReview(String titleReview) {
        this.titleReview = titleReview;
    }

    public String getDescriptionReview() {
        return descriptionReview;
    }

    public void setDescriptionReview(String descriptionReview) {
        this.descriptionReview = descriptionReview;
    }

    public int getVoteItinerary() {
        return voteItinerary;
    }

    public void setVoteItinerary(int voteItinerary) {
        this.voteItinerary = voteItinerary;
    }

    public String getImageReview() {
        return imageReview;
    }

    public void setImageReview(String imageReview) {
        this.imageReview = imageReview;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
