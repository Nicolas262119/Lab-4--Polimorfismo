import java.time.LocalDate;
import java.util.Scanner;

/** Menú principal y carga inicial de ArenaGT. */
public class Main {
    private static final Scanner teclado =
        new Scanner(System.in);

    public static void main(String[] args) {
        ArenaGT arena = cargarDatosIniciales();

        while (true) {
            System.out.println(
                "\n=== ARENA GT ==="
                + "\n1. Registrar participante"
                + "\n2. Crear torneo"
                + "\n3. Cotizar inscripción"
                + "\n4. Inscribir participante"
                + "\n5. Cerrar inscripciones"
                + "\n6. Programar o reprogramar evento"
                + "\n7. Registrar resultado"
                + "\n8. Consultar participantes"
                + "\n9. Consultar torneos y tabla"
                + "\n10. Calendario general"
                + "\n11. Calendario por torneo"
                + "\n12. Calendario por participante"
                + "\n13. Ingresos y premios"
                + "\n14. Ranking de empresas"
                + "\n15. Salir"
            );

            try {
                int opcion = Entrada.entero(
                    teclado, "Opción: "
                );

                switch (opcion) {
                    case 1:
                        arena.registrarParticipante(
                            new Participante(
                                Entrada.texto(
                                    teclado, "Código: "
                                ),
                                Entrada.texto(
                                    teclado, "Nombre: "
                                ),
                                Entrada.texto(
                                    teclado, "Empresa: "
                                )
                            )
                        );

                        System.out.println(
                            "Participante registrado."
                        );
                        break;

                    case 2:
                        String codigo = Entrada.texto(
                            teclado, "Código del torneo: "
                        );

                        String nombre = Entrada.texto(
                            teclado, "Nombre: "
                        );

                        arena.crearTorneo(new Torneo(
                            codigo,
                            nombre,
                            seleccionarDisciplina()
                        ));

                        System.out.println(
                            "Torneo creado en inscripción."
                        );
                        break;

                    case 3:
                        Disciplina disciplina =
                            seleccionarDisciplina();

                        int cantidad = Entrada.entero(
                            teclado,
                            "Cantidad de participantes: "
                        );

                        System.out.printf(
                            "Cotización: Q%.2f "
                            + "(sin inscripción ni cobro)%n",
                            arena.cotizar(
                                disciplina, cantidad
                            )
                        );
                        break;

                    case 4:
                        arena.inscribir(
                            pedirTorneo(arena),
                            pedirParticipante(arena)
                        );

                        System.out.println(
                            "Inscripción aceptada "
                            + "y cuota cobrada."
                        );
                        break;

                    case 5:
                        Torneo cerrar =
                            pedirTorneo(arena);

                        cerrar.cerrarInscripciones();

                        System.out.println(
                            "Torneo en juego. "
                            + "Eventos creados:"
                        );

                        for (Programable evento :
                                cerrar.getEventos()) {
                            System.out.println(evento);
                        }
                        break;

                    case 6:
                        Torneo torneoProgramar =
                            pedirTorneo(arena);

                        mostrarEventos(
                            torneoProgramar
                        );

                        Programable programable =
                            pedirEvento(
                                torneoProgramar
                            );

                        arena.programar(
                            programable,
                            Entrada.fecha(
                                teclado,
                                "Fecha (AAAA-MM-DD): "
                            ),
                            Entrada.entero(
                                teclado,
                                "Hora de 0 a 23: "
                            ),
                            Entrada.texto(
                                teclado, "Sede: "
                            )
                        );

                        System.out.println(
                            "Programación guardada."
                        );
                        break;

                    case 7:
                        Torneo torneoResultado =
                            pedirTorneo(arena);

                        mostrarEventos(
                            torneoResultado
                        );

                        Programable elegido =
                            pedirEvento(
                                torneoResultado
                            );

                        if (!torneoResultado
                                .getEstado()
                                .equals("EN_JUEGO")
                                || !elegido
                                    .getEstado()
                                    .equals("PROGRAMADO")) {
                            throw new IllegalStateException(
                                "El resultado exige un evento "
                                + "programado de un torneo en juego."
                            );
                        }

                        ((Evento) elegido)
                            .leerYRegistrarResultado(
                                torneoResultado,
                                teclado
                            );

                        System.out.println(
                            "Resultado registrado. Torneo: "
                            + torneoResultado.getEstado()
                        );
                        break;

                    case 8:
                        for (Participante participante :
                                arena.getParticipantes()) {
                            System.out.println(
                                participante
                            );
                        }
                        break;

                    case 9:
                        for (Torneo torneo :
                                arena.getTorneos()) {
                            System.out.println(
                                torneo
                            );
                        }

                        Torneo tabla =
                            pedirTorneo(arena);

                        int puesto = 1;

                        for (Inscripcion inscripcion :
                                tabla.tablaPosiciones()) {
                            System.out.println(
                                puesto + ". "
                                + inscripcion
                            );
                            puesto++;
                        }

                        if (tabla.getCampeon()
                                != null) {
                            System.out.printf(
                                "Campeón: %s "
                                + "| Premio: Q%.2f%n",
                                tabla.getCampeon()
                                    .getNombre(),
                                tabla.getPremioEntregado()
                            );
                        }
                        break;

                    case 10:
                        mostrarCalendario(
                            arena.calendarioGeneral()
                        );
                        break;

                    case 11:
                        mostrarCalendario(
                            arena.calendarioTorneo(
                                pedirTorneo(arena)
                            )
                        );
                        break;

                    case 12:
                        mostrarCalendario(
                            arena.calendarioParticipante(
                                pedirParticipante(arena)
                            )
                        );
                        break;

                    case 13:
                        System.out.println(
                            arena.reporteIngresosPremios()
                        );
                        break;

                    case 14:
                        System.out.println(
                            arena.rankingEmpresas()
                        );
                        break;

                    case 15:
                        System.out.println(
                            "Hasta luego."
                        );
                        return;

                    default:
                        System.out.println(
                            "Seleccione un número "
                            + "entre 1 y 15."
                        );
                }

            } catch (
                IllegalArgumentException
                | IllegalStateException error
            ) {
                System.out.println(
                    "Operación rechazada: "
                    + error.getMessage()
                );

                if (error.getMessage()
                        .equals("Fin de la entrada.")) {
                    return;
                }
            }
        }
    }

    private static Disciplina seleccionarDisciplina() {
        System.out.println(
            "1. Fútbol sala"
            + " | 2. Fútbol con penales"
            + " | 3. Baloncesto"
            + " | 4. Voleibol"
            + " | 5. Relevos"
        );

        switch (Entrada.entero(
                teclado, "Disciplina: "
            )) {
            case 1:
                return new FutbolSala();

            case 2:
                return new FutbolSalaConPenales();

            case 3:
                return new Baloncesto();

            case 4:
                return new Voleibol();

            case 5:
                return new Relevos();

            default:
                throw new IllegalArgumentException(
                    "Disciplina inexistente."
                );
        }
    }

    private static Torneo pedirTorneo(
        ArenaGT arena
    ) {
        Torneo torneo = arena.buscarTorneo(
            Entrada.texto(
                teclado,
                "Código del torneo: "
            )
        );

        if (torneo == null) {
            throw new IllegalArgumentException(
                "Torneo no encontrado."
            );
        }

        return torneo;
    }

    private static Participante pedirParticipante(
        ArenaGT arena
    ) {
        Participante participante =
            arena.buscarParticipante(
                Entrada.texto(
                    teclado,
                    "Código del participante: "
                )
            );

        if (participante == null) {
            throw new IllegalArgumentException(
                "Participante no encontrado."
            );
        }

        return participante;
    }

    private static Programable pedirEvento(
        Torneo torneo
    ) {
        Programable evento =
            torneo.buscarEvento(
                Entrada.texto(
                    teclado,
                    "Código del evento: "
                )
            );

        if (evento == null) {
            throw new IllegalArgumentException(
                "Evento no encontrado "
                + "en ese torneo."
            );
        }

        return evento;
    }

    private static void mostrarEventos(
        Torneo torneo
    ) {
        for (Programable evento :
                torneo.getEventos()) {
            System.out.println(evento);
        }
    }

    private static void mostrarCalendario(
        Calendario calendario
    ) {
        int cantidad = 0;

        for (Programable evento :
                calendario) {
            System.out.println(evento);
            cantidad++;
        }

        if (cantidad == 0) {
            System.out.println(
                "No hay eventos programados."
            );
        }
    }

    private static void cargarParticipantes(
        Torneo torneo,
        Participante... participantes
    ) {
        for (Participante participante :
                participantes) {
            torneo.cargarInscripcionInicial(
                participante
            );
        }
    }

