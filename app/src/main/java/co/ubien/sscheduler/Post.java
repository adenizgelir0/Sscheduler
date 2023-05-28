package co.ubien.sscheduler;

import java.util.ArrayList;

public class Post {
    public int like;
    public int dislike;
    public User user;
    public Schedule schedule;
    public String title;
    public String description;

    private ArrayList<Comment> comments = new ArrayList<>();

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public Post(String title, String description, Schedule schedule, User user) {
        this.title = title;
        this.description = description;
        this.schedule = schedule;
        this.user = user;
        this.like = 0;
        this.dislike = 0;

    }
    //GETTERS
    public int getDisLike() {
        return dislike;
    }

    public int getLike() {
        return like;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public void addComment(String comment, User user){
        Comment c = new Comment(comment,user);
        this.comments.add(c);
    }

    public String getDescription() {
        return description;
    }

    //SETTERS
    public void setDisLike(int dislike) {
        this.dislike = dislike;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //Specific modifiers
    public void liked() {
        this.like++;
    }
    public void disLiked() {
        this.like++;
    }
}

