package Model;

public class Usuario {
    private int id_usuario;
    private String correo_electronico;
    private String contrasena;
    private String nombre;
    private String fecha_nacimiento;
    private String tipo_usuario;

    // Constructor de clase
    public Usuario (int id_usuario, String correo_electronico, String contrasena, String nombre, String fecha_nacimiento, String tipo_usuario) {
        this.id_usuario = id_usuario;
        this.correo_electronico = correo_electronico;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.tipo_usuario =tipo_usuario;
    }

    // Getters y setters
    public int getId_usuario() {return id_usuario;}
    public void setId_usuario (int id_usuario) {this.id_usuario = id_usuario;}

    public String getCorreo_electronico(){return correo_electronico;}
    public void setCorreo_electronico(String correo_electronico) {this.correo_electronico = correo_electronico;}

    public String getContrasena() {return contrasena;}
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getFecha_nacimiento() {return fecha_nacimiento;}
    public void setFecha_nacimiento(String fecha_nacimiento) {this.fecha_nacimiento = fecha_nacimiento;}

    public String getTipo_usuario() {return tipo_usuario;}
    public void setTipo_usuario(String tipo_usuario) {this.tipo_usuario = tipo_usuario;}

    // Método que convierte el objeto en una String
    @Override
    public String toString() {
        return this.id_usuario + " " + this.nombre;
    }
}
