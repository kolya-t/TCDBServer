package dbservice.datasets;

public class User {
    private int id;
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

    public User(int id, String login, String name, String password, String email) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String login, String name, String password, String email) {
        this.id = 0;
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
