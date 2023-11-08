import java.util.Map;
import java.util.HashMap;

public class Database{
    private static Database instance;
    private Map<String,String> userPassword;

    private Database(){
        this.userPassword = new HashMap<>();
        userPassword.put("Pippo", "password");
        userPassword.put("Pippo2", "password2");
    }

    public Map<String,String> getDb(){return this.userPassword;}

    public static Database getInstance(){
        if(instance == null)
            instance = new Database();
        return instance;
    }

    public boolean isRegistered(String user){
        if(userPassword.get(user) != "null")
            return true;
        return false;
    }

    public boolean register(String user, String pass){
        if(isRegistered(user) == false)
            return false;
        
        userPassword.put(user, pass);
        return true;
    }

    public boolean unregister(String user){
        if(isRegistered(user) == false)
            return false;
        userPassword.remove(user);
        return true;
    }

    public void printDb(){
        for (Map.Entry<String, String> entry : userPassword.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }   
    }
}