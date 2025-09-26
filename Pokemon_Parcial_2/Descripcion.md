🎮 Juego de Pokémon - Documentación Técnica
👨‍💻 Información del Desarrollador
Nombre: Marlyn Higueros

Carrera: Ingeniería en Sistemas

Universidad: Universidad Mariano Gálvez de Guatemala

Asignatura: Programación II

Examen: Parcial II - POO, Excepciones, Colecciones, Streams y Lambdas

📋 Descripción del Proyecto
Implementación de un juego de batallas Pokémon por turnos en Java que demuestra el dominio de los conceptos avanzados de programación orientada a objetos y funcional.

🎯 Requisitos Cumplidos
✅ Programación Orientada a Objetos
Clase abstracta Pokemon con encapsulamiento completo

4 subclases concretas (Charmander, Squirtle, Bulbasaur, Pikachu)

Atributos privados con métodos getters/setters

Métodos abstractos y concretos

✅ Polimorfismo
Sobrescritura de métodos en cada subclase de Pokémon

Comportamiento específico por tipo elemental

Ataques únicos con diferentes reglas de daño

✅ Manejo de Excepciones
OpcionInvalidaException - Para entradas incorrectas del usuario

AtaqueFalladoException - Para ataques que fallan por precisión

Manejo de NumberFormatException - Validación de entrada numérica

✅ Colecciones del JDK
List<String> - Registro de eventos de batalla

List<Integer> - Historial de daño

Map<Integer, Pokemon> - Pokédex de selección

Map<String, Integer> - Contador de eventos por jugador

✅ Streams y Expresiones Lambda
Interfaz funcional ReglaDano con lambdas para cálculo de daño

Streams para procesamiento:

.stream().filter() - Filtrar Pokémon disponibles

.stream().sorted() - Ordenar ataques por precisión

.stream().mapToInt().average() - Calcular promedio de daño

.stream().max() - Encontrar máximo valor