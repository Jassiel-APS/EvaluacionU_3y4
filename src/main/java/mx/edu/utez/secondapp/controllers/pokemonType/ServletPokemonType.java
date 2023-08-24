package mx.edu.utez.secondapp.controllers.pokemonType;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.secondapp.models.pokemon.PokemonType;
import mx.edu.utez.secondapp.models.type.DaoType;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.server.ExportException;

@WebServlet(name = "PokemonType", urlPatterns = {
        "/api/type/all",
        "/api/type/create",
        "/api/type/save",
        "/api/type/edit",
        "/api/type/update",
})
public class ServletPokemonType extends HttpServlet {
    private String action;
    private String redirect;
    private String id, description;
    private PokemonType type;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        switch (action) {
            case "/api/type/all":
                req.setAttribute("types", new DaoType().findAll());
                redirect = "/views/pokemon-type/index.jsp";
                break;
            case "/api/type/create":
                redirect = "/views/pokemon-type/create.jsp";
                break;
            case "/api/type/edit":
                id = req.getParameter("id");
                type = new DaoType().findOne(id != null ? Long.parseLong(id) : 0);
                if (type != null) {
                    req.setAttribute("type", type);
                    redirect = "/views/pokemon-type/update.jsp";
                } else {
                    redirect = "/api/type/all?result=false&message=" + URLEncoder
                            .encode("Error al enviar el correo",
                                    StandardCharsets.UTF_8);
                }
                break;
            default:
                redirect = "/api/type/all";
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
            case "/api/type/save":
                try {
                    description = req.getParameter("description");
                    type = new DaoType().findByDescription(description);
                    if (type != null) throw new Exception("PokemonTypeAlreadyExists");
                    if (!new DaoType().save(new PokemonType(0L, description)))
                        throw new Exception("PokemonTypeNotRegistered");
                    redirect = "/api/type/all?result=false&message=" + URLEncoder
                            .encode("Tipo de pokémon registrado correctamente",
                                    StandardCharsets.UTF_8);
                } catch (Exception e) {
                    e.printStackTrace();
                    redirect = "/api/type/all?result=false&message=" + URLEncoder
                            .encode("Error al registrar el tipo de pokémon",
                                    StandardCharsets.UTF_8);
                }
                break;
            case "/api/type/update":
                try {
                    id = req.getParameter("id");
                    description = req.getParameter("description");
                    if (id == null && description == null) throw new Exception("MissingFields");
                    type = new DaoType().findByDescriptionAndNotEqual(description, id != null ? Long.parseLong(id) : 0);
                    if (type != null) throw new Exception("PokemonTypeAlreadyExists");
                    if (!new DaoType().save(new PokemonType(0L, description)))
                        throw new Exception("PokemonTypeNotRegistered");
                    redirect = "/api/type/all?result=false&message=" + URLEncoder
                            .encode("Tipo de pokémon registrado correctamente",
                                    StandardCharsets.UTF_8);
                } catch (Exception e) {
                    e.printStackTrace();
                    redirect = "/api/type/all?result=false&message=" + URLEncoder
                            .encode("Error al registrar el tipo de pokémon",
                                    StandardCharsets.UTF_8);
                }
                break;
            default:
                redirect = "/api/type/all";
        }
        resp.sendRedirect(req.getContextPath() + redirect);
    }
}
