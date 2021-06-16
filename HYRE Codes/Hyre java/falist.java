package com.example.hyreeee;

public class falist {
    private String Name;
    private String Img_URL;
    private String FUID;
    private String Experience;
    private String Skills;
    private String Username;

    public falist(String name, String img_URL, String FUID, String experience, String skills, String username) {
        Name = name;
        Img_URL = img_URL;
        this.FUID = FUID;
        Experience = experience;
        Skills = skills;
        Username = username;
    }

    public falist() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImg_URL() {
        return Img_URL;
    }

    public void setImg_URL(String img_URL) {
        Img_URL = img_URL;
    }

    public String getFUID() {
        return FUID;
    }

    public void setFUID(String FUID) {
        this.FUID = FUID;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}