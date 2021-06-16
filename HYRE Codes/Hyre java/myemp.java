package com.example.hyreeee;

public class myemp {
    private String Name;
    private String FUID;
    private String Img_URL;

    public myemp(String name, String FUID, String img_URL) {
        Name = name;
        this.FUID = FUID;
        Img_URL = img_URL;
    }

    public myemp() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFUID() {
        return FUID;
    }

    public void setFUID(String FUID) {
        this.FUID = FUID;
    }

    public String getImg_URL() {
        return Img_URL;
    }

    public void setImg_URL(String img_URL) {
        Img_URL = img_URL;
    }
}
