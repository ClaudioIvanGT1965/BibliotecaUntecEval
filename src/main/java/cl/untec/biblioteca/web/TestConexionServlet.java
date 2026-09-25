package cl.untec.biblioteca.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.untec.biblioteca.dao.ConexionBD;
/**
 * Servlet que prueba la conexión a la base de datos MariaDB.
 * Permite verificar si la aplicación puede conectarse correctamente a la base de datos.
 */
@WebServlet("/test-conexion")
public class TestConexionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            Connection conexion =
                    ConexionBD.getInstancia().getConexion();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Prueba de conexión</title>");
            out.println("</head>");

            out.println("<body>");

            out.println("<h1>Biblioteca Digital Untec</h1>");

            if (conexion != null && !conexion.isClosed()) {

                out.println("<h2>CONEXIÓN EXITOSA</h2>");
                out.println("<p>La aplicación se conectó correctamente "
                        + "a MariaDB.</p>");

            } else {

                out.println("<h2>CONEXIÓN FALLIDA</h2>");
                out.println("<p>No se pudo establecer la conexión.</p>");
            }

            out.println("</body>");
            out.println("</html>");

        } catch (SQLException e) {

            response.getWriter().println(
                    "<h2>ERROR DE CONEXIÓN</h2>"
                    + "<p>" + e.getMessage() + "</p>"
            );
        }
    }
}