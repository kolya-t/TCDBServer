package database.pojo;

import java.io.Serializable;

/**
 * User Transfer Object
 */
@SuppressWarnings("UnusedDeclaration")
public class User implements Serializable {
    private long id;
    private String login;
    private String name;
    private String password;
    private String email;

    public User() {
        this.id = 0;
        this.login = "";
        this.name = "";
        this.password = "";
        this.email = "";
    }

    public User(long id, String login, String name, String password, String email) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}