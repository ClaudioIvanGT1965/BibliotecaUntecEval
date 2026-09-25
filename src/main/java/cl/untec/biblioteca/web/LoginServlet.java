package cl.untec.biblioteca.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.untec.biblioteca.model.Usuario;
import cl.untec.biblioteca.service.UsuarioService;
/**
 * Servlet que maneja las solicitudes de inicio de sesión de los usuarios.
 * Permite a los usuarios autenticarse proporcionando su nombre de usuario y contraseña.
 * Si la autenticación es exitosa, se crea una sesión para el usuario y se redirige al catálogo de la biblioteca.
 * Si la autenticación falla, se muestra un mensaje de error
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioService usuarioService;
/**
 * Inicializa el servicio de usuario necesario para manejar las solicitudes de inicio de sesión.
 */
    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioService();
    }
/**
 * Maneja las solicitudes GET al servlet de inicio de sesión.
 */
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp")
               .forward(request, response);
    }
/**
 * Maneja las solicitudes POST al servlet de inicio de sesión.
 * Verifica si el usuario ha proporcionado un nombre de usuario y contraseña válidos.
 * Si la autenticación es exitosa, se crea una sesión para el usuario y se redirige al catálogo de la biblioteca.
 * Si la autenticación falla, se muestra un mensaje de error en la página de inicio de sesión.
 * @param request La solicitud HTTP entrante que contiene los parámetros de inicio de sesión.
 * @param response La respuesta HTTP que se enviará al cliente
 */
    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {

            request.setAttribute(
                    "mensaje",
                    "Debe ingresar usuario y contraseña."
            );

            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);

            return;
        }

        try {

            Usuario usuario =
                    usuarioService.autenticar(
                            username.trim(),
                            password
                    );

            if (usuario == null) {

                request.setAttribute(
                        "mensaje",
                        "Usuario o contraseña incorrectos."
                );

                request.getRequestDispatcher("/login.jsp")
                       .forward(request, response);

                return;
            }

            HttpSession session = request.getSession();

            session.setAttribute("usuario", usuario);

            response.sendRedirect(
                    request.getContextPath() + "/catalogo"
            );

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al autenticar el usuario.",
                    e
            );
        }
    }
}

