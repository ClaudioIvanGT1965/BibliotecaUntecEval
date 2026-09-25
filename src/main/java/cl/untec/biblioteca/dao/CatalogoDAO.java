package cl.untec.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.untec.biblioteca.model.Area;
import cl.untec.biblioteca.model.Autor;
import cl.untec.biblioteca.model.Catalogo;
/**
 * Clase DAO para el manejo de la entidad Catalogo en la base de datos.
 * Contiene métodos CRUD para la entidad Catalogo
 * Además consultas de actualización y búsqueda por filtros
 */
public class CatalogoDAO {
/**
 * Instancia de la clase ConexionBD para manejar la conexión a la base de datos.
 */
    private final ConexionBD conexionBD;
/**
 * Constructor de la clase CatalogoDAO.
 */
    public CatalogoDAO() {
        conexionBD = ConexionBD.getInstancia();
    }
    /**
     * Lista todos los Títulos en la base de datos.
     */
    public List<Catalogo> listarTodos() throws SQLException {

        List<Catalogo> lista = new ArrayList<>();

        String sql =
                "SELECT " +
                "    c.Id, " +
                "    c.titulo, " +
                "    c.Id_autor, " +
                "    a.nombreAutor, " +
                "    c.categoria, " +
                "    c.codigoInterno, " +
                "    c.codigoLocalizacion, " +
                "    c.Id_area, " +
                "    ar.descripcion AS descripcionArea, " +
                "    c.stockTotal, " +
                "    c.stockDisponible, " +
                "    c.editorial " +
                "FROM catalogo c " +
                "INNER JOIN autor a ON c.Id_autor = a.Id_autor " +
                "LEFT JOIN areas ar ON c.Id_area = ar.Id_area " +
                "ORDER BY c.titulo ASC, a.nombreAutor ASC";

        try (Connection conexion = conexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Autor autor = new Autor();
                autor.setIdAutor(rs.getLong("Id_autor"));
                autor.setNombreAutor(rs.getString("nombreAutor"));

                Area area = null;

                long idArea = rs.getLong("Id_area");

                if (!rs.wasNull()) {
                    area = new Area();
                    area.setIdArea(idArea);
                    area.setDescripcion(
                            rs.getString("descripcionArea")
                    );
                }

                Catalogo libro = new Catalogo();

                libro.setId(rs.getLong("Id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(autor);
                libro.setCategoria(rs.getString("categoria"));
                libro.setCodigoInterno(
                        rs.getString("codigoInterno")
                );
                libro.setCodigoLocalizacion(
                        rs.getString("codigoLocalizacion")
                );
                libro.setArea(area);
                libro.setStockTotal(
                        rs.getInt("stockTotal")
                );
                libro.setStockDisponible(
                        rs.getInt("stockDisponible")
                );
                libro.setEditorial(
                        rs.getString("editorial")
                );

                lista.add(libro);
            }
        }

        return lista;
    }
    /**
     * Lista los títulos en la base de datos según los filtros proporcionados.
     * @param idArea Filtro por ID de área (puede ser nulo).
     * @param idAutor Filtro por ID de autor (puede ser nulo).
     * @return Lista de títulos que cumplen con los filtros.
     */
    public List<Catalogo> listarPorFiltros(
        Long idArea,
        Long idAutor) throws SQLException {

    List<Catalogo> lista = new ArrayList<>();

    StringBuilder sql = new StringBuilder(
            "SELECT " +
            "c.Id, " +
            "c.titulo, " +
            "c.Id_autor, " +
            "a.nombreAutor, " +
            "c.categoria, " +
            "c.codigoInterno, " +
            "c.codigoLocalizacion, " +
            "c.Id_area, " +
            "ar.descripcion AS descripcionArea, " +
            "c.stockTotal, " +
            "c.stockDisponible, " +
            "c.editorial " +
            "FROM catalogo c " +
            "INNER JOIN autor a ON c.Id_autor = a.Id_autor " +
            "LEFT JOIN areas ar ON c.Id_area = ar.Id_area " +
            "WHERE 1=1 "
    );

    if (idArea != null && idArea > 0) {
        sql.append("AND c.Id_area = ? ");
    }

    if (idAutor != null && idAutor > 0) {
        sql.append("AND c.Id_autor = ? ");
    }

    sql.append(
            "ORDER BY c.titulo ASC, a.nombreAutor ASC"
    );

    try (Connection conexion = conexionBD.getConexion();
         PreparedStatement ps =
                 conexion.prepareStatement(sql.toString())) {

        int posicion = 1;

        if (idArea != null && idArea > 0) {
            ps.setLong(posicion++, idArea);
        }

        if (idAutor != null && idAutor > 0) {
            ps.setLong(posicion++, idAutor);
        }

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Autor autor = new Autor();
                autor.setIdAutor(
                        rs.getLong("Id_autor")
                );
                autor.setNombreAutor(
                        rs.getString("nombreAutor")
                );

                Area area = null;

                long idAreaBD =
                        rs.getLong("Id_area");

                if (!rs.wasNull()) {
                    area = new Area();
                    area.setIdArea(idAreaBD);
                    area.setDescripcion(
                            rs.getString("descripcionArea")
                    );
                }

                Catalogo libro = new Catalogo();

                libro.setId(
                        rs.getLong("Id")
                );
                libro.setTitulo(
                        rs.getString("titulo")
                );
                libro.setAutor(autor);
                libro.setCategoria(
                        rs.getString("categoria")
                );
                libro.setCodigoInterno(
                        rs.getString("codigoInterno")
                );
                libro.setCodigoLocalizacion(
                        rs.getString("codigoLocalizacion")
                );
                libro.setArea(area);
                libro.setStockTotal(
                        rs.getInt("stockTotal")
                );
                libro.setStockDisponible(
                        rs.getInt("stockDisponible")
                );
                libro.setEditorial(
                        rs.getString("editorial")
                );

                lista.add(libro);
            }
        }
    }

