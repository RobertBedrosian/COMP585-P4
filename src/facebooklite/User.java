package facebooklite;

public class User {
    private String firstName;
    private String lastName;
    private int age;
    private String userName;
    private byte[] salt;
    private byte[] hashedPassword;
    private String email;

    public User (){

    }

    //This constructor is to used when creating a new profile
    public User (String firstName, String lastName, int age, String email, String userName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.userName = userName;

        Password tmpPass = new Password(password);
        this.salt = tmpPass.getSalt();
        this.hashedPassword = tmpPass.getHashedPassword();
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public int getAge(){
        return age;
    }

    public String getUserName(){
        return userName;
    }

    public byte[] getSalt(){
        return salt;
    }

    public byte[] getHashedPassword(){
        return hashedPassword;
    }

    public String getEmail(){
        return email;
    }


    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setSalt(byte [] salt){
        this.salt = salt;
    }

    public void setHashedPassword(byte [] hashedPassword){
        this.hashedPassword = hashedPassword;
    }
}
