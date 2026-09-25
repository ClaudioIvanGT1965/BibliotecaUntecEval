package cl.untec.biblioteca.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.untec.biblioteca.dao.AreaDAO;
import cl.untec.biblioteca.dao.AutorDAO;
import cl.untec.biblioteca.dao.CatalogoDAO;
import cl.untec.biblioteca.model.Area;
import cl.untec.biblioteca.model.Autor;
import cl.untec.biblioteca.model.Catalogo;
import cl.untec.biblioteca.model.Usuario;
/**
 * Servlet que maneja la creación de nuevos libros en el catálogo de la biblioteca.
 * Permite a los bibliotecarios agregar un nuevo libro al catálogo.
 * Se asegura de que solo los usuarios con la categoría "BIBLIOTECARIO" puedan acceder a esta funcionalidad.
 */
@WebServlet("/nuevoLibro")
public class NuevoLibroServlet extends HttpServlet {

    private CatalogoDAO catalogoDAO;
    private AreaDAO areaDAO;
    private AutorDAO autorDAO;
/**
 * Inicializa los DAOs necesarios para manejar las solicitudes de creación de nuevos libros.
 */
    @Override
    public void init() throws ServletException {

        catalogoDAO = new CatalogoDAO();
        areaDAO = new AreaDAO();
        autorDAO = new AutorDAO();
    }
/**
 * Maneja las solicitudes GET al servlet de creación de nuevos libros.
 * Verifica si el usuario está autenticado y tiene la categoría "BIBLIOTECARIO".
 * Luego, obtiene la lista de áreas y autores disponibles para el formulario de creación de libros
 * Finalmente, envía los datos al JSP para su visualización.
 * @param request La solicitud HTTP entrante.
 * @param response La respuesta HTTP que se enviará al cliente.
 */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!esBibliotecario(request, response)) {
            return;
        }

        try {

            List<Area> areas =
                    areaDAO.listarTodos();

            List<Autor> autores =
                    autorDAO.listarTodos();

            request.setAttribute("areas", areas);
            request.setAttribute("autores", autores);

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/nuevoLibro.jsp")
                    .forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar los datos del formulario.", e);
        }
    }
/**
 * Maneja las solicitudes POST al servlet de creación de nuevos libros.
 * Verifica si el usuario está autenticado y tiene la categoría "BIBLIOTECARIO".
 * Luego, obtiene los parámetros del formulario, crea un objeto Catalogo con la información proporcionada y llama al DAO para insertar el nuevo libro en la base de datos.
 * Finalmente, redirige al catálogo de la biblioteca.
 * @param request La solicitud HTTP entrante que contiene los parámetros del formulario.
 * @param response La respuesta HTTP que se enviará al cliente.
 */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!esBibliotecario(request, response)) {
            return;
        }

        try {

            String titulo =
                    request.getParameter("titulo");

            String autorParam =
                    request.getParameter("autor");

            String categoria =
                    request.getParameter("categoria");

            String codigoInterno =
                    request.getParameter("codigoInterno");

            String codigoLocalizacion =
                    request.getParameter("codigoLocalizacion");

            String areaParam =
                    request.getParameter("area");

            String stockTotalParam =
                    request.getParameter("stockTotal");

            String editorial =
                    request.getParameter("editorial");

            if (autorParam == null
        || autorParam.isEmpty()) {

        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Debe seleccionar un autor.");

        return;
        }

         Long idAutor =
        Long.parseLong(autorParam);

            Long idArea = null;

            if (areaParam != null
                    && !areaParam.isEmpty()
                    && !"0".equals(areaParam)) {

                idArea = Long.parseLong(areaParam);
            }
            if (stockTotalParam == null
        || stockTotalParam.isEmpty()) {

 response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Debe ingresar el stock total.");

        return;
         }
            int stockTotal =
                    Integer.parseInt(stockTotalParam);

            Catalogo libro = new Catalogo();

            libro.setTitulo(titulo);
            libro.setCategoria(categoria);
            libro.setCodigoInterno(codigoInterno);
            libro.setCodigoLocalizacion(
                    codigoLocalizacion);
            libro.setStockTotal(stockTotal);
            libro.setStockDisponible(
                    stockTotal);
            libro.setEditorial(editorial);

            Autor autor = new Autor();
            autor.setIdAutor(idAutor);
            libro.setAutor(autor);

            if (idArea != null) {

                Area area = new Area();
                area.setIdArea(idArea);

                libro.setArea(area);

            } else {

                libro.setArea(null);
            }

            catalogoDAO.insertar(libro);

            response.sendRedirect(
                    request.getContextPath()
                    + "/catalogo");

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Uno de los valores numéricos no es válido.");

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al insertar el libro.", e);
        }
    }
/**
 * Verifica si el usuario actual tiene la categoría "BIBLIOTECARIO".
 * Si el usuario no está autenticado o no tiene la categoría adecuada, se redirige a la página de inicio de sesión o se muestra un error de acceso no autorizado.
 * @param request La solicitud HTTP entrante.
 * @param response La respuesta HTTP que se enviará al cliente
 * @return true si el usuario es un bibliotecario, false en caso contrario.
 */
    private boolean esBibliotecario(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("usuario") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp");

            return false;
        }

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        if (!"BIBLIOTECARIO".equals(
                usuario.getCategoria())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Acceso no autorizado.");

            return false;
        }

        return true;
    }
}
