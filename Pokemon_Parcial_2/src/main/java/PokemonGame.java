import java.util.*;
import java.util.stream.Collectors;

// Excepciones personalizadas
class Excepciones {
    public static class OpcionInvalidaException extends Exception {
        public OpcionInvalidaException(String mensaje) { super(mensaje); }
    }
    public static class AtaqueFalladoException extends Exception {
        public AtaqueFalladoException(String mensaje) { super(mensaje); }
    }
}

// Interfaz funcional para reglas de daño
interface ReglaDano {
    int calcularDano(Pokemon atacante, Pokemon defensor);
}

// Clase para representar un ataque
class Ataque {
    private String nombre;
    private int precision;
    private int poderBase;
    private ReglaDano reglaDano;

    public Ataque(String nombre, int precision, int poderBase, ReglaDano reglaDano) {
        this.nombre = nombre;
        this.precision = precision;
        this.poderBase = poderBase;
        this.reglaDano = reglaDano;
    }

    public String getNombre() { return nombre; }
    public int getPrecision() { return precision; }
    public int getPoderBase() { return poderBase; }
    public ReglaDano getReglaDano() { return reglaDano; }

    @Override
    public String toString() {
        return nombre + " (Precisión: " + precision + "%)";
    }
}

// Clase abstracta para Pokémon
abstract class Pokemon {
    private String nombre;
    private int hpMaximo;
    private int hpActual;
    private String tipo;
    private List<Ataque> ataques;
    private int nivel;

    public Pokemon(String nombre, int hpMaximo, String tipo, int nivel) {
        this.nombre = nombre;
        this.hpMaximo = hpMaximo;
        this.hpActual = hpMaximo;
        this.tipo = tipo;
        this.nivel = nivel;
        this.ataques = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public int getHpMaximo() { return hpMaximo; }
    public int getHpActual() { return hpActual; }
    public String getTipo() { return tipo; }
    public int getNivel() { return nivel; }
    public List<Ataque> getAtaques() { return ataques; }

    public void setHpActual(int hpActual) {
        this.hpActual = Math.max(0, Math.min(hpMaximo, hpActual));
    }

    public void agregarAtaque(Ataque ataque) {
        ataques.add(ataque);
    }

    public boolean estaDebilitado() {
        return hpActual <= 0;  // ✅ CORREGIDO: hpActual en lugar de currentHp
    }

    public void curarCompleto() {
        this.hpActual = this.hpMaximo;
    }

    public abstract void aprenderAtaques();

    @Override
    public String toString() {
        return nombre + " (" + tipo + ") Nv." + nivel + " - HP: " + hpActual + "/" + hpMaximo;
    }
}

// Implementaciones concretas de Pokémon
class Charmander extends Pokemon {
    public Charmander() {
        super("Charmander", 105, "Fuego", 5);
        aprenderAtaques();
    }

    @Override
    public void aprenderAtaques() {
        agregarAtaque(new Ataque("Arañazo", 90, 40,
                (atacante, defensor) -> {
                    int danoBase = 40 + (int)(Math.random() * 6);
                    return calcularDanoElemental(danoBase, defensor.getTipo());
                }));

        agregarAtaque(new Ataque("Lanzallamas", 80, 90,
                (atacante, defensor) -> {
                    int danoBase = 90 + (int)(Math.random() * 11);
                    return calcularDanoElemental(danoBase, defensor.getTipo());
                }));
    }

    private int calcularDanoElemental(int danoBase, String tipoDefensor) {
        if (tipoDefensor.equals("Planta")) {
            return (int)(danoBase * 1.5);
        } else if (tipoDefensor.equals("Agua") || tipoDefensor.equals("Roca")) {
            return (int)(danoBase * 0.5);
        }
        return danoBase;
    }
}

class Squirtle extends Pokemon {
    public Squirtle() {
        super("Squirtle", 115, "Agua", 5);
        aprenderAtaques();
    }

    @Override
    public void aprenderAtaques() {
        agregarAtaque(new Ataque("Burbuja", 85, 40,
                (atacante, defensor) -> {
                    int danoBase = 40 + (int)(Math.random() * 5);
                    return calcularDanoElemental(danoBase, defensor.getTipo());
                }));

        agregarAtaque(new Ataque("Hidrobomba", 75, 110,
                (atacante, defensor) -> {
                    int danoBase = 110 + (int)(Math.random() * 11);
                    return calcularDanoElemental(danoBase, defensor.getTipo());
                }));
    }

