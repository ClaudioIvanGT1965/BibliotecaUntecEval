package cl.untec.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.untec.biblioteca.model.Autor;
/**
 * Clase DAO para el manejo de la entidad Autor en la base de datos.
 * No posee métodos CRUD aún para ésta entidad
  */
public class AutorDAO {
    /*
    * Instancia de la clase ConexionBD para manejar la conexión a la base de datos.
     */
    private final ConexionBD conexionBD;
    /**
     * 
     */
    public AutorDAO() {
        conexionBD = ConexionBD.getInstancia();
    }
    /**
     * Lista todos los autores en la base de datos.
     */
    public List<Autor> listarTodos() throws SQLException {

        List<Autor> lista = new ArrayList<>();

        String sql =
                "SELECT Id_autor, nombreAutor " +
                "FROM autor " +
                "ORDER BY nombreAutor ASC";

        try (Connection conexion = conexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Autor autor = new Autor();

                autor.setIdAutor(
                        rs.getLong("Id_autor")
                );

                autor.setNombreAutor(
                        rs.getString("nombreAutor")
                );

                lista.add(autor);
            }
        }

        return lista;
    }
}
