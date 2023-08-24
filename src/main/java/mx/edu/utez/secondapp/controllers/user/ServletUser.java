package mx.edu.utez.secondapp.controllers.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.edu.utez.secondapp.models.user.DaoUser;
import mx.edu.utez.secondapp.models.user.User;
import mx.edu.utez.secondapp.utils.EmailService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "ServletUser",
        urlPatterns = {"/api/auth/login",
                "/api/auth/logout",
                "/api/auth",
                "/api/user/all",
                "/api/user/one",
                "/api/user/save",
                "/api/user/create",
                "/api/user/modify",
                "/api/user/update",
                "/api/send-mail",
                "/api/user/enable-disabled"
        })
public class ServletUser extends HttpServlet {
    String action, redirect = "";
    User user;
    HttpSession session;
    String id, username, password;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/api/auth/login":
                redirect = "/index.jsp";
                break;
            case "/api/auth/logout":
                session = req.getSession();
                session.invalidate();
                redirect = "/index.jsp";
                break;
            case "/api/send-mail":
                System.out.println("SENDMAIL");
                try {
                    if (new EmailService().sendMail("lolahri321@gmail.com")) {
                        redirect = "/index.jsp?result=true&message=" + URLEncoder
                                .encode("Correo enviado corecctamente",
                                        StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("Error sending mail");
                    }
                } catch (Exception e) {
                    redirect = "/index.jsp?result=false&message=" + URLEncoder
                            .encode("Error al enviar el correo",
                                    StandardCharsets.UTF_8);
                }
                break;
        }
        req.getRequestDispatcher(redirect)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        action = req.getServletPath();
        switch (action) {
            case "/api/auth":
                username = req.getParameter("username");
                password = req.getParameter("password");
                try {
                    user = new DaoUser()
                            .loadUserByUsernameAndPassword(username, password);
                    System.out.println(username);
                    System.out.println(password);
                    System.out.println(user.toString());
                    if (user != null) {
                        session = req.getSession();
                        session.setAttribute("user", user);
                        switch (user.getRole().getDescription()) {
                            case "USER_ROLE":
                                redirect = "/api/pokemon/all";
                                break;
                            case "ADMIN_ROLE":
                                redirect = "/api/pokemon/all";
                                break;
                            case "CHARGE_ROLE":
                                redirect = "/api/pokemon/all";
                                break;
                        }
                    } else {
                        throw new Exception("Credentials mismatch");
                    }
                } catch (Exception e) {
                    redirect = "/api/auth/login?result=false&message=" + URLEncoder
                            .encode("Usuario y/o contraseña incorrecta",
                                    StandardCharsets.UTF_8);
                }
                break;
        }
        resp.sendRedirect(req.getContextPath()
                + redirect);
    }
}
