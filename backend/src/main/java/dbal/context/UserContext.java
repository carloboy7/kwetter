package dbal.context;

import entities.user.User;

public interface UserContext extends BaseContext<User> {
    void deleteFollower(int follower, int following);
}
