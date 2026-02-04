package main;

/**
 * Singleton Session class to store the logged-in user's info
 */
public class Session {

    // The single instance of this class
    private static Session instance;

    private String name;
    private String email;
    private String u_status;
    private String u_role;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setU_status(String u_status) {
        this.u_status = u_status;
    }

    public void setU_role(String u_role) {
        this.u_role = u_role;
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getU_status() {
        return u_status;
    }

    public String getU_role() {
        return u_role;
    }

    // LOGOUT / CLEAR SESSION
    public void clear() {
        instance = null;
    }
}
