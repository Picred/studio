import java.io.Serializable;

public class Message implements Serializable 
{
    public String operation;
    public String username;
    public String password;

    public Message(String op, String usr, String pass) 
    {
        this.operation = op;
        this.username = usr;
        this.password = pass;
    }

    public String getOperation() 
    {
        return operation;
    }

    public void setOperation(String operation) 
    {
        this.operation = operation;
    }

    public String getUsername() 
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }
}