    /**
     * Agrega resultados históricos sin cobrar
     * las inscripciones de la carga inicial.
     */
    private static ArenaGT cargarDatosIniciales() {
        ArenaGT arena = new ArenaGT();

        Participante[] p = {
            new Participante(
                "P01", "Águilas", "Alfa"
            ),
            new Participante(
                "P02", "Centellas", "Alfa"
            ),
            new Participante(
                "P03", "Cóndores", "Beta"
            ),
            new Participante(
                "P04", "Dragones", "Beta"
            ),
            new Participante(
                "P05", "Estrellas", "Gamma"
            ),
            new Participante(
                "P06", "Fénix", "Gamma"
            ),
            new Participante(
                "P07", "Gladiadores", "Delta"
            ),
            new Participante(
                "P08", "Halcones", "Delta"
            )
        };

        for (Participante participante : p) {
            arena.registrarParticipante(
                participante
            );
        }

        Torneo futbol = new Torneo(
            "T01", "Copa Sala",
            new FutbolSala()
        );

        Torneo penales = new Torneo(
            "T02", "Copa Penales",
            new FutbolSalaConPenales()
        );

        Torneo baloncesto = new Torneo(
            "T03", "Copa Baloncesto",
            new Baloncesto()
        );

        Torneo voleibol = new Torneo(
            "T04", "Copa Voleibol",
            new Voleibol()
        );

        Torneo relevos = new Torneo(
            "T05", "Copa Relevos",
            new Relevos()
        );

        arena.crearTorneo(futbol);
        arena.crearTorneo(penales);
        arena.crearTorneo(baloncesto);
        arena.crearTorneo(voleibol);
        arena.crearTorneo(relevos);

        cargarParticipantes(
            futbol,
            p[0], p[2], p[4],
            p[6], p[7]
        );

        cargarParticipantes(
            penales,
            p[0], p[1], p[2], p[4]
        );

        cargarParticipantes(
            baloncesto,
            p[1], p[3], p[5], p[7]
        );

        cargarParticipantes(
            voleibol,
            p[0], p[2], p[4], p[6]
        );

        cargarParticipantes(
            relevos,
            p[1], p[3], p[5]
        );

        futbol.cerrarInscripciones();
        penales.cerrarInscripciones();
        baloncesto.cerrarInscripciones();
        voleibol.cerrarInscripciones();

        // T01 queda en juego. Estos resultados
        // muestran desempates por diferencia
        // y por victorias.
        cargarEncuentro(
            arena, futbol, "E1",
            2026, 12, 1,
            "Cancha A", 2, 0, -1, -1
        );

        cargarEncuentro(
            arena, futbol, "E2",
            2026, 12, 2,
            "Cancha A", 0, 2, -1, -1
        );

        cargarEncuentro(
            arena, futbol, "E3",
            2026, 12, 3,
            "Cancha A", 0, 0, -1, -1
        );

        cargarEncuentro(
            arena, futbol, "E6",
            2026, 12, 4,
            "Cancha A", 0, 0, -1, -1
        );

        cargarEncuentro(
            arena, futbol, "E7",
            2026, 12, 5,
            "Cancha A", 1, 0, -1, -1
        );

        cargarEncuentro(
            arena, futbol, "E8",
            2026, 12, 6,
            "Cancha A", 0, 0, -1, -1
        );

        cargarEncuentro(
            arena, futbol, "E10",
            2026, 12, 7,
            "Cancha A", 0, 0, -1, -1
        );

        // E1 de T02 termina empatado en goles
        // y se define por penales.
        cargarEncuentro(
            arena, penales, "E1",
            2026, 12, 8,
            "Cancha B", 1, 1, 4, 3
        );

        cargarEncuentro(
            arena, baloncesto, "E1",
            2026, 12, 9,
            "Gimnasio", 70, 68, -1, -1
        );

        cargarEncuentro(
            arena, voleibol, "E1",
            2026, 12, 10,
            "Coliseo", 3, 2, -1, -1
        );

        cargarEncuentro(
            arena, voleibol, "E2",
            2026, 12, 11,
            "Coliseo", 3, 0, -1, -1
        );

        return arena;
    }

    private static void cargarEncuentro(
        ArenaGT arena,
        Torneo torneo,
        String codigo,
        int anio,
        int mes,
        int dia,
        String sede,
        int marcadorA,
        int marcadorB,
        int penalesA,
        int penalesB
    ) {
        Programable evento =
            torneo.buscarEvento(codigo);

        arena.programar(
            evento,
            LocalDate.of(
                anio, mes, dia
            ),
            10,
            sede
        );

        ((Encuentro) evento)
            .registrarResultado(
                torneo,
                marcadorA,
                marcadorB,
                penalesA,
                penalesB
            );
    }
}