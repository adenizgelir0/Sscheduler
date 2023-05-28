package co.ubien.sscheduler;

public class Comment {

    private String comment;
    private User user;

    public Comment(String comment, User user){
        this.comment = comment;
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }
}
