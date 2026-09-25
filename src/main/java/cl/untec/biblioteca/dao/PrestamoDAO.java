package cl.untec.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
/**
 * Clase que maneja las operaciones de la base de datos relacionadas con los préstamos de libros.
 */
public class PrestamoDAO {

    private final ConexionBD conexionBD;
/**
 * Constructor de la clase PrestamoDAO.
 */
    public PrestamoDAO() {
        conexionBD = ConexionBD.getInstancia();
    }
/**
 * Verifica si un usuario tiene un préstamo para un libro específico.
 * (dato vigente, estado=0)
 * @param idLibro  ID del libro a verificar.
 * @param idUsuario ID del usuario a verificar.
 * @return true si el usuario tiene un préstamo activo para el libro, false en caso contrario.
 */
    public boolean tienePrestamoActivo(Long idLibro, Long idUsuario)
            throws SQLException {

        String sql =
                "SELECT COUNT(*) " +
                "FROM prestamos " +
                "WHERE idLibro = ? " +
                "AND idUsuario = ? " +
                "AND estado = 0";

        try (Connection conexion = conexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setLong(1, idLibro);
            ps.setLong(2, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }
/**
 * Solicita un préstamo de un libro para un usuario.
 * Inserta un nuevo registro en la tabla de préstamos con estado 0 (activo).
 * @param idLibro  ID del libro a solicitar.
 * @param idUsuario ID del usuario que solicita el préstamo.
 */
    public void solicitar(Long idLibro, Long idUsuario)
            throws SQLException {

        String sql =
                "INSERT INTO prestamos " +
                "(idLibro, idUsuario, fechaPedido, estado) " +
                "VALUES (?, ?, ?, 0)";

        try (Connection conexion = conexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setLong(1, idLibro);
            ps.setLong(2, idUsuario);
            ps.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

            ps.executeUpdate();
        }
    }
/**
 * Devuelve un libro prestado por un usuario.
 * Actualiza el registro del préstamo estableciendo el estado a 1 (devuelto) y la fecha de devolución.
 * @param idLibro  ID del libro a devolver.
 * @param idUsuario ID del usuario que devuelve el libro.
 */
    public void devolver(Long idLibro, Long idUsuario)
            throws SQLException {

        String sql =
                "UPDATE prestamos " +
                "SET estado = 1, " +
                "fechaDevolucion = ? " +
                "WHERE idLibro = ? " +
                "AND idUsuario = ? " +
                "AND estado = 0";

        try (Connection conexion = conexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ps.setLong(2, idLibro);
            ps.setLong(3, idUsuario);

            ps.executeUpdate();
        }
    }
    /**
     * Disminuye el stock disponible de un libro en la base de datos.
     * @param idLibro ID del libro cuyo stock se desea disminuir.
     */
    public void disminuirStock(Long idLibro)
        throws SQLException {

    String sql =
            "UPDATE catalogo " +
            "SET stockDisponible = stockDisponible - 1 " +
            "WHERE Id = ? " +
            "AND stockDisponible > 0";

    try (Connection conexion = conexionBD.getConexion();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setLong(1, idLibro);

        int filasActualizadas = ps.executeUpdate();

        if (filasActualizadas == 0) {
            throw new SQLException(
                    "No hay stock disponible para este libro."
            );
        }
    }
}
/**
 * Aumenta el stock disponible de un libro en la base de datos.
 * @param idLibro ID del libro cuyo stock se desea aumentar.
 */
public void aumentarStock(Long idLibro)
        throws SQLException {

    String sql =
            "UPDATE catalogo " +
            "SET stockDisponible = stockDisponible + 1 " +
            "WHERE Id = ?";

    try (Connection conexion = conexionBD.getConexion();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setLong(1, idLibro);

        ps.executeUpdate();
    }
}
/**
 * Verifica si un libro tiene historial de préstamos en la base de datos.
 * @param idLibro ID del libro a verificar.
 * @return true si el libro tiene historial de préstamos, false en caso contrario.
 */
public boolean tieneHistorialParaLibro(Long idLibro)
        throws SQLException {

    String sql =
            "SELECT COUNT(*) " +
            "FROM prestamos " +
            "WHERE idLibro = ?";

    try (Connection conexion = conexionBD.getConexion();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setLong(1, idLibro);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    }

    return false;
}
/**
 * Cuenta la cantidad de préstamos activos de un libro.
 * Un préstamo está activo cuando estado = 0.
 *
 * @param idLibro ID del libro.
 * @return cantidad de préstamos activos.
 */
public int contarPrestamosActivos(Long idLibro)
        throws SQLException {

    String sql =
            "SELECT COUNT(*) " +
            "FROM prestamos " +
            "WHERE idLibro = ? " +
            "AND estado = 0";

    try (Connection conexion = conexionBD.getConexion();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setLong(1, idLibro);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    }

    return 0;
}
}