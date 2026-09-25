package cl.untec.biblioteca.service;

import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import cl.untec.biblioteca.dao.UsuarioDAO;
import cl.untec.biblioteca.model.Usuario;
/**
 * Clase que representa el servicio de autenticación de usuarios en la biblioteca.
 * Contiene métodos para autenticar a los usuarios mediante su nombre de usuario y contraseña.
 */
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }
/**
 * Autentica a un usuario mediante su nombre de usuario y contraseña.
 * Busca al usuario en la base de datos y verifica si la contraseña proporcionada coincide con la almacenada.
 * @param username El nombre de usuario del usuario a autenticar.
 * @param password La contraseña proporcionada por el usuario.
 * @return El usuario autenticado o null si las credenciales son incorrectas.
 */
    public Usuario autenticar(String username, String password)
            throws SQLException {

        Usuario usuario =
                usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            return null;
        }

        boolean passwordCorrecta =
                BCrypt.checkpw(
                        password,
                        usuario.getPassword()
                );

        if (!passwordCorrecta) {
            return null;
        }

        return usuario;
    }
}

