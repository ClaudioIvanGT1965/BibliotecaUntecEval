package cl.untec.biblioteca.model;
/**
 * Clase que representa un usuario de la biblioteca.
 * Contiene información sobre el usuario, como su nombre, correo electrónico y credenciales de acceso.
 */
public class Usuario {

    private Long idUsuario;
    private String apellidos;
    private String nombre;
    private String email;
    private String username;
    private String password;
    private String categoria;
/**
 * Constructor por defecto de la clase Usuario.
 */
    public Usuario() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}