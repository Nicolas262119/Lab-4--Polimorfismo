import java.util.ArrayList;
import java.util.List;

/** Genera una sola carrera con todos los inscritos. */
public class Relevos extends Disciplina {
    @Override
    public String getNombre() {
        return "RELEVOS";
    }

    @Override
    public int getMinimo() {
        return 3;
    }

    @Override
    public int getMaximo() {
        return 8;
    }

    @Override
    public double getCuota() {
        return 300;
    }

    @Override
    public List<Programable> generarEventos(
        List<Participante> participantes
    ) {
        List<Programable> eventos =
            new ArrayList<Programable>();

        eventos.add(new Carrera("C1", participantes));
        return eventos;
    }
}