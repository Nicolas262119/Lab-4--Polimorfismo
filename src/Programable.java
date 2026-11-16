import java.time.LocalDate;
import java.util.List;

/** Contrato de los eventos que aparecen en calendarios. */
public interface Programable extends Comparable<Programable> {
    String getCodigo();

    LocalDate getFecha();

    int getHora();

    String getSede();

    String getEstado();

    List<Participante> getParticipantes();

    boolean tieneResultado();

    void programar(LocalDate fecha, int hora, String sede);
}