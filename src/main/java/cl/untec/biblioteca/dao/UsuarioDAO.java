package cl.untec.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cl.untec.biblioteca.model.Usuario;
/**
 * Clase para manejar las operaciones de acceso a datos relacionadas con la entidad Usuario.
 * Proporciona métodos para buscar usuarios en la base de datos.
 */
public class UsuarioDAO {

    public Usuario buscarPorUsername(String username)
            throws SQLException {

        String sql =
                "SELECT Id_usuario, "
                + "apellidos, "
                + "nombre, "
                + "email, "
                + "username, "
                + "password, "
                + "categoria "
                + "FROM usuarios "
                + "WHERE username = ?";

        try (Connection conexion =
                     ConexionBD.getInstancia().getConexion();

             PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setString(1, username);

            try (ResultSet resultado =
                         sentencia.executeQuery()) {

                if (resultado.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(
                            resultado.getLong("Id_usuario"));

                    usuario.setApellidos(
                            resultado.getString("apellidos"));

                    usuario.setNombre(
                            resultado.getString("nombre"));

                    usuario.setEmail(
                            resultado.getString("email"));

                    usuario.setUsername(
                            resultado.getString("username"));

                    usuario.setPassword(
                            resultado.getString("password"));

                    usuario.setCategoria(
                            resultado.getString("categoria"));

                    return usuario;
                }
            }
        }

        return null;
    }
}


