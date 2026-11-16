/** Victoria: 3 puntos; empate: 1; derrota: 0. */
public class FutbolSala extends DisciplinaEncuentros {
    @Override
    public String getNombre() {
        return "FUTBOL_SALA";
    }

    @Override
    public int getMinimo() {
        return 4;
    }

    @Override
    public int getMaximo() {
        return 8;
    }

    @Override
    public double getCuota() {
        return 600;
    }

    @Override
    public ResultadoEncuentro calcularResultado(
        int a, int b, int pa, int pb
    ) {
        marcadorNoNegativo(a, b);
        sinPenales(pa, pb);

        int victoriaA = a > b ? 1 : 0;
        int victoriaB = b > a ? 1 : 0;

        int puntosA = a > b ? 3 : a == b ? 1 : 0;
        int puntosB = b > a ? 3 : a == b ? 1 : 0;

        return new ResultadoEncuentro(
            puntosA, puntosB,
            a - b, b - a,
            victoriaA, victoriaB
        );
    }
}