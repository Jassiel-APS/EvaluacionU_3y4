package mx.edu.utez.secondapp.models.pokemon;

public class PokemonType {
    private Long id;
    private String description;

    public PokemonType() {
    }

    public PokemonType(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public PokemonType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
