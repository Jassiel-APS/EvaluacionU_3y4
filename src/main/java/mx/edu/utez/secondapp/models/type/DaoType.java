package mx.edu.utez.secondapp.models.type;

import mx.edu.utez.secondapp.models.pokemon.Pokemon;
import mx.edu.utez.secondapp.models.pokemon.PokemonType;
import mx.edu.utez.secondapp.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoType {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public List<PokemonType> findAll() {
        List<PokemonType> types = new ArrayList<>();
        PokemonType type = null;
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM types ORDER BY id DESC";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                type = new PokemonType(rs.getLong("id"), rs.getString("description"));
                types.add(type);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoType.class.getName()).log(Level.SEVERE, "Error findAll " + e.getMessage());
        } finally {
            close();
        }
        return types;
    }

    public PokemonType findOne(long id) {
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM types WHERE id = ?;";
            ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new PokemonType(rs.getLong("id"), rs.getString("description"));
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoType.class.getName()).log(Level.SEVERE, "Error findOne " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    public PokemonType findByDescription(String description) {
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM types WHERE description = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, description);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new PokemonType(rs.getLong("id"), rs.getString("description"));
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoType.class.getName()).log(Level.SEVERE, "Error findOne " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    public PokemonType findByDescriptionAndNotEqual(String description, long id) {
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM types WHERE description = ? AND id != ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, description);
            ps.setLong(2, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new PokemonType(rs.getLong("id"), rs.getString("description"));
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoType.class.getName()).log(Level.SEVERE, "Error findOne " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    public boolean save(PokemonType type) {
        try {
            conn = new MySQLConnection().connect();
            String query = "INSERT INTO types (description) VALUES (?);";
            ps = conn.prepareStatement(query);
            ps.setString(1, type.getDescription());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(DaoType.class.getName()).log(Level.SEVERE, "Error save " + e.getMessage());
        } finally {
            close();
        }
        return false;
    }

    public boolean update(PokemonType type) {
        try {
            conn = new MySQLConnection().connect();
            String query = "UPDATE types SET description = ? WHERE id = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, type.getDescription());
            ps.setLong(2, type.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(DaoType.class.getName()).log(Level.SEVERE, "Error update " + e.getMessage());
        } finally {
            close();
        }
        return false;
    }


    public void close() {
        try {
            if (conn != null) conn.close();
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