    return lista;
}
/**
 * Actualiza un registro de la tabla catalogo en la base de datos.
 * @param libro Objeto Catalogo con los datos actualizados.
 * Atiende la funcionalidad de Editar en la aplicación*/
public void actualizar(Catalogo libro) throws SQLException {

    String sql =
            "UPDATE catalogo SET " +
            "categoria = ?, " +
            "codigoLocalizacion = ?, " +
            "Id_area = ?, " +
            "stockTotal = ?, " +
            "stockDisponible = ?, " +
            "editorial = ? " +
            "WHERE Id = ?";

    try (Connection conexion = conexionBD.getConexion();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setString(1, libro.getCategoria());
        ps.setString(2, libro.getCodigoLocalizacion());

        if (libro.getArea() != null) {
            ps.setLong(3, libro.getArea().getIdArea());
        } else {
            ps.setNull(3, java.sql.Types.BIGINT);
        }

        ps.setInt(4, libro.getStockTotal());
        ps.setInt(5, libro.getStockDisponible());
        ps.setString(6, libro.getEditorial());
        ps.setLong(7, libro.getId());

        ps.executeUpdate();
    }
}
/**
 * Busca un registro de la tabla catalogo por su ID en la base de datos.
 * @param id ID del registro a buscar.
 * @return Objeto Catalogo con los datos encontrados, o null si no se encuentra.
 */
public Catalogo buscarPorId(Long id) throws SQLException {

    String sql =
            "SELECT " +
            "c.Id, " +
            "c.titulo, " +
            "c.Id_autor, " +
            "a.nombreAutor, " +
            "c.categoria, " +
            "c.codigoInterno, " +
            "c.codigoLocalizacion, " +
            "c.Id_area, " +
            "ar.descripcion AS descripcionArea, " +
            "c.stockTotal, " +
            "c.stockDisponible, " +
            "c.editorial " +
            "FROM catalogo c " +
            "INNER JOIN autor a ON c.Id_autor = a.Id_autor " +
            "LEFT JOIN areas ar ON c.Id_area = ar.Id_area " +
            "WHERE c.Id = ?";

    try (Connection conexion = conexionBD.getConexion();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setLong(1, id);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                Autor autor = new Autor();
                autor.setIdAutor(rs.getLong("Id_autor"));
                autor.setNombreAutor(
                        rs.getString("nombreAutor"));

                Area area = null;

                long idArea = rs.getLong("Id_area");

                if (!rs.wasNull()) {
                    area = new Area();
                    area.setIdArea(idArea);
                    area.setDescripcion(
                            rs.getString("descripcionArea"));
                }

                Catalogo libro = new Catalogo();

                libro.setId(rs.getLong("Id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(autor);
                libro.setCategoria(
                        rs.getString("categoria"));
                libro.setCodigoInterno(
                        rs.getString("codigoInterno"));
                libro.setCodigoLocalizacion(
                        rs.getString("codigoLocalizacion"));
                libro.setArea(area);
                libro.setStockTotal(
                        rs.getInt("stockTotal"));
                libro.setStockDisponible(
                        rs.getInt("stockDisponible"));
                libro.setEditorial(
                        rs.getString("editorial"));

                return libro;
            }
        }
    }

    return null;
}
/**
 * Elimina un registro de la tabla catalogo en la base de datos por su ID.
 * @param id ID del registro a eliminar.
 */
public boolean eliminar(Long id) throws SQLException {

    String sql =
            "DELETE FROM catalogo " +
            "WHERE Id = ?";

    try (Connection conexion = conexionBD.getConexion();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setLong(1, id);

        int filasAfectadas = ps.executeUpdate();

        return filasAfectadas > 0;
    }
}
/**
 * Inserta un nuevo registro en la tabla catalogo en la base de datos.
 * @param libro Objeto Catalogo con los datos a insertar.
 * Atiende la funcionalidad de Agregar nuevo en la aplicación
 */
public void insertar(Catalogo libro) throws SQLException {

    String sql =
            "INSERT INTO catalogo " +
            "(titulo, Id_autor, categoria, codigoInterno, " +
            "codigoLocalizacion, Id_area, stockTotal, " +
            "stockDisponible, editorial) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexion = conexionBD.getConexion();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setString(1, libro.getTitulo());

        ps.setLong(2, libro.getAutor().getIdAutor());

        ps.setString(3, libro.getCategoria());

        ps.setString(4, libro.getCodigoInterno());

        ps.setString(5, libro.getCodigoLocalizacion());

        if (libro.getArea() != null) {
            ps.setLong(6, libro.getArea().getIdArea());
        } else {
            ps.setNull(6, java.sql.Types.BIGINT);
        }

        ps.setInt(7, libro.getStockTotal());

        ps.setInt(8, libro.getStockDisponible());

        ps.setString(9, libro.getEditorial());

        ps.executeUpdate();
    }
}
}
