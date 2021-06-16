package com.example.hyreeee;

public class calist {
    private String Name;
    private String Img_URL;
    private String CUID;
    private String Description;
    private String Username;


    public calist(String name, String img_URL, String CUID, String description, String username) {
        Name = name;
        Img_URL = img_URL;
        this.CUID = CUID;
        Description = description;
        Username = username;
    }

    public calist() {
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

    public String getCUID() {
        return CUID;
    }

    public void setCUID(String CUID) {
        this.CUID = CUID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}


