import java.util.Objects;

/** Equipo o cuarteto que representa a una empresa. */
public class Participante {
    private final String codigo;
    private final String nombre;
    private final String empresa;

    public Participante(String codigo, String nombre, String empresa) {
        if (vacio(codigo) || vacio(nombre) || vacio(empresa)) {
            throw new IllegalArgumentException(
                "Código, nombre y empresa son obligatorios."
            );
        }

        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.empresa = empresa.trim();
    }

    private boolean vacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    @Override
    public boolean equals(Object otro) {
        if (!(otro instanceof Participante)) {
            return false;
        }

        Participante participante = (Participante) otro;
        return codigo.equalsIgnoreCase(participante.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo.toLowerCase());
    }

    @Override
    public String toString() {
        return codigo + " | " + nombre + " | " + empresa;
    }
}