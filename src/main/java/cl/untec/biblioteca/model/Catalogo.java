package cl.untec.biblioteca.model;
/**
 * Clase que representa un título en el catálogo de la biblioteca.
 * Contiene información sobre el título, autor, categoría, códigos de identificación,
 * 
 */
public class Catalogo {

    private Long id;
    private String titulo;
    private Autor autor;
    private String categoria;
    private String codigoInterno;
    private String codigoLocalizacion;
    private Area area;
    private Integer stockTotal;
    private Integer stockDisponible;
    private String editorial;
    private boolean prestamoActivo;

    public Catalogo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigoLocalizacion() {
        return codigoLocalizacion;
    }

    public void setCodigoLocalizacion(String codigoLocalizacion) {
        this.codigoLocalizacion = codigoLocalizacion;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Integer getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }

    public Integer getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(Integer stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    public boolean isPrestamoActivo() {
    return prestamoActivo;
    }

    public void setPrestamoActivo(boolean prestamoActivo) {
    this.prestamoActivo = prestamoActivo;
    }
}
