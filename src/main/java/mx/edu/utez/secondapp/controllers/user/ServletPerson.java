package mx.edu.utez.secondapp.controllers.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.secondapp.models.pokemon.DaoPokemon;
import mx.edu.utez.secondapp.models.role.Role;
import mx.edu.utez.secondapp.models.user.DaoUser;
import mx.edu.utez.secondapp.models.user.Person;
import mx.edu.utez.secondapp.models.user.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "ServletPerson", urlPatterns = {
        "/api/people/all",
        "/api/people/create",
        "/api/people/save",
        "/api/people/edit",
        "/api/people/update",
        "/api/people/enable"
})
public class ServletPerson extends HttpServlet {
    private String action, redirect, id, name, surname, lastname, curp, personStatus, personId, birthday, roleId, username, password;
    private Role role;
    private User user;
    private Person person;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        switch (action) {
            case "/api/people/all":
                req.setAttribute("users", new DaoUser().findAll());
                redirect = "/views/user/index.jsp";
                break;
            case "/api/people/create":
                req.setAttribute("roles", new DaoUser().findAllRoles());
                redirect = "/views/user/create.jsp";
                break;
            case "/api/people/edit":
                try {
                    id = req.getParameter("id");
                    req.setAttribute("user", new DaoUser().findOne(Long.parseLong(id)));
                    req.setAttribute("roles", new DaoUser().findAllRoles());
                    redirect = "/views/user/update.jsp";
                } catch (Exception e) {
                    redirect = "/api/people/all";
                }
                break;
        }
        req.getRequestDispatcher(redirect).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        action = req.getServletPath();
        switch (action) {
            case "/api/people/save":
                try {
                    name = req.getParameter("name");
                    surname = req.getParameter("surname");
                    lastname = req.getParameter("lastname");
                    curp = req.getParameter("curp");
                    birthday = req.getParameter("birthday");
                    username = req.getParameter("username");
                    password = req.getParameter("password");
                    roleId = req.getParameter("role");
                    person = new Person();
                    person.setName(name);
                    person.setSurname(surname);
                    person.setLastname(lastname);
                    person.setCurp(curp);
                    person.setBirthday(birthday);
                    person.setStatus(1);
                    role = new Role();
                    role.setId(roleId != null ? Long.parseLong(roleId) : 0L);
                    user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setPerson(person);
                    user.setRole(role);
                    if (new DaoUser().save(user)) {
                        redirect = "/api/people/all?result=false&message=" + URLEncoder
                                .encode("Persona registrada correctamente",
                                        StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("Error");
                    }
                } catch (Exception e) {
                    redirect = "/api/people/all?result=true&message=" + URLEncoder
                            .encode("Ocurrió un error!",
                                    StandardCharsets.UTF_8);
                    e.printStackTrace();
                }
                break;
            case "/api/people/update":
                try {
                    name = req.getParameter("name");
                    surname = req.getParameter("surname");
                    lastname = req.getParameter("lastname");
                    curp = req.getParameter("curp");
                    birthday = req.getParameter("birthday");
                    username = req.getParameter("username");
                    password = req.getParameter("password");
                    roleId = req.getParameter("role");
                    id = req.getParameter("id");
                    personId = req.getParameter("person");
                    person = new Person();
                    person.setName(name);
                    person.setSurname(surname);
                    person.setLastname(lastname);
                    person.setCurp(curp);
                    person.setBirthday(birthday);
                    person.setId(personId != null ? Long.parseLong(personId) : 0L);
                    role = new Role();
                    role.setId(roleId != null ? Long.parseLong(roleId) : 0L);
                    user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setPerson(person);
                    user.setRole(role);
                    user.setId(id != null ? Long.parseLong(id) : 0L);
                    if (new DaoUser().update(user)) {
                        redirect = "/api/people/all?result=false&message=" + URLEncoder
                                .encode("Pokémon registrado correctamente",
                                        StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("Error");
                    }
                } catch (Exception e) {
                    redirect = "/api/people/all?result=true&message=" + URLEncoder
                            .encode("Ocurrió un error!",
                                    StandardCharsets.UTF_8);
                    e.printStackTrace();
                }
                break;
            case "/api/people/enable":
                try {
                    id = req.getParameter("id");
                    personId = req.getParameter("personId");
                    user = new User();
                    user.setId(id != null ? Long.parseLong(id) : 0L);
                    user.setPerson(new Person(personId != null ? Long.parseLong(personId) : 0L));
                    if (id != null && personId != null && new DaoUser().enabled(user)) {
                        redirect = "/api/people/all?result=false&message=" + URLEncoder
                                .encode("Pokémon registrado correctamente",
                                        StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("Error");
                    }
                } catch (Exception e) {
                    redirect = "/api/people/all?result=true&message=" + URLEncoder
                            .encode("Ocurrió un error!",
                                    StandardCharsets.UTF_8);
                    e.printStackTrace();
                }
                break;
            default:
                redirect = "/api/people/all";
        }
        resp.sendRedirect(req.getContextPath() + redirect);
    }
}
