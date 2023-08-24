package mx.edu.utez.secondapp.models.pokemon;

import mx.edu.utez.secondapp.models.user.Person;

public class Pokemon {
    private Long id;
    private String name;
    private Double ps;
    private Double hp;
    private Double power;
    private Double weight;
    private Double height;
    private String abilities;
    private Person person;
    private PokemonType type;
    private String fileName;
    private int status;

    private byte[] file;

    public Pokemon() {
    }

    public Pokemon(Long id, String name, Double ps, Double hp, Double power, Double weight, Double height, String abilities, Person person, PokemonType type) {
        this.id = id;
        this.name = name;
        this.ps = ps;
        this.hp = hp;
        this.power = power;
        this.weight = weight;
        this.height = height;
        this.abilities = abilities;
        this.person = person;
        this.type = type;
    }

    public byte[] getFile() {
        return file;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFile(byte[] file) {
        this.file = file;
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

    public Double getPs() {
        return ps;
    }

    public void setPs(Double ps) {
        this.ps = ps;
    }

    public Double getHp() {
        return hp;
    }

    public void setHp(Double hp) {
        this.hp = hp;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }
}
