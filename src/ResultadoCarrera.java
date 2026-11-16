/** Puntos y victoria obtenidos por una persona en relevos. */
public class ResultadoCarrera {
    private final Participante participante;
    private final int puntosTabla;
    private final int victorias;

    public ResultadoCarrera(
        Participante participante,
        int puntosTabla,
        int victorias
    ) {
        this.participante = participante;
        this.puntosTabla = puntosTabla;
        this.victorias = victorias;
    }

    public Participante getParticipante() {
        return participante;
    }

    public int getPuntosTabla() {
        return puntosTabla;
    }

    public int getVictorias() {
        return victorias;
    }
}