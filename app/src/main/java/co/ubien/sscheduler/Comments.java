package co.ubien.sscheduler;

import java.util.ArrayList;

public class Comments {
    private static ArrayList<String> commentsMaded;

    public Comments()
    {
        commentsMaded = new ArrayList<String>();

        // Deneme birki
        commentsMaded.add("sa");
        commentsMaded.add("asdaa");
        commentsMaded.add("sa");
        commentsMaded.add("asdaa");
        commentsMaded.add("sa");
        commentsMaded.add("asdaa");
        commentsMaded.add("sa");
        commentsMaded.add("asdaa");
        commentsMaded.add("sa");
        commentsMaded.add("asdaa");
        commentsMaded.add("sa");
        commentsMaded.add("asdaa");
    }

    // getter methods
    public ArrayList<String> getComment()
    {
        return commentsMaded;
    }

    // setter methods

    public void makeComment(String comment)
    {
        commentsMaded.add(comment);
    }
}
