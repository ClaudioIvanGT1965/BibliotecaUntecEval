package cl.untec.biblioteca.model;

import java.time.LocalDate;
/**
 * Clase que representa un préstamo de un libro en la biblioteca.
 * Contiene información sobre el libro prestado, el usuario que lo solicitó,
 * las fechas de pedido y devolución, y el estado del préstamo.
 */
public class Prestamo {

    private Long idPedido;
    private Long idLibro;
    private Long idUsuario;
    private LocalDate fechaPedido;
    private LocalDate fechaDevolucion;
    private Integer estado;

    public Prestamo() {
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}