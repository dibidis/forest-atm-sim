package ATM;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    private String password;

    //Used for serializable
    User(){}

    public User(String username, String password){
        setUsername(username);
        setPassword(password);
    }

    void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
}
