class Profile
{
    private User user;  // declared
    private Wall wall;
    private Friends friends;

    public Profile(String firstName , String lastName , int age, String salt, String hashedPass)
    {
        user = new User(firstName , lastName , age, salt, hashedPass);  // initialized by constructor
        wall = new Wall();
        friends = new Friends();
    }
    
    public Profile(String firstName , String lastName , int age, String password)
    {
        user = new User(firstName , lastName , age, password);  // initialized by constructor
        wall = new Wall();
        friends = new Friends();
    }
    
    public void printProfile()
    {
        // call all display() methods for user , wall , friends
        user.display();
        wall.display();
        friends.display();
    }
    
    public void toggleAge()
    {
        user.toggleVis();
    }
    
    public void toggleWall()
    {
        wall.toggleVis();
    }
    
    public void toggleFriends()
    {
        friends.toggleVis();
    }
    
    public void setStatus(String status)
    {
        user.setStatus(status);
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void post(String input)
    {
        wall.addPost(input);
    }
    
    public Friends getFriends()
    {
        return friends;
    }
    
    public Wall getWall()
    {
        return wall;
    }
}