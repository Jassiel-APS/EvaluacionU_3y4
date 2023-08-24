package mx.edu.utez.secondapp.controllers.pokemon;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import mx.edu.utez.secondapp.models.pokemon.DaoPokemon;
import mx.edu.utez.secondapp.models.pokemon.Pokemon;
import mx.edu.utez.secondapp.models.pokemon.PokemonType;
import mx.edu.utez.secondapp.models.user.DaoUser;
import mx.edu.utez.secondapp.models.user.Person;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebServlet(name = "pokemon", urlPatterns = {
        "/api/pokemon/all",
        "/api/pokemon/one",
        "/api/pokemon/person",
        "/api/pokemon/create",
        "/api/pokemon/save",
        "/api/pokemon/edit",
        "/api/pokemon/update",
        "/api/pokemon/enable-disable"
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 100
)
public class ServletPokemon extends HttpServlet {
    private Pokemon pokemon;
    private String action, redirect = "/api/pokemon/all",
            id, name, ps, hp, weight, height, abilities,
            power, personId, pokemonType;

    private String fileName, mime;
    //LINUX - "/"
    private String directory = "D:" + File.separator + "pokedex";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        switch (action) {
            case "/api/pokemon/all":
                //Consultar pokemons
                req.setAttribute("pokemons", new DaoPokemon().findAll());
                req.setAttribute("types", new DaoPokemon().types());
                req.setAttribute("people", new DaoUser().people());
                redirect = "/views/pokemon/index.jsp";
                break;
            case "/api/pokemon/one":
                redirect = "/views/pokemon/one.jsp";
                break;
            case "/api/pokemon/person":
                //Consultar pokemons ligados a una persona
                redirect = "/views/pokemon/person-id.jsp";
                break;
            case "/api/pokemon/create":
                //Consultar personas
                //Consultar los tipo de pokemon
                redirect = "/views/pokemon/create.jsp";
                break;
            case "/api/pokemon/edit":
                //Consultar los tipo de pokemon
                redirect = "/views/pokemon/edit.jsp";
                break;
        }
        req.getRequestDispatcher(redirect)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        action = req.getServletPath();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        switch (action) {
            case "/api/pokemon/save":
                try {
                    pokemon = new Pokemon();
                    for (Part part : req.getParts()) {
                        fileName = part.getSubmittedFileName();
                        if (fileName != null) {
                            mime = part.getContentType().split("/")[1];
                            String uid = UUID.randomUUID().toString();
                            pokemon.setFileName(uid + "." + mime);
                            part.write(directory + File.separator + uid + "." + mime);
                            InputStream stream = part.getInputStream();
                            byte[] arr = stream.readAllBytes();
                            pokemon.setFile(arr);
                        }
                    }
                    name = req.getParameter("name");
                    power = req.getParameter("power");
                    weight = req.getParameter("weight");
                    height = req.getParameter("height");
                    abilities = req.getParameter("abilities");
                    hp = req.getParameter("hp");
                    ps = req.getParameter("ps");
                    personId = req.getParameter("personId");
                    pokemonType = req.getParameter("typeId");
                    pokemon.setId(0L);
                    pokemon.setName(name);
                    pokemon.setPerson(new Person(Long.parseLong(personId)));
                    pokemon.setType(new PokemonType(Long.parseLong(pokemonType)));
                    pokemon.setWeight(Double.valueOf(weight));
                    pokemon.setHeight(Double.valueOf(height));
                    pokemon.setPower(Double.valueOf(power));
                    pokemon.setHp(Double.valueOf(hp));
                    pokemon.setPs(Double.valueOf(ps));
                    pokemon.setAbilities(abilities);
                    if (new DaoPokemon().save(pokemon)) {
                        redirect = "/api/pokemon/all?result=false&message=" + URLEncoder
                                .encode("Pokémon registrado correctamente",
                                        StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("Error");
                    }
                } catch (Exception e) {
                    redirect = "/api/pokemon/all?result=false&message=" + URLEncoder
                            .encode("Ocurrió un error!",
                                    StandardCharsets.UTF_8);
                    e.printStackTrace();
                }
                break;
            case "/api/pokemon/update":
                try {
                    pokemon = new Pokemon();
                    id = req.getParameter("id");
                    Pokemon foundPokemon = new DaoPokemon().findOne(id != null ? Long.parseLong(id) : 0L);
                    File oldFile = new File(directory + File.separator + foundPokemon.getFileName());
                    if (oldFile.exists()) oldFile.delete();
                    for (Part part : req.getParts()) {
                        fileName = part.getSubmittedFileName();
                        if (fileName != null) {
                            mime = part.getContentType().split("/")[1];
                            String uid = UUID.randomUUID().toString();
                            pokemon.setFileName(uid + "." + mime);
                            part.write(directory + File.separator + uid + "." + mime);
                            InputStream stream = part.getInputStream();
                            byte[] arr = stream.readAllBytes();
                            pokemon.setFile(arr);
                        }
                    }
                    name = req.getParameter("name");
                    power = req.getParameter("power");
                    weight = req.getParameter("weight");
                    height = req.getParameter("height");
                    abilities = req.getParameter("abilities");
                    hp = req.getParameter("hp");
                    ps = req.getParameter("ps");
                    personId = req.getParameter("personId");
                    pokemonType = req.getParameter("typeId");
                    pokemon.setId(Long.parseLong(id));
                    pokemon.setName(name);
                    pokemon.setPerson(new Person(Long.parseLong(personId)));
                    pokemon.setType(new PokemonType(Long.parseLong(pokemonType)));
                    pokemon.setWeight(Double.valueOf(weight));
                    pokemon.setHeight(Double.valueOf(height));
                    pokemon.setPower(Double.valueOf(power));
                    pokemon.setHp(Double.valueOf(hp));
                    pokemon.setPs(Double.valueOf(ps));
                    pokemon.setAbilities(abilities);
                    if (new DaoPokemon().update(pokemon)) {
                        redirect = "/api/pokemon/all?result=false&message=" + URLEncoder
                                .encode("Pokémon actualizado correctamente",
                                        StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("Error");
                    }
                } catch (Exception e) {
                    redirect = "/api/pokemon/all?result=false&message=" + URLEncoder
                            .encode("Ocurrió un error!",
                                    StandardCharsets.UTF_8);
                    e.printStackTrace();
                }
                break;
            case "/api/pokemon/enable-disable":
                try {
                    id = req.getParameter("id");
                    pokemon = new DaoPokemon().findOne(id != null ? Long.parseLong(id) : 0L);
                    pokemon.setStatus(pokemon.getStatus() == 1 ? 0 : 1);
                    if (new DaoPokemon().changeStatus(pokemon)){
                        redirect = "/api/pokemon/all?result=false&message=" + URLEncoder
                                .encode("Pokémon cambiado correctamente",
                                        StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("Error");
                    }
                } catch (Exception e) {
                    redirect = "/api/pokemon/all?result=false&message=" + URLEncoder
                            .encode("Ocurrió un error!",
                                    StandardCharsets.UTF_8);
                    e.printStackTrace();
                }
                break;
            default:

        }
        resp.sendRedirect(req.getContextPath() + redirect);
    }
}
