package models;

public class User extends Person implements IDisplay
{
    private String status;
    private boolean isAgeVis;
    private Password password;


    //This constructor is used only when a new account is being set up
    public User(String firstName , String lastName , int age, String password)
    {
        super(firstName , lastName , age);
        this.password = new Password(password);
        status = "";
        isAgeVis = true;
    }

    //used when loading an existing account
    public User(String firstName , String lastName , int age, String salt, String hashedPass)
    {
        super(firstName , lastName , age);
        this.password = new Password(salt, hashedPass);
        status = "";
        isAgeVis = true;
    }
    
    public void display()
    {
        Util.print(getFirstName() + " " + getLastName());
        if (isAgeVis)
        {
            Util.print(String.valueOf(getAge()) + " Years old");
        }
        
        Util.print("Status: " + getStatus());
    }
    
    public void toggleVis()
    {
        isAgeVis = !isAgeVis;
    }
    
    // getter
    public String getStatus()
    {
        return status;
    }
    public String getSalt(){
        return password.getSalt();
    }
    public String getHashedPass(){
        return password.getHashedPassword();
    }
    
    // setter
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getFullName()
    {
        return getFirstName() + " " + getLastName();
    }
    
    public String getFormattedInfo()
    {
        return getFirstName() + ";;" + getLastName() + ";;" + getAge() + ";;" + getSalt() + ";;" + getHashedPass();
    }
    
    public boolean ageVis()
    {
        return isAgeVis;
    }
}