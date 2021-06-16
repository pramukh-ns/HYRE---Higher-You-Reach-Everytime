package com.example.hyreeee;

public class cpost {
    private String Img_URL;
    private String PID;
    private String Name;
    private String Project_Title;
    private String Project_Description;
    private String Project_Role;
    private String Project_Skills;
    private String Project_Duration;

    public cpost(String img_URL, String PID, String name, String project_Title, String project_Description, String project_Role, String project_Skills, String project_Duration) {
        Img_URL = img_URL;
        this.PID = PID;
        Name = name;
        Project_Title = project_Title;
        Project_Description = project_Description;
        Project_Role = project_Role;
        Project_Skills = project_Skills;
        Project_Duration = project_Duration;
    }

    public cpost() {
    }

    public String getImg_URL() {
        return Img_URL;
    }

    public void setImg_URL(String img_URL) {
        Img_URL = img_URL;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProject_Title() {
        return Project_Title;
    }

    public void setProject_Title(String project_Title) {
        Project_Title = project_Title;
    }

    public String getProject_Description() {
        return Project_Description;
    }

    public void setProject_Description(String project_Description) {
        Project_Description = project_Description;
    }

    public String getProject_Role() {
        return Project_Role;
    }

    public void setProject_Role(String project_Role) {
        Project_Role = project_Role;
    }

    public String getProject_Skills() {
        return Project_Skills;
    }

    public void setProject_Skills(String project_Skills) {
        Project_Skills = project_Skills;
    }

    public String getProject_Duration() {
        return Project_Duration;
    }

    public void setProject_Duration(String project_Duration) {
        Project_Duration = project_Duration;
    }
}
