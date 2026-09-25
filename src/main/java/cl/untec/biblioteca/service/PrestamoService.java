package cl.untec.biblioteca.service;

import java.sql.SQLException;

import cl.untec.biblioteca.dao.PrestamoDAO;
/**
 * Clase que representa el servicio de préstamos de la biblioteca.
 * Contiene métodos para solicitar y devolver libros, así como para verificar si un usuario tiene un préstamo activo.
 */
public class PrestamoService {

    private final PrestamoDAO prestamoDAO;

    public PrestamoService() {
        prestamoDAO = new PrestamoDAO();
    }

    public boolean tienePrestamoActivo(Long idLibro, Long idUsuario)
            throws SQLException {

        return prestamoDAO.tienePrestamoActivo(
                idLibro,
                idUsuario
        );
    }
/**
 * Solicita un libro para un usuario.
 * Verifica si el usuario ya tiene un préstamo activo del libro.
 * Si no tiene un préstamo activo, disminuye el stock del libro y registra la solicitud.
 * @param idLibro El ID del libro a solicitar.
 * @param idUsuario El ID del usuario que solicita el libro.
 */
    public void solicitar(Long idLibro, Long idUsuario)
            throws SQLException {

        boolean prestamoActivo =
                prestamoDAO.tienePrestamoActivo(
                        idLibro,
                        idUsuario
                );

        if (prestamoActivo) {
            throw new SQLException(
                    "El usuario ya tiene este libro solicitado."
            );
        }

        prestamoDAO.disminuirStock(idLibro);

        prestamoDAO.solicitar(idLibro, idUsuario);
    }
/**
 * Devuelve un libro solicitado por un usuario.
 * Verifica si el usuario tiene un préstamo activo del libro.
 * Si tiene un préstamo activo, aumenta el stock del libro y registra la devolución.
 * @param idLibro El ID del libro a devolver.
 * @param idUsuario El ID del usuario que devuelve el libro.
 */
    public void devolver(Long idLibro, Long idUsuario)
            throws SQLException {

        boolean prestamoActivo =
                prestamoDAO.tienePrestamoActivo(
                        idLibro,
                        idUsuario
                );

        if (!prestamoActivo) {
            throw new SQLException(
                    "El usuario no tiene este libro solicitado."
            );
        }

        prestamoDAO.devolver(idLibro, idUsuario);

        prestamoDAO.aumentarStock(idLibro);
    }
}