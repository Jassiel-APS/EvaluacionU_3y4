package mx.edu.utez.secondapp.models.user;

public class Person {
    private Long id;
    private String name;
    private String surname;
    private String lastname;
    private String curp;
    private String birthday;
    private int status;

    public Person() {
    }

    public Person(Long id) {
        this.id = id;
    }

    public Person(Long id, String name, String surname, String lastname, String curp, String birthday, int status) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.curp = curp;
        this.birthday = birthday;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status == 1 ? "ACTIVO" : "INACTIVO";
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
