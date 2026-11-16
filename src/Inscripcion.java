/** Estadísticas de un participante dentro de un torneo. */
public class Inscripcion implements Comparable<Inscripcion> {
    private final Participante participante;
    private int puntosTabla;
    private int diferencia;
    private int victorias;

    public Inscripcion(Participante participante) {
        if (participante == null) {
            throw new IllegalArgumentException("Falta el participante.");
        }

        this.participante = participante;
    }

    public Participante getParticipante() {
        return participante;
    }

    public int getPuntosTabla() {
        return puntosTabla;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public int getVictorias() {
        return victorias;
    }

    public void sumarResultado(int puntos, int diferencia, int victorias) {
        if (puntos < 0 || victorias < 0) {
            throw new IllegalArgumentException("Resultado de tabla inválido.");
        }

        puntosTabla += puntos;
        this.diferencia += diferencia;
        this.victorias += victorias;
    }

    /** Aplica los cuatro criterios de orden de la tabla. */
    @Override
    public int compareTo(Inscripcion otra) {
        int comparacion =
            Integer.compare(otra.puntosTabla, puntosTabla);

        if (comparacion == 0) {
            comparacion =
                Integer.compare(otra.diferencia, diferencia);
        }

        if (comparacion == 0) {
            comparacion =
                Integer.compare(otra.victorias, victorias);
        }

        if (comparacion == 0) {
            comparacion = participante.getNombre()
                .compareToIgnoreCase(
                    otra.participante.getNombre()
                );
        }

        if (comparacion == 0) {
            comparacion = participante.getCodigo()
                .compareToIgnoreCase(
                    otra.participante.getCodigo()
                );
        }

        return comparacion;
    }

    @Override
    public String toString() {
        return participante.getNombre()
            + " (" + participante.getEmpresa() + ")"
            + " | Puntos: " + puntosTabla
            + " | Diferencia: "
            + (diferencia >= 0 ? "+" : "") + diferencia
            + " | Victorias: " + victorias;
    }
}