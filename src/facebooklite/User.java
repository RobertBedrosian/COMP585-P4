package facebooklite;

public class User {
    private String firstName;
    private String lastName;
    private int age;
    private String userName;
    private byte[] salt;
    private byte[] hashedPassword;


    //This constructor is to used when creating a new profile
    public User (String firstName, String lastName, int age, String userName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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



}
