import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/** Programación y estado comunes de encuentros y carreras. */
public abstract class Evento implements Programable {
    private final String codigo;
    private LocalDate fecha;
    private int hora;
    private String sede;
    private String estado = "SIN_PROGRAMAR";

    protected Evento(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Código de evento vacío."
            );
        }

        this.codigo = codigo.trim();
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public int getHora() {
        return hora;
    }

    @Override
    public String getSede() {
        return sede;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public boolean tieneResultado() {
        return estado.equals("FINALIZADO");
    }

    @Override
    public void programar(
        LocalDate fecha, int hora, String sede
    ) {
        if (tieneResultado()) {
            throw new IllegalStateException(
                "Un evento finalizado no puede reprogramarse."
            );
        }

        if (fecha == null || hora < 0 || hora > 23
                || sede == null || sede.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Fecha, hora (0 a 23) y sede válidas son obligatorias."
            );
        }

        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
        estado = "PROGRAMADO";
    }

    protected void finalizar() {
        if (!estado.equals("PROGRAMADO")) {
            throw new IllegalStateException(
                "El evento debe estar programado y sin resultado."
            );
        }

        estado = "FINALIZADO";
    }

    /** Orden cronológico: fecha, hora y sede. */
    @Override
    public int compareTo(Programable otro) {
        int comparacion = fecha.compareTo(otro.getFecha());

        if (comparacion == 0) {
            comparacion = Integer.compare(hora, otro.getHora());
        }

        if (comparacion == 0) {
            comparacion =
                sede.compareToIgnoreCase(otro.getSede());
        }

        if (comparacion == 0) {
            comparacion =
                codigo.compareToIgnoreCase(otro.getCodigo());
        }

        return comparacion;
    }

    @Override
    public abstract List<Participante> getParticipantes();

    /**
     * Cada tipo de evento solicita los datos que necesita.
     * El menú no pregunta si el objeto es encuentro o carrera.
     */
    public abstract void leerYRegistrarResultado(
        Torneo torneo, Scanner teclado
    );

    @Override
    public String toString() {
        return codigo + " | " + estado
            + (fecha == null
                ? ""
                : " | " + fecha
                    + " " + String.format("%02d:00", hora)
                    + " | " + sede);
    }
}