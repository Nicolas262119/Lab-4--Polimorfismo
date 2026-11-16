/** Si los goles empatan, se necesitan penales diferentes. */
public class FutbolSalaConPenales extends FutbolSala {
    @Override
    public String getNombre() {
        return "FUTBOL_PENALES";
    }

    @Override
    public double getCuota() {
        return 750;
    }

    @Override
    public boolean requierePenales(int a, int b) {
        return a == b;
    }

    @Override
    public ResultadoEncuentro calcularResultado(
        int a, int b, int pa, int pb
    ) {
        marcadorNoNegativo(a, b);

        if (a != b) {
            return super.calcularResultado(a, b, pa, pb);
        }

        if (pa < 0 || pb < 0 || pa == pb) {
            throw new IllegalArgumentException(
                "Goles empatados requieren penales no negativos y distintos."
            );
        }

        return new ResultadoEncuentro(
            pa > pb ? 2 : 1,
            pb > pa ? 2 : 1,
            0, 0,
            pa > pb ? 1 : 0,
            pb > pa ? 1 : 0
        );
    }
}