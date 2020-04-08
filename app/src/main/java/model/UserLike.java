package model;

public class UserLike {
    boolean like;

    public UserLike() { }

    public UserLike(boolean like) {
        this.like = like;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
