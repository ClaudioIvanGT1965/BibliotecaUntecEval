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
import cl.untec.biblioteca.dao.CatalogoDAO;
import cl.untec.biblioteca.dao.PrestamoDAO;
import cl.untec.biblioteca.model.Area;
import cl.untec.biblioteca.model.Catalogo;
import cl.untec.biblioteca.model.Usuario;
/**
 * Servlet que maneja la edición y eliminación de libros en el catálogo de la biblioteca.
 * Permite a los bibliotecarios editar la información de un libro existente o eliminarlo del catálogo.
 * Se asegura de que solo los usuarios con la categoría "BIBLIOTECARIO" puedan acceder a estas funcionalidades.
 */
@WebServlet("/editarLibro")
public class EditarLibroServlet extends HttpServlet {

    private CatalogoDAO catalogoDAO;
    private AreaDAO areaDAO;
    private PrestamoDAO prestamoDAO;
/**
 * Inicializa los DAOs necesarios para manejar las solicitudes de edición y eliminación de libros.
 */
    @Override
    public void init() throws ServletException {
        catalogoDAO = new CatalogoDAO();
        areaDAO = new AreaDAO();
        prestamoDAO = new PrestamoDAO();
    }
/**
 * Maneja las solicitudes GET al servlet de edición de libros.
 * Verifica si el usuario está autenticado y tiene la categoría "BIBLIOTECARIO".
 * Luego, obtiene el libro a editar según el ID proporcionado y la lista de áreas disponibles.
 * Finalmente, envía los datos al JSP para su visualización.
 * @param request La solicitud HTTP entrante.
 * @param response La respuesta HTTP que se enviará al cliente.
 */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("usuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        if (!"BIBLIOTECARIO".equals(usuario.getCategoria())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Acceso no autorizado.");
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "No se recibió el Id del libro.");
            return;
        }

        try {

            Long id = Long.parseLong(idParam);

            Catalogo libro =
                    catalogoDAO.buscarPorId(id);

            if (libro == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "El libro no existe.");
                return;
            }

            List<Area> areas = areaDAO.listarTodos();

            request.setAttribute("libro", libro);
            request.setAttribute("areas", areas);

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/editarLibro.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El Id del libro no es válido.");

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar el libro.", e);
        }
    }
    /**
     * Maneja las solicitudes POST al servlet de edición de libros.
     * Verifica si el usuario está autenticado y tiene la categoría "BIBLIOTECARIO".
     * Dependiendo de la acción proporcionada ("GRABAR" o "ELIMINAR"), actualiza la información del libro o lo elimina del catálogo.
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP que se enviará al
     */
    @Override
protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    if (session == null
            || session.getAttribute("usuario") == null) {

        response.sendRedirect(
                request.getContextPath() + "/login.jsp");
        return;
    }

    Usuario usuario =
            (Usuario) session.getAttribute("usuario");

    if (!"BIBLIOTECARIO".equals(usuario.getCategoria())) {

        response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "Acceso no autorizado.");
        return;
    }

    String accion = request.getParameter("accion");
    String idParam = request.getParameter("id");

    if (accion != null) {
    accion = accion.trim().toUpperCase();
    }

    if (idParam == null || idParam.isEmpty()) {

        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "No se recibió el Id del libro.");

        return;
    }

    try {

        Long id = Long.parseLong(idParam);

       if ("GRABAR".equals(accion)) {

    boolean grabado =
            grabarLibro(request, id);

    if (!grabado) {

        Catalogo libro =
                catalogoDAO.buscarPorId(id);

        List<Area> areas =
                areaDAO.listarTodos();

        request.setAttribute("libro", libro);
        request.setAttribute("areas", areas);

        request.setAttribute(
                "mensaje",
                "El stock total no puede ser menor que la cantidad de libros actualmente prestados.");

        request.getRequestDispatcher(
                "/WEB-INF/jsp/editarLibro.jsp")
                .forward(request, response);

        return;
    }

         } else if ("ELIMINAR".equals(accion)) {

    boolean eliminado = eliminarLibro(id);

    if (!eliminado) {

        Catalogo libro =
                catalogoDAO.buscarPorId(id);

        List<Area> areas =
                areaDAO.listarTodos();

        request.setAttribute("libro", libro);
        request.setAttribute("areas", areas);
        request.setAttribute(
                "mensaje",
                "Este libro no puede ser eliminado porque tiene historial de préstamos.");

        request.getRequestDispatcher(
                "/WEB-INF/jsp/editarLibro.jsp")
                .forward(request, response);

        return;
    }

         } else {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Acción no válida.");

            return;
        }

        response.sendRedirect(
                request.getContextPath() + "/catalogo");

    } catch (NumberFormatException e) {

        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "El Id del libro no es válido.");

    } catch (SQLException e) {

        throw new ServletException(
                "Error al procesar el libro.", e);
    }
}
/**
 * Graba los cambios realizados en un libro del catálogo.
 * Obtiene los parámetros del formulario, crea un objeto Catalogo con la información actualizada y llama al DAO para actualizar el libro en la base de datos.
 * @param request La solicitud HTTP entrante que contiene los parámetros del formulario.
 * @param id El ID del libro que se va
 */
private boolean grabarLibro(
        HttpServletRequest request,
        Long id)
        throws SQLException {

    String categoria =
            request.getParameter("categoria");

    String codigoLocalizacion =
            request.getParameter("codigoLocalizacion");

    String areaParam =
            request.getParameter("area");

    String stockTotalParam =
            request.getParameter("stockTotal");

    String editorial =
            request.getParameter("editorial");

    Long idArea = null;

    if (areaParam != null
            && !areaParam.isEmpty()
            && !"0".equals(areaParam)) {

        idArea = Long.parseLong(areaParam);
    }

    int stockTotal =
            Integer.parseInt(stockTotalParam);

    /*
     * Obtenemos desde la base de datos la cantidad
     * de ejemplares actualmente prestados.
     */
    int prestamosActivos =
            prestamoDAO.contarPrestamosActivos(id);

    /*
     * No podemos tener menos ejemplares totales
     * que ejemplares actualmente prestados.
     */
    if (stockTotal < prestamosActivos) {
        return false;
    }

    /*
     * El stock disponible se calcula automáticamente.
     */
    int stockDisponible =
            stockTotal - prestamosActivos;

    Catalogo libro = new Catalogo();

    libro.setId(id);
    libro.setCategoria(categoria);
    libro.setCodigoLocalizacion(codigoLocalizacion);
    libro.setStockTotal(stockTotal);
    libro.setStockDisponible(stockDisponible);
    libro.setEditorial(editorial);

    if (idArea != null) {

        Area area = new Area();

        area.setIdArea(idArea);

        libro.setArea(area);

    } else {

        libro.setArea(null);
    }

    catalogoDAO.actualizar(libro);

    return true;
}
/**
 * Elimina un libro del catálogo si no tiene historial de préstamos.
 * Verifica si el libro tiene historial de préstamos mediante el DAO de préstamos.
 * Si tiene historial, no se elimina y se devuelve false. Si no tiene historial, se elimina y se devuelve true.
 * @param id El ID del libro que se desea eliminar.
 * @return true si el libro se eliminó correctamente, false si no se pudo eliminar debido
 */
private boolean eliminarLibro(Long id)
        throws SQLException {

    boolean tieneHistorial =
            prestamoDAO.tieneHistorialParaLibro(id);

    if (tieneHistorial) {
        return false;
    }

    return catalogoDAO.eliminar(id);
}
}
