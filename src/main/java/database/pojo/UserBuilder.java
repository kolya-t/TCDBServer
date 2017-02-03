package database.pojo;

@SuppressWarnings("UnusedDeclaration")
public class UserBuilder {
    private long id = -1;
    private String login = null;
    private String name = null;
    private String password = null;
    private String email = null;

    public UserBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        return new User(id, login, name, password, email);
    }
}
