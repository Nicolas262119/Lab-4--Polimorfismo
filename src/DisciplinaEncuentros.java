import java.util.ArrayList;
import java.util.List;

/**
 * Los deportes de encuentros generan cada pareja una sola vez.
 * La subclase decide cómo se puntúa el marcador.
 */
public abstract class DisciplinaEncuentros
        extends Disciplina implements ReglasEncuentro {

    @Override
    public List<Programable> generarEventos(
        List<Participante> participantes
    ) {
        List<Programable> eventos =
            new ArrayList<Programable>();

        for (int i = 0; i < participantes.size(); i++) {
            for (int j = i + 1; j < participantes.size(); j++) {
                eventos.add(new Encuentro(
                    "E" + (eventos.size() + 1),
                    participantes.get(i),
                    participantes.get(j),
                    this
                ));
            }
        }

        return eventos;
    }

    protected void marcadorNoNegativo(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException(
                "El marcador debe contener enteros no negativos."
            );
        }
    }

    protected void sinPenales(int pa, int pb) {
        if (pa != -1 || pb != -1) {
            throw new IllegalArgumentException(
                "Esta situación no admite penales."
            );
        }
    }
}