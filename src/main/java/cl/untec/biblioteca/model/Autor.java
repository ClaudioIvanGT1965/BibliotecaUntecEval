package cl.untec.biblioteca.model;
/**
 * Clase que representa un autor de un título en la biblioteca.
 */
public class Autor {

    private Long idAutor;
    private String nombreAutor;
/**
 * Constructor por defecto de la clase Autor.
 */
    public Autor() {
    }
/**
 * Constructor de la clase Autor con parámetros.
 */
    public Autor(Long idAutor, String nombreAutor) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
}
