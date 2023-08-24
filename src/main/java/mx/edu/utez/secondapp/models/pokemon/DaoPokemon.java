package mx.edu.utez.secondapp.models.pokemon;

import mx.edu.utez.secondapp.models.user.DaoUser;
import mx.edu.utez.secondapp.models.user.Person;
import mx.edu.utez.secondapp.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPokemon {
    //Crear los métodos CRUD
    // All, One, Create, Update, Enabled-Disabled
    private Connection conn;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;

    public List<Pokemon> findAll() {
        List<Pokemon> pokemons = new ArrayList<>();
        Pokemon pokemon = null;
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT p.*," +
                    "       p2.id as personId," +
                    "       p2.name as personName," +
                    "       p2.surname," +
                    "       p2.lastname," +
                    "       t.id as typeId," +
                    "       t.description" +
                    " FROM pokemons p" +
                    "         INNER JOIN people p2 on p.people_id = p2.id" +
                    "         INNER JOIN types t on p.types_id = t.id;";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                pokemon = new Pokemon();
                pokemon.setId(rs.getLong("id"));
                pokemon.setName(rs.getString("name"));
                pokemon.setHeight(rs.getDouble("height"));
                pokemon.setWeight(rs.getDouble("weight"));
                pokemon.setPs(rs.getDouble("ps"));
                pokemon.setHp(rs.getDouble("hp"));
                pokemon.setPower(rs.getDouble("power"));
                pokemon.setAbilities(rs.getString("abilities"));
                Person person = new Person();
                person.setId(rs.getLong("personId"));
                person.setName(rs.getString("personName"));
                person.setSurname(rs.getString("surname"));
                person.setLastname(rs.getString("lastname"));
                pokemon.setPerson(person);
                PokemonType type = new PokemonType();
                type.setId(rs.getLong("typeId"));
                type.setDescription(rs.getString("description"));
                pokemon.setType(type);
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPokemon.class.getName())
                    .log(Level.SEVERE, "ERROR findAll" + e.getMessage());
        } finally {
            close();
        }
        return pokemons;
    }

    public Pokemon findOne(long id) {
        Pokemon pokemon = null;
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT p.*," +
                    "       p2.id as personId," +
                    "       p2.name as personName," +
                    "       p2.surname," +
                    "       p2.lastname," +
                    "       t.id as typeId," +
                    "       t.description" +
                    " FROM pokemons p" +
                    "         INNER JOIN people p2 on p.people_id = p2.id" +
                    "         INNER JOIN types t on p.types_id = t.id WHERE p.id = ? ORDER BY p.id DESC";
            ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                pokemon = new Pokemon();
                pokemon.setId(rs.getLong("id"));
                pokemon.setName(rs.getString("name"));
                pokemon.setHeight(rs.getDouble("height"));
                pokemon.setWeight(rs.getDouble("weight"));
                pokemon.setPs(rs.getDouble("ps"));
                pokemon.setHp(rs.getDouble("hp"));
                pokemon.setPower(rs.getDouble("power"));
                pokemon.setAbilities(rs.getString("abilities"));
                Person person = new Person();
                person.setId(rs.getLong("personId"));
                person.setName(rs.getString("personName"));
                person.setSurname(rs.getString("surname"));
                person.setLastname(rs.getString("lastname"));
                pokemon.setPerson(person);
                PokemonType type = new PokemonType();
                type.setId(rs.getLong("typeId"));
                type.setDescription(rs.getString("description"));
                pokemon.setType(type);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPokemon.class.getName())
                    .log(Level.SEVERE, "ERROR findAll" + e.getMessage());
        } finally {
            close();
        }
        return pokemon;
    }

    public Pokemon findFile(long id) {
        Pokemon pokemon = null;
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM pokemon_img WHERE pokemon_id = ?;";
            ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pokemon = new Pokemon();
                pokemon.setFileName(rs.getString("file_name"));
                pokemon.setFile(rs.getBytes("file"));
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPokemon.class.getName())
                    .log(Level.SEVERE, "ERROR findFile" + e.getMessage());
        } finally {
            close();
        }
        return pokemon;
    }


    public boolean save(Pokemon pokemon) throws SQLException {
        try {
            conn = new MySQLConnection().connect();
            conn.setAutoCommit(false); // Preparar la transaccion
            String query = "INSERT INTO pokemons (name, power, weight, height, abilities, hp, ps, people_id, types_id) VALUES (?,?,?,?,?,?,?,?,?);";
            ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, pokemon.getName());
            ps.setDouble(2, pokemon.getPower());
            ps.setDouble(3, pokemon.getWeight());
            ps.setDouble(4, pokemon.getHeight());
            ps.setString(5, pokemon.getAbilities());
            ps.setDouble(6, pokemon.getHp());
            ps.setDouble(7, pokemon.getPs());
            ps.setLong(8, pokemon.getPerson().getId());
            ps.setLong(9, pokemon.getType().getId());
            ps.execute();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);//ID pokemon
                String querySaveImg = "INSERT INTO pokemon_img (file, pokemon_id, file_name) VALUES (?,?,?);";
                ps = conn.prepareStatement(querySaveImg);
                ps.setBytes(1, pokemon.getFile());
                ps.setLong(2, id);
                ps.setString(3, pokemon.getFileName());
                ps.execute();
            }
            conn.commit(); // flush - COMMIT;
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DaoPokemon.class.getName())
                    .log(Level.SEVERE, "ERROR save " + e.getMessage());
            conn.rollback();
        } finally {
            close();
        }
        return false;
    }

    public boolean update(Pokemon pokemon) throws SQLException {
        try {
            conn = new MySQLConnection().connect();
            conn.setAutoCommit(false); // Preparar la transaccion
            String query = "UPDATE pokemons t SET t.name = ?, t.power = ?, t.weight = ?, t.height = ?, t.abilities = ?, t.hp = ?, t.ps = ?, t.people_id = ?, t.types_id  = ? WHERE t.id = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, pokemon.getName());
            ps.setDouble(2, pokemon.getPower());
            ps.setDouble(3, pokemon.getWeight());
            ps.setDouble(4, pokemon.getHeight());
            ps.setString(5, pokemon.getAbilities());
            ps.setDouble(6, pokemon.getHp());
            ps.setDouble(7, pokemon.getPs());
            ps.setLong(8, pokemon.getPerson().getId());
            ps.setLong(9, pokemon.getType().getId());
            ps.setLong(10, pokemon.getId());
            if (ps.executeUpdate() == 1) {
                String querySaveImg = "UPDATE pokemon_img p SET p.file = ?, p.file_name= ? WHERE p.pokemon_id = ?";
                ps = conn.prepareStatement(querySaveImg);
                ps.setBytes(1, pokemon.getFile());
                ps.setString(2, pokemon.getFileName());
                ps.setLong(3, pokemon.getId());
                ps.execute();
            }
            conn.commit(); // flush - COMMIT;
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DaoPokemon.class.getName()).log(Level.SEVERE, "ERROR save " + e.getMessage());
            conn.rollback();
        } finally {
            close();
        }
        return false;
    }

    public boolean changeStatus(Pokemon pokemon) throws SQLException {
        try {
            conn = new MySQLConnection().connect();
            String query = "UPDATE pokemons t SET t.status = ? WHERE t.id = ?;";
            ps = conn.prepareStatement(query);
            ps.setInt(1, pokemon.getStatus());
            ps.setLong(2, pokemon.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(DaoPokemon.class.getName()).log(Level.SEVERE, "ERROR changeStatus " + e.getMessage());
        } finally {
            close();
        }
        return false;
    }

    public List<PokemonType> types() {
        List<PokemonType> list = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM types;";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                PokemonType type = new PokemonType();
                type.setId(rs.getLong("id"));
                type.setDescription(rs.getString("description"));
                list.add(type);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName())
                    .log(Level.SEVERE,
                            "Get types : " + e.getMessage());
        } finally {
            close();
        }
        return list;
    }

    public void close() {
        try {
            if (conn != null)
                conn.close();
            if (ps != null)
                ps.close();
            if (cs != null)
                cs.close();
            if (rs != null)
                rs.close();
        } catch (SQLException e) {

        }
    }
}
