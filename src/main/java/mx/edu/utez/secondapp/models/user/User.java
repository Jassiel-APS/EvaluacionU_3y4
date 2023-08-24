package mx.edu.utez.secondapp.models.user;

import mx.edu.utez.secondapp.models.role.Role;

public class User {
    private Long id;
    private String username;
    private String password;
    private String token;
    private int status;

    private Person person;
    private Role role;

    public User() {
    }

    public User(Long id, String username, String password, String token, int status, Person person, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.token = token;
        this.status = status;
        this.person = person;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status == 1 ? "ACTIVO" : "INACTIVO";
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", status=" + status +
                ", person=" + person +
                ", role=" + role +
                '}';
    }
}
