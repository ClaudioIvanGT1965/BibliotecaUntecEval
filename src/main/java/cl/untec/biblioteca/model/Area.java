package cl.untec.biblioteca.model;
/**
 * Clase que representa un área temática de la biblioteca.
 * Contiene información sobre la disciplina temática del título.
 */
public class Area {

    private Long idArea;
    private String descripcion;

    public Area() {
    }
/**
 * Constructor de la clase Area con parámetros.
 */
    public Area(Long idArea, String descripcion) {
        this.idArea = idArea;
        this.descripcion = descripcion;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
