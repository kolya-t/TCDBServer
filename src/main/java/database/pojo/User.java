package database.pojo;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * User Transfer Object
 */
@SuppressWarnings("UnusedDeclaration")
public class User implements Serializable {
    private long id;
    private String login;
    private String password;
    private String email;
    private String role;

    public User() {
        this.id = -1;
        this.login = "";
        this.password = "";
        this.email = "";
        this.role = "";
    }

    public User(@NotNull String login, @NotNull String password, @NotNull String email, @NotNull String role) {
        this.id = -1;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(long id, @NotNull String login, @NotNull String password, @NotNull String email, @NotNull String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
