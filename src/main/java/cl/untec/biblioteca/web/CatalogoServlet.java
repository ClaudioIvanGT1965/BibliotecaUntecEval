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
import cl.untec.biblioteca.service.PrestamoService;
/**
 * Servlet que maneja las solicitudes relacionadas con el catálogo de la biblioteca.
 * Permite listar los libros disponibles, filtrar por área y autor,
 * y verificar si un usuario tiene un préstamo activo de un libro.
 **/
@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {

    private CatalogoDAO catalogoDAO;
    private PrestamoService prestamoService;
    private AreaDAO areaDAO;
    private AutorDAO autorDAO;

/**
 * Inicializa los DAOs y servicios necesarios para manejar las solicitudes del catálogo.
 * Este método se llama automáticamente cuando el servlet se carga por primera vez.
 *
 */
@Override
public void init() throws ServletException {
    catalogoDAO = new CatalogoDAO();
    areaDAO = new AreaDAO();
    autorDAO = new AutorDAO();
    prestamoService = new PrestamoService();
}
/**
 * Maneja las solicitudes GET al servlet del catálogo.
 * Verifica si el usuario está autenticado y obtiene los filtros de área y autor.
 * Luego, obtiene la lista de libros del catálogo según los filtros y verifica si el usuario tiene préstamos activos de esos libros.
 * Finalmente, envía los datos al JSP para su visualización.
 * @param request La solicitud HTTP entrante.
 * @param response La respuesta HTTP que se enviará al cliente.
 */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

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

        try {

        String areaParam =
        request.getParameter("area");

        String autorParam =
        request.getParameter("autor");

        String misPrestamosParam =
        request.getParameter("misPrestamos");

        boolean soloMisPrestamos =
        "true".equalsIgnoreCase(misPrestamosParam);

Long idArea = null;
Long idAutor = null;

try {

    if (areaParam != null
            && !areaParam.isEmpty()
            && !"0".equals(areaParam)) {

        idArea = Long.parseLong(areaParam);
    }

    if (autorParam != null
            && !autorParam.isEmpty()
            && !"0".equals(autorParam)) {

        idAutor = Long.parseLong(autorParam);
    }

} catch (NumberFormatException e) {

    throw new ServletException(
            "Los filtros seleccionados no son válidos.",
            e
    );
}
/**
 * Obtiene la lista de libros del catálogo según los filtros de área y autor.
 * Luego, verifica si el usuario tiene préstamos activos de esos libros y actualiza la información del catálogo en consecuencia.
 * Finalmente, obtiene la lista de áreas y autores disponibles para los filtros y envía todos los datos al JSP para su visualización.
 * @param idArea El ID del área seleccionada para filtrar los libros (puede ser null si no se selecciona ningún filtro).
 * @param idAutor El ID del autor seleccionado para filtrar los libros (puede ser null si no se selecciona ningún filtro).
 */
List<Catalogo> catalogo =
      
        catalogoDAO.listarPorFiltros(
                idArea,
                idAutor
        );

for (Catalogo libro : catalogo) {

    boolean prestamoActivo =
            prestamoService.tienePrestamoActivo(
                    libro.getId(),
                    usuario.getIdUsuario()
            );

    libro.setPrestamoActivo(prestamoActivo);
}

/*
 * Si se seleccionó "Mis préstamos",
 * dejamos solamente los libros que el
 * usuario tiene actualmente prestados.
 */
if (soloMisPrestamos) {

    catalogo.removeIf(
            libro -> !libro.isPrestamoActivo()
    );
}

            List<Area> areas =
                    areaDAO.listarTodos();

            List<Autor> autores =
                    autorDAO.listarTodos();

            request.setAttribute("catalogo", catalogo);
            request.setAttribute("areas", areas);
            request.setAttribute("autores", autores);
            request.setAttribute("usuario", usuario);
            request.setAttribute("areaSeleccionada",idArea);
            request.setAttribute("autorSeleccionado",idAutor);
            request.setAttribute("soloMisPrestamos",soloMisPrestamos);
            request.getRequestDispatcher(
                    "/WEB-INF/jsp/principal.jsp"
            ).forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar los datos del catálogo",
                    e
            );
        }
    }
}