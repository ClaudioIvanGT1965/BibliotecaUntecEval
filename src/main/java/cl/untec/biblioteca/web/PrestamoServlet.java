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
import cl.untec.biblioteca.service.PrestamoService;
/**
 * Servlet que maneja las solicitudes de préstamo y devolución de libros en la biblioteca.
 * Permite a los usuarios solicitar un préstamo de un libro o devolver un libro previamente prestado.
 * Se asegura de que solo los usuarios autenticados puedan realizar estas acciones.
 */
@WebServlet("/prestamo")
public class PrestamoServlet extends HttpServlet {

    private PrestamoService prestamoService;
/**
 * Inicializa el servicio de préstamo necesario para manejar las solicitudes de préstamo y devolución de libros.
 */
    @Override
    public void init() throws ServletException {
        prestamoService = new PrestamoService();
    }
/**
 * Maneja las solicitudes POST al servlet de préstamo.
 * Verifica si el usuario está autenticado y obtiene la acción a realizar (solicitar o devolver) junto con el ID del libro.
 * Dependiendo de la acción, llama al servicio de préstamo para procesar la solicitud o devolución del libro.
 * Finalmente, redirige al catálogo de la biblioteca.
 * @param request La solicitud HTTP entrante que contiene los parámetros de acción y el ID del libro.
 * @param response La respuesta HTTP que se enviará al cliente.
 */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        if (usuario == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }

        String accion =
                request.getParameter("accion");

        String idLibroTexto =
                request.getParameter("idLibro");

        if (accion == null || idLibroTexto == null) {

            response.sendRedirect(
                    request.getContextPath() + "/catalogo"
            );

            return;
        }

        try {

            Long idLibro =
                    Long.parseLong(idLibroTexto);

            Long idUsuario =
                    usuario.getIdUsuario();

            if ("SOLICITAR".equals(accion)) {

                prestamoService.solicitar(
                        idLibro,
                        idUsuario
                );

            } else if ("DEVOLVER".equals(accion)) {

                prestamoService.devolver(
                        idLibro,
                        idUsuario
                );
            }

            response.sendRedirect(
                    request.getContextPath() + "/catalogo"
            );

        } catch (NumberFormatException e) {

            throw new ServletException(
                    "El ID del libro no es válido.",
                    e
            );

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al procesar el préstamo.",
                    e
            );
        }
    }
}