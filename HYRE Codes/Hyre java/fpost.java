package com.example.hyreeee;

public class fpost {
    private String Img_URL;
    private String Name;
    private String Description;
    private String Skills;
    private String Ratings;
    private String FUID;

    public fpost(String img_URL, String name, String description, String skills, String ratings, String FUID) {
        Img_URL = img_URL;
        Name = name;
        Description = description;
        Skills = skills;
        Ratings = ratings;
        this.FUID = FUID;
    }

    public fpost() {
    }

    public String getImg_URL() {
        return Img_URL;
    }

    public void setImg_URL(String img_URL) {
        Img_URL = img_URL;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public String getFUID() {
        return FUID;
    }

    public void setFUID(String FUID) {
        this.FUID = FUID;
    }
}
