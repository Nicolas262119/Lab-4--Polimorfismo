import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** Partido de dos participantes. */
public class Encuentro extends Evento {
    private final Participante participanteA;
    private final Participante participanteB;
    private final ReglasEncuentro reglas;

    private int marcadorA;
    private int marcadorB;
    private int penalesA = -1;
    private int penalesB = -1;

    public Encuentro(
        String codigo,
        Participante a,
        Participante b,
        ReglasEncuentro reglas
    ) {
        super(codigo);

        if (a == null || b == null
                || a.equals(b) || reglas == null) {
            throw new IllegalArgumentException(
                "Participantes o reglas inválidas."
            );
        }

        participanteA = a;
        participanteB = b;
        this.reglas = reglas;
    }

    public Participante getParticipanteA() {
        return participanteA;
    }

    public Participante getParticipanteB() {
        return participanteB;
    }

    public boolean requierePenales(int a, int b) {
        return reglas.requierePenales(a, b);
    }

    @Override
    public List<Participante> getParticipantes() {
        return Arrays.asList(participanteA, participanteB);
    }

    public void registrarResultado(
        Torneo torneo,
        int a, int b,
        int pa, int pb
    ) {
        if (!getEstado().equals("PROGRAMADO")) {
            throw new IllegalStateException(
                "Se requiere un encuentro programado y sin resultado."
            );
        }

        // Primero se valida el resultado. Solo después cambia la tabla.
        ResultadoEncuentro resultado =
            reglas.calcularResultado(a, b, pa, pb);

        torneo.aplicarResultadoEncuentro(this, resultado);

        marcadorA = a;
        marcadorB = b;
        penalesA = pa;
        penalesB = pb;
    }

    void cerrarResultado() {
        finalizar();
    }

    @Override
    public void leerYRegistrarResultado(
        Torneo torneo, Scanner teclado
    ) {
        System.out.println(
            participanteA.getNombre()
            + " vs " + participanteB.getNombre()
        );

        int a = Entrada.entero(
            teclado, "Marcador del primero: "
        );
        int b = Entrada.entero(
            teclado, "Marcador del segundo: "
        );

        int pa = -1;
        int pb = -1;

        if (requierePenales(a, b)) {
            pa = Entrada.entero(
                teclado, "Penales del primero: "
            );
            pb = Entrada.entero(
                teclado, "Penales del segundo: "
            );
        }

        registrarResultado(torneo, a, b, pa, pb);
    }

    @Override
    public String toString() {
        String texto = super.toString()
            + " | " + participanteA.getNombre()
            + " vs " + participanteB.getNombre();

        if (tieneResultado()) {
            texto += " | Marcador: "
                + marcadorA + "-" + marcadorB;

            if (penalesA >= 0) {
                texto += " | Penales: "
                    + penalesA + "-" + penalesB;
            }
        }

        return texto;
    }
}