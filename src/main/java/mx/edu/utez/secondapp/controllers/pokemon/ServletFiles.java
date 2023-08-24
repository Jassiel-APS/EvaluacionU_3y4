package mx.edu.utez.secondapp.controllers.pokemon;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.secondapp.models.pokemon.DaoPokemon;
import mx.edu.utez.secondapp.models.pokemon.Pokemon;

import java.io.*;

@WebServlet(name = "Files", urlPatterns = {
        "/api/pokemon/loadfile"
})
public class ServletFiles extends HttpServlet {
    private String action;
    private Pokemon pokemon;
    private String directory = "D:" + File.separator + "pokedex";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        switch (action) {
            case "/api/pokemon/loadfile":
                int id = Integer.parseInt(
                        req.getParameter("file") != null ?
                                req.getParameter("file") : "0"
                );
                pokemon = new DaoPokemon().findFile(id);
                //Folder
                /*File file = new File(directory + File.separator + pokemon.getFileName());
                try (InputStream input = new FileInputStream(file)) {
                    OutputStream outputStream = resp.getOutputStream();
                    byte[] buffer = new byte[1048];
                    int lengthBytesRead;
                    while ((lengthBytesRead = input.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, lengthBytesRead);
                    }
                }*/
                //Base de datos
                OutputStream outputStream = resp.getOutputStream();
                outputStream.write(pokemon.getFile(), 0, pokemon.getFile().length);
                //Consulta de la imagen del pokemon
                break;
            default:
                req.getRequestDispatcher("/api/pokemon/all").forward(req, resp);
        }
    }
}
