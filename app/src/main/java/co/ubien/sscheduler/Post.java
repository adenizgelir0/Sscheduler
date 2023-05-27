package co.ubien.sscheduler;

public class Post {
    public int like;
    public int dislike;
    public User user;
    public Schedule schedule;
    public String title;
    public String description;

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

