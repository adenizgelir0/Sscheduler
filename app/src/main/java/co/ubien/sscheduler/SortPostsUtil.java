package co.ubien.sscheduler;

import java.util.Comparator;

public class SortPostsUtil implements Comparator<Post> {

    @Override
    public int compare(Post post, Post t1) {
        int result = post.getLike() - t1.getLike();
        return result * -1;
    }
}
