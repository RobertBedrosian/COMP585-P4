package facebooklite;

public interface UserDaoInterface {
    void insertUser(User user);

    void getUser(int userID);

    void updateUser(User user);

    void deleteUser(int userID);
}