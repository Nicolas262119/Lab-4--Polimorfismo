/** Cada deporte decide si el marcador es válido y cómo puntúa. */
public interface ReglasEncuentro {
    ResultadoEncuentro calcularResultado(
        int marcadorA, int marcadorB,
        int penalesA, int penalesB
    );

    default boolean requierePenales(int a, int b) {
        return false;
    }
}