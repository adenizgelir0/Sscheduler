package co.ubien.sscheduler;

import android.view.View;
import android.widget.ImageView;

public class User {
    private String username;
    private String name;
    private String bio = "Hi there :)";
    private String sid = "";
    private int avatarIndex;

    public int getAvatarIndex() {
        return avatarIndex;
    }

    public User(){
        this.username = "test";
        this.name = username;
    }
    public User(String username) {
        this.username = username;
        this.name = username;
        avatarIndex = 0;
    }
    public User(String username, int avatarIndex) {
        this.username = username;
        this.name = username;
        this.avatarIndex = avatarIndex;
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

    public void setAvatar(int indexAvatar){
        this.avatarIndex = indexAvatar;
    }
    public ImageView getAvatar(View rootview){
        ImageView avatar = new ImageView(rootview.getContext());
        avatar.setImageResource(R.drawable.man);

        if (avatarIndex == 1){
            avatar.setImageResource(R.drawable.cat);

        }
        else if (avatarIndex == 2){
            avatar.setImageResource(R.drawable.gamer);

        }
        else if (avatarIndex == 3){
            avatar.setImageResource(R.drawable.woman1);
        }
        else if (avatarIndex == 4){
            avatar.setImageResource(R.drawable.man1);

        }
        else if (avatarIndex == 5){
            avatar.setImageResource(R.drawable.man2);

        }
        else if (avatarIndex == 6){
            avatar.setImageResource(R.drawable.man3);

        }
        else if (avatarIndex == 7){
            avatar.setImageResource(R.drawable.profile);

        }
        else if (avatarIndex == 8){
            avatar.setImageResource(R.drawable.user);

        }
        else if (avatarIndex == 9){
            avatar.setImageResource(R.drawable.woman);

        }

        return avatar;
    }



}