    private int calcularDanoElemental(int danoBase, String tipoDefensor) {
        if (tipoDefensor.equals("Fuego") || tipoDefensor.equals("Roca")) {
            return (int)(danoBase * 1.5);
        } else if (tipoDefensor.equals("Planta") || tipoDefensor.equals("Eléctrico")) {
            return (int)(danoBase * 0.5);
        }
        return danoBase;
    }
}

class Bulbasaur extends Pokemon {
    public Bulbasaur() {
        super("Bulbasaur", 125, "Planta", 5);
        aprenderAtaques();
    }

    @Override
    public void aprenderAtaques() {
        agregarAtaque(new Ataque("Latigazo", 80, 45,
                (atacante, defensor) -> {
                    int danoBase = 45 + (int)(Math.random() * 4);
                    return calcularDanoElemental(danoBase, defensor.getTipo());
                }));

        agregarAtaque(new Ataque("Drenadoras", 85, 75,
                (atacante, defensor) -> {
                    int danoBase = 75 + (int)(Math.random() * 6);
                    return calcularDanoElemental(danoBase, defensor.getTipo());
                }));
    }

    private int calcularDanoElemental(int danoBase, String tipoDefensor) {
        if (tipoDefensor.equals("Agua") || tipoDefensor.equals("Roca")) {
            return (int)(danoBase * 1.5);
        } else if (tipoDefensor.equals("Fuego") || tipoDefensor.equals("Volador")) {
            return (int)(danoBase * 0.5);
        }
        return danoBase;
    }
}

class Pikachu extends Pokemon {
    public Pikachu() {
        super("Pikachu", 95, "Eléctrico", 5);
        aprenderAtaques();
    }

    @Override
    public void aprenderAtaques() {
        agregarAtaque(new Ataque("Impactrueno", 95, 40,
                (atacante, defensor) -> {
                    int danoBase = 40 + (int)(Math.random() * 5);
                    return calcularDanoElemental(danoBase, defensor.getTipo());
                }));

        agregarAtaque(new Ataque("Rayo", 75, 120,
                (atacante, defensor) -> {
                    int danoBase = 120 + (int)(Math.random() * 11);
                    return calcularDanoElemental(danoBase, defensor.getTipo());
                }));
    }

    private int calcularDanoElemental(int danoBase, String tipoDefensor) {
        if (tipoDefensor.equals("Agua") || tipoDefensor.equals("Volador")) {
            return (int)(danoBase * 1.5);
        } else if (tipoDefensor.equals("Planta") || tipoDefensor.equals("Eléctrico") || tipoDefensor.equals("Tierra")) {
            return (int)(danoBase * 0.5);
        }
        return danoBase;
    }
}

// Sistema de batalla mejorado
class SistemaBatalla {
    private List<String> registroBatalla = new ArrayList<>();
    private List<Integer> historialDano = new ArrayList<>();
    private Map<String, Integer> contadorEventos = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    public SistemaBatalla() {
        contadorEventos.put("Jugador", 0);
        contadorEventos.put("CPU", 0);
    }

    public Pokemon seleccionarPokemonJugador(String nombreJugador) throws Excepciones.OpcionInvalidaException {
        Map<Integer, Pokemon> pokedex = new HashMap<>();
        pokedex.put(1, new Charmander());
        pokedex.put(2, new Squirtle());
        pokedex.put(3, new Bulbasaur());
        pokedex.put(4, new Pikachu());

        System.out.println("\n" + nombreJugador + ", elige tu compañero Pokémon:");
        System.out.println("1. 🔥 Charmander (Fuego)");
        System.out.println("2. 💧 Squirtle (Agua)");
        System.out.println("3. 🌿 Bulbasaur (Planta)");
        System.out.println("4. ⚡ Pikachu (Eléctrico)");
        System.out.print("Tu elección (1-4): ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion < 1 || opcion > 4) {
                throw new Excepciones.OpcionInvalidaException("Debes elegir un número entre 1 y 4.");
            }

            Pokemon elegido = pokedex.get(opcion);
            System.out.println("\n¡Excelente! Has elegido a " + elegido.getNombre() + "!");
            return elegido;

        } catch (NumberFormatException e) {
            throw new Excepciones.OpcionInvalidaException("Por favor, ingresa un número válido.");
        }
    }

