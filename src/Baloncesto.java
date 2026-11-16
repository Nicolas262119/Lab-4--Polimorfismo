/** No admite empate; ganador 2 puntos y perdedor 1. */
public class Baloncesto extends DisciplinaEncuentros {
    @Override
    public String getNombre() {
        return "BALONCESTO";
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
        return 500;
    }

    @Override
    public ResultadoEncuentro calcularResultado(
        int a, int b, int pa, int pb
    ) {
        marcadorNoNegativo(a, b);
        sinPenales(pa, pb);

        if (a == b) {
            throw new IllegalArgumentException(
                "Baloncesto no permite empates."
            );
        }

        return new ResultadoEncuentro(
            a > b ? 2 : 1,
            b > a ? 2 : 1,
            a - b, b - a,
            a > b ? 1 : 0,
            b > a ? 1 : 0
        );
    }
}