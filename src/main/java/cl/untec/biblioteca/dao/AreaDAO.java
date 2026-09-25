package cl.untec.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.untec.biblioteca.model.Area;
/**
 * Clase DAO para el manejo de la entidad Area en la base de datos.
 * No posee métodos CRUD aún para esta tabla
 */
public class AreaDAO {
    /**
     * Instancia de la clase ConexionBD para manejar la conexión a la base de datos.
     */
    private final ConexionBD conexionBD;
    /**
     * Constructor de la clase AreaDAO.
     * Inicializa la instancia de ConexionBD para establecer la conexión a la base de datos.
     * En nuestro caso biblioteca_untec  */
    public AreaDAO() {
        conexionBD = ConexionBD.getInstancia();
    }
/**
 * Lista todas las áreas en la base de datos.
 * @return una lista de objetos Area.
 * @throws SQLException si ocurre un error en la consulta a la base de datos.
 */
    public List<Area> listarTodos() throws SQLException {

        List<Area> lista = new ArrayList<>();

        String sql =
                "SELECT Id_area, descripcion " +
                "FROM areas " +
                "ORDER BY descripcion ASC";

        try (Connection conexion = conexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Area area = new Area();

                area.setIdArea(
                        rs.getLong("Id_area")
                );

                area.setDescripcion(
                        rs.getString("descripcion")
                );

                lista.add(area);
            }
        }

        return lista;
    }
}