    public Pokemon seleccionarPokemonRival(Pokemon pokemonJugador) {
        List<Pokemon> pokemonesDisponibles = Arrays.asList(
                new Charmander(), new Squirtle(), new Bulbasaur(), new Pikachu()
        );

        List<Pokemon> oponentes = pokemonesDisponibles.stream()
                .filter(p -> !p.getNombre().equals(pokemonJugador.getNombre()))
                .collect(Collectors.toList());

        Pokemon rival = oponentes.get(random.nextInt(oponentes.size()));
        System.out.println("\n¡Un " + rival.getNombre() + " salvaje apareció!");
        return rival;
    }

    public void realizarTurnoJugador(String nombreJugador, Pokemon jugador, Pokemon rival) {
        System.out.println("\n===== TURNO DE " + nombreJugador.toUpperCase() + " =====");
        System.out.println("Elige un movimiento:");

        List<Ataque> movimientos = jugador.getAtaques().stream()
                .sorted(Comparator.comparingInt(Ataque::getPrecision).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < movimientos.size(); i++) {
            Ataque ataque = movimientos.get(i);
            System.out.println((i + 1) + ". " + ataque + " - Poder: " + ataque.getPoderBase());
        }

        try {
            System.out.print("Tu movimiento (1-" + movimientos.size() + "): ");
            int eleccion = Integer.parseInt(scanner.nextLine());

            if (eleccion < 1 || eleccion > movimientos.size()) {
                throw new Excepciones.OpcionInvalidaException("Selección inválida. Pierdes tu turno.");
            }

            Ataque ataqueElegido = movimientos.get(eleccion - 1);
            ejecutarMovimiento(nombreJugador, jugador, rival, ataqueElegido);
            contadorEventos.put("Jugador", contadorEventos.get("Jugador") + 1);

        } catch (NumberFormatException | Excepciones.OpcionInvalidaException e) {
            System.out.println("❌ " + e.getMessage());
            registroBatalla.add(nombreJugador + " perdió su turno por elección inválida.");
        }
    }

    public void realizarTurnoRival(Pokemon jugador, Pokemon rival) {
        System.out.println("\n===== TURNO DEL RIVAL =====");

        Ataque ataqueRival = rival.getAtaques().stream()
                .max(Comparator.comparingInt(Ataque::getPoderBase))
                .orElse(rival.getAtaques().get(0));

        System.out.println("El rival usa: " + ataqueRival.getNombre() + "!");
        ejecutarMovimiento("Rival", rival, jugador, ataqueRival);
        contadorEventos.put("CPU", contadorEventos.get("CPU") + 1);
    }

    private void ejecutarMovimiento(String atacante, Pokemon pokemonAtacante, Pokemon pokemonDefensor, Ataque ataque) {
        try {
            if (random.nextInt(100) >= ataque.getPrecision()) {
                throw new Excepciones.AtaqueFalladoException("El movimiento falló.");
            }

            int dano = ataque.getReglaDano().calcularDano(pokemonAtacante, pokemonDefensor);
            int nuevoHp = pokemonDefensor.getHpActual() - dano;
            pokemonDefensor.setHpActual(nuevoHp);

            historialDano.add(dano);
            String evento = atacante + " usó " + ataque.getNombre() + " -> " + dano + " de daño!";
            registroBatalla.add(evento);
            System.out.println("💥 " + evento);

            if (pokemonDefensor.estaDebilitado()) {
                System.out.println("\n✨ " + pokemonDefensor.getNombre() + " se debilitó!");
            }

        } catch (Excepciones.AtaqueFalladoException e) {
            String evento = atacante + " usó " + ataque.getNombre() + " pero falló!";
            registroBatalla.add(evento);
            System.out.println("❌ " + evento);
        }
    }

    public void determinarResultado(String nombreJugador, Pokemon jugador, Pokemon rival) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           🏁 BATALLA CONCLUIDA 🏁");
        System.out.println("=".repeat(50));

