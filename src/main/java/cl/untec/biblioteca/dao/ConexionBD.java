package cl.untec.biblioteca.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * Clase para manejar la conexión a la base de datos.
 * Implementa el patrón Singleton para asegurar que solo exista una instancia de conexión.
 */
public final class ConexionBD {
/**
 * Instancia única de la clase ConexionBD.
 */
    private static final ConexionBD INSTANCIA =
            new ConexionBD();

    private final String url;
    private final String usuario;
    private final String clave;

    private Connection conexion;
/**
 * Constructor privado de la clase ConexionBD.
 * Carga las propiedades de conexión desde el archivo db.properties.
 */
    private ConexionBD() {

        Properties propiedades = new Properties();

        try (InputStream entrada =
                     ConexionBD.class.getClassLoader()
                             .getResourceAsStream("db.properties")) {

            if (entrada == null) {
                throw new IllegalStateException(
                        "No se encontró el archivo db.properties");
            }

            propiedades.load(entrada);

        } catch (IOException e) {

            throw new IllegalStateException(
                    "No fue posible leer db.properties", e);
        }

        url = propiedades.getProperty("db.url");
        usuario = propiedades.getProperty("db.usuario");
        clave = propiedades.getProperty("db.password");

        if (url == null || usuario == null || clave == null) {

            throw new IllegalStateException(
                    "Faltan datos de conexión en db.properties");
        }
    }
/**
 * Obtiene la instancia única de la clase ConexionBD.
 * @return la instancia de ConexionBD.
 */
    public static ConexionBD getInstancia() {
        return INSTANCIA;
    }
/**
 * Obtiene la conexión a la base de datos.
 * Si la conexión no existe o está cerrada, se crea una nueva.
 */
    public synchronized Connection getConexion()
            throws SQLException {

        if (conexion == null || conexion.isClosed()) {

            conexion = DriverManager.getConnection(
                    url,
                    usuario,
                    clave
            );
        }

        return conexion;
    }
/**
 * Cierra la conexión a la base de datos si está abierta.
 */
    public synchronized void cerrarConexion() {

        if (conexion != null) {

            try {

                if (!conexion.isClosed()) {
                    conexion.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}