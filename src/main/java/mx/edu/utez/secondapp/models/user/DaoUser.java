package mx.edu.utez.secondapp.models.user;

import com.mysql.cj.log.Log;
import mx.edu.utez.secondapp.models.role.Role;
import mx.edu.utez.secondapp.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUser {
    private Connection conn;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;

    public User loadUserByUsernameAndPassword(String username,
                                              String password) {
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT u.id, u.username," +
                    "p.name, p.surname, ifnull(p.lastname, '') as lastname," +
                    "    r.description  FROM users u " +
                    "INNER JOIN people p ON p.id = u.people_id " +
                    "    INNER JOIN roles r ON r.id = u.roles_id " +
                    "WHERE u.username = ? AND u.password = ? AND u.status = 1;";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                Person person = new Person();
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setLastname(rs.getString("lastname"));
                user.setPerson(person);
                Role role = new Role();
                role.setDescription(rs.getString("description"));
                user.setRole(role);
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName())
                    .log(Level.SEVERE,
                            "Credentials mismatch: " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT u.id, u.username," +
                    "p.name, p.surname, ifnull(p.lastname, '') as lastname, p.curp, p.id as personId, p.status, " +
                    "    r.description, u.status as userStatus  FROM users u " +
                    "INNER JOIN people p ON p.id = u.people_id " +
                    "    INNER JOIN roles r ON r.id = u.roles_id WHERE u.id not in (1) ORDER BY u.id DESC";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setStatus(rs.getInt("userStatus"));
                Person person = new Person();
                person.setId(rs.getLong("personId"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setLastname(rs.getString("lastname"));
                person.setCurp(rs.getString("curp"));
                person.setStatus(rs.getInt("status"));
                user.setPerson(person);
                Role role = new Role();
                role.setDescription(rs.getString("description"));
                user.setRole(role);
                users.add(user);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName())
                    .log(Level.SEVERE,
                            "Credentials mismatch: " + e.getMessage());
        } finally {
            close();
        }
        return users;
    }

    public User findOne(long id) {
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT u.id, u.username," +
                    "p.name, p.surname, ifnull(p.lastname, '') as lastname, p.curp, p.id as personId, p.status, p.birthday, " +
                    "    r.description, u.password, r.id as roleId  FROM users u " +
                    "INNER JOIN people p ON p.id = u.people_id " +
                    "    INNER JOIN roles r ON r.id = u.roles_id WHERE u.id = ? ORDER BY u.id DESC";
            ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                Person person = new Person();
                person.setId(rs.getLong("personId"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setLastname(rs.getString("lastname"));
                person.setBirthday(rs.getString("birthday"));
                person.setCurp(rs.getString("curp"));
                person.setStatus(rs.getInt("status"));
                user.setPerson(person);
                Role role = new Role();
                role.setId(rs.getLong("roleId"));
                role.setDescription(rs.getString("description"));
                user.setRole(role);
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName())
                    .log(Level.SEVERE,
                            "find One: " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    public boolean existsByUsername(String username) {
        try {
            conn = new MySQLConnection().connect();
            conn.setAutoCommit(false);
            String query = "SELECT * FROM users WHERE username = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error existsByUsername " + e.getMessage());
        } finally {
            close();
        }
        return false;
    }

    public boolean existsByUsernameNotEqual(String username, long id) {
        try {
            conn = new MySQLConnection().connect();
            conn.setAutoCommit(false);
            String query = "SELECT * FROM users WHERE username = ? AND id != ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setLong(2, id);
            rs = ps.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error existsByUsername " + e.getMessage());
        } finally {
            close();
        }
        return false;
    }

    public boolean save(User user) throws SQLException {
        try {
            conn = new MySQLConnection().connect();
            conn.setAutoCommit(false);
            String query = "INSERT INTO people (name, surname, lastname, curp, birthday, status) VALUES (?,?,?,?,?, 1);";
            ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getPerson().getName());
            ps.setString(2, user.getPerson().getSurname());
            ps.setString(3, user.getPerson().getLastname());
            ps.setString(4, user.getPerson().getCurp());
            ps.setString(5, user.getPerson().getBirthday());
            ps.execute();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                query = "INSERT INTO users (username, password, status, token, roles_id, people_id) VALUES (?,?,1, null, 2, ?);";
                ps = conn.prepareStatement(query);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setLong(3, id);
                ps.execute();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error save " + e.getMessage());
            conn.rollback();
        } finally {
            close();
        }
        return false;
    }

    public boolean update(User user) throws SQLException {
        try {
            conn = new MySQLConnection().connect();
            conn.setAutoCommit(false);
            String query = "UPDATE people SET name = ?, surname = ?, lastname = ?, curp = ?, birthday = ? WHERE id = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getPerson().getName());
            ps.setString(2, user.getPerson().getSurname());
            ps.setString(3, user.getPerson().getLastname());
            ps.setString(4, user.getPerson().getCurp());
            ps.setString(5, user.getPerson().getBirthday());
            ps.setLong(6, user.getPerson().getId());
            ps.execute();
            query = "UPDATE users SET username = ?, password = ?, roles_id = ? WHERE id = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setLong(3, user.getRole().getId());
            ps.setLong(4, user.getId());
            ps.execute();
            conn.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error update " + e.getMessage());
            conn.rollback();
        } finally {
            close();
        }
        return false;
    }

    public boolean enabled(User user) throws SQLException {
        try {
            conn = new MySQLConnection().connect();
            conn.setAutoCommit(false);
            String query = "UPDATE people SET status = !status WHERE id = ?;";
            ps = conn.prepareStatement(query);
            ps.setLong(1, user.getPerson().getId());
            ps.execute();
            query = "UPDATE users SET status = !status WHERE id = ?;";
            ps = conn.prepareStatement(query);
            ps.setLong(1, user.getId());
            ps.execute();
            conn.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error update " + e.getMessage());
            conn.rollback();
        } finally {
            close();
        }
        return false;
    }

    public List<Person> people() {
        List<Person> list = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM people ORDER BY id DESC;";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setLastname(rs.getString("lastname"));
                list.add(person);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName())
                    .log(Level.SEVERE,
                            "Get people : " + e.getMessage());
        } finally {
            close();
        }
        return list;
    }

    public List<Role> findAllRoles() {
        List<Role> list = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM roles ORDER BY id DESC;";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setDescription(rs.getString("description"));
                System.out.println(role);
                list.add(role);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName())
                    .log(Level.SEVERE,
                            "Find All Roles : " + e.getMessage());
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
            e.printStackTrace();
        }
    }


}
