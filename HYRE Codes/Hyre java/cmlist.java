package com.example.hyreeee;

public class cmlist {
    private String Name;
    private String FUID;
    private String Img_URL;

    public cmlist(String name, String FUID, String img_URL) {
        Name = name;
        this.FUID = FUID;
        Img_URL = img_URL;
    }

    public cmlist() {
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
