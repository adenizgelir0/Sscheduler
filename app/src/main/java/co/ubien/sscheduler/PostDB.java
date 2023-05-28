package co.ubien.sscheduler;

public class PostDB {
    private String title;
    private String desc;
    private String sid;
    private String uid;
    private int likes;
    private int dislikes;

    public PostDB(String title, String desc, String sid, String uid, int likes, int dislikes){
        this.title = title;
        this.desc = desc;
        this.sid = sid;
        this.uid = uid;
        this.likes = likes;
        this.dislikes = dislikes;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
