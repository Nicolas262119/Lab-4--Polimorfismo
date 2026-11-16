/** Marcadores válidos: 3-0, 3-1 y 3-2, en cualquier orden. */
public class Voleibol extends DisciplinaEncuentros {
    @Override
    public String getNombre() {
        return "VOLEIBOL";
    }

    @Override
    public int getMinimo() {
        return 4;
    }

    @Override
    public int getMaximo() {
        return 6;
    }

    @Override
    public double getCuota() {
        return 450;
    }

    @Override
    public ResultadoEncuentro calcularResultado(
        int a, int b, int pa, int pb
    ) {
        sinPenales(pa, pb);

        boolean ganaA = a == 3 && b >= 0 && b <= 2;
        boolean ganaB = b == 3 && a >= 0 && a <= 2;

        if (!ganaA && !ganaB) {
            throw new IllegalArgumentException(
                "Voleibol exige 3 sets para el ganador "
                + "y 0, 1 o 2 para el perdedor."
            );
        }

        boolean ajustado = Math.min(a, b) == 2;

        int puntosA = ganaA
            ? (ajustado ? 2 : 3)
            : (ajustado ? 1 : 0);

        int puntosB = ganaA
            ? (ajustado ? 1 : 0)
            : (ajustado ? 2 : 3);

        return new ResultadoEncuentro(
            puntosA, puntosB,
            a - b, b - a,
            ganaA ? 1 : 0,
            ganaA ? 0 : 1
        );
    }
}