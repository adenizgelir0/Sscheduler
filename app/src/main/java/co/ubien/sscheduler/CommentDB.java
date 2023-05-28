package co.ubien.sscheduler;

public class CommentDB {
    String pid;


    String uid;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public CommentDB(String pid, String uid, String content, String username) {
        this.pid = pid;
        this.uid = uid;
        this.content = content;
        this.username = username;
    }
    public CommentDB()
    {
        this.pid = "";
        this.uid = "";
        this.content = "";
        this.username = "";
    }
    String content;
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
