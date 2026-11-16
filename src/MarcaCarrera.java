/** Una marca consiste en un tiempo o una descalificación. */
public class MarcaCarrera {
    private final Participante participante;
    private final Double tiempoSegundos;
    private final boolean descalificado;

    public MarcaCarrera(
        Participante participante,
        Double tiempoSegundos,
        boolean descalificado
    ) {
        if (participante == null
                || (descalificado && tiempoSegundos != null)
                || (!descalificado
                    && (tiempoSegundos == null
                        || !Double.isFinite(tiempoSegundos)
                        || tiempoSegundos <= 0))) {
            throw new IllegalArgumentException(
                "La marca debe ser tiempo positivo "
                + "o descalificación."
            );
        }

        this.participante = participante;
        this.tiempoSegundos = tiempoSegundos;
        this.descalificado = descalificado;
    }

    public Participante getParticipante() {
        return participante;
    }

    public Double getTiempoSegundos() {
        return tiempoSegundos;
    }

    public boolean isDescalificado() {
        return descalificado;
    }
}