import java.util.List;

/** Reglas comunes que todo tipo de torneo debe proporcionar. */
public abstract class Disciplina {
    public abstract String getNombre();

    public abstract int getMinimo();

    public abstract int getMaximo();

    public abstract double getCuota();

    public abstract List<Programable> generarEventos(
        List<Participante> participantes
    );
}   