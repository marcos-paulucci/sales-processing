----------------------
Instalar dependencias con 'mvn install'
----------------------
Ejecutar tests con 'mvn test'
-------------------------
Diseno de la solucion

La solucion resuelve el problema principal, y tambien los items
* Qué pasa  con una llamada cuando  no hay ningún  empleado  libre.
* Más de  10  llamadas  concurrentes que slots.
* Tests unitarios correspondientes.
* Documentación de código.

La clase CallCenter es la interfaz/api al usuario. Tiene referencia al pool de empleados, cuya clase es EmpleadosPool, y recibe las llamadas a traves de su metodo handleCalls. Este metodo delega a la clase CallsDispatcher. La clase calldispatcher centraliza la logica de la resolucion de llamadas, obteniendo empleados disponibles del pool de empleados, y delegando cada llamada al empleado libre encontrado. Cuando no hay empleado libre, se encola la llamada para ser atendida cuando se libere un empleado.
Los diagramas de clase y secuencia son simples para entender el diseno.

-------------------------
Configuracion de la aplicacion en config.properties. Properties que contiene:
*Numero de threads a utilizar por el sistema para atender llamadas
*Numero de operadores
*Numero de supervisores
*Numero de directores
*Tiempo minimo de llamada para el calculo aleatorio
*Tiempo maximo de llamada para el calculo aleatorio
--------------
Aclaraciones:

* Para poder correr los tests sin esperar tanto tiempo, se redujo los tiempos de llamada al intervalo de 1 a dos segundos.
* Dado que es una prueba "asincrona" donde cada thread maneja sus tiempos y el tiempo de llamada es aleatorio, en cada test
  se invoca a sleep previo a los asserts, para asegurar que las llamadas fueron atendidas y no obtener nulls. Se podria mejorar esto
  usando callbacks y futures
