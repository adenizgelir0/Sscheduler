package co.ubien.sscheduler;

public class User {
    private String username;
    private String name;
    private String bio = "Hi there :)";
    private String sid = "";
    public User(){
        this.username = "test";
        this.name = username;
    }
    public User(String username) {
        this.username = username;
        this.name = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