        if (jugador.estaDebilitado() && rival.estaDebilitado()) {
            System.out.println("🤝 ¡ES UN EMPATE! Ambos Pokémon dieron lo mejor de sí.");
            registroBatalla.add("Resultado: Empate honorable");
        } else if (jugador.estaDebilitado()) {
            System.out.println("😔 " + nombreJugador + " ha sido derrotado... " + rival.getNombre() + " gana!");
            registroBatalla.add("Resultado: Victoria del Rival");
        } else {
            System.out.println("🎉 ¡FELICIDADES " + nombreJugador + "! " + jugador.getNombre() + " es el vencedor!");
            registroBatalla.add("Resultado: Victoria de " + nombreJugador);
        }
    }

    public void mostrarEstadisticasBatalla(String nombreJugador) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           📊 ESTADÍSTICAS DE BATALLA 📊");
        System.out.println("=".repeat(50));

        System.out.println("\n📝 REGISTRO DE EVENTOS:");
        registroBatalla.forEach(evento -> System.out.println("• " + evento));

        System.out.println("\n📈 MÉTRICAS:");

        long totalFallos = registroBatalla.stream()
                .filter(evento -> evento.contains("falló"))
                .count();
        System.out.println("• Movimientos fallados: " + totalFallos);

        OptionalInt maxDano = historialDano.stream().mapToInt(Integer::intValue).max();  // ✅ CORREGIDO
        OptionalInt minDano = historialDano.stream().mapToInt(Integer::intValue).min();  // ✅ CORREGIDO

        System.out.println("• Daño máximo: " + (maxDano.isPresent() ? maxDano.getAsInt() : 0));
        System.out.println("• Daño mínimo: " + (minDano.isPresent() ? minDano.getAsInt() : 0));

        double promedioDano = historialDano.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        System.out.println("• Daño promedio: " + String.format("%.1f", promedioDano));

        System.out.println("\n👥 ACCIONES POR ENTRENADOR:");
        contadorEventos.forEach((entrenador, acciones) -> {
            String nombre = entrenador.equals("Jugador") ? nombreJugador : "Rival";
            System.out.println("• " + nombre + ": " + acciones + " movimientos");
        });

        long totalTurnos = contadorEventos.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("• Total de turnos: " + totalTurnos);
    }
}

// Clase principal del juego
public class PokemonGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaBatalla sistema = new SistemaBatalla();

        System.out.println("✨ ¡BIENVENIDO AL MUNDO POKÉMON! ✨");
        System.out.println("=".repeat(40));
        System.out.print("¿Cuál es tu nombre, entrenador? ");
        String nombreEntrenador = scanner.nextLine();

        try {
            Pokemon pokemonJugador = sistema.seleccionarPokemonJugador(nombreEntrenador);
            Pokemon pokemonRival = sistema.seleccionarPokemonRival(pokemonJugador);

            System.out.println("\n¡COMIENZA LA BATALLA!");
            System.out.println("⚔️  " + nombreEntrenador + ": " + pokemonJugador);
            System.out.println("⚔️  Rival: " + pokemonRival);

            boolean turnoJugador = true;
            int numeroTurno = 1;

            while (!pokemonJugador.estaDebilitado() && !pokemonRival.estaDebilitado()) {
                System.out.println("\n" + "═".repeat(30));
                System.out.println("          TURNO " + numeroTurno);
                System.out.println("═".repeat(30));

                if (turnoJugador) {
                    sistema.realizarTurnoJugador(nombreEntrenador, pokemonJugador, pokemonRival);
                } else {
                    sistema.realizarTurnoRival(pokemonJugador, pokemonRival);
                }

                System.out.println("\nESTADO ACTUAL:");
                System.out.println("❤️  " + nombreEntrenador + ": " + pokemonJugador);
                System.out.println("❤️  Rival: " + pokemonRival);

                turnoJugador = !turnoJugador;
                numeroTurno++;

                // Pequeña pausa para mejor experiencia
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }

            sistema.determinarResultado(nombreEntrenador, pokemonJugador, pokemonRival);
            sistema.mostrarEstadisticasBatalla(nombreEntrenador);

            System.out.println("\n🎮 ¡Gracias por jugar!");

        } catch (Excepciones.OpcionInvalidaException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}