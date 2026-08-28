1. Introduccion

En este trabajo se analizo un sistema bancario legacy desarrollado en C++. Durante el analisis se identificaron problemas relacionados con la gestion manual de memoria y con la forma en que se manejan los diferentes tipos de cuentas.

A partir de estos hallazgos se realizo un rediseno en Java utilizando Programacion Orientada a Objetos.

2. Hallazgos de la auditoria en C++

En el codigo original se identificaron dos reservas de memoria dinamica.

La primera ocurre al crear una cuenta:

CuentaLegacy* c = new CuentaLegacy();

La segunda ocurre al crear el espacio para almacenar el titular:

c->titular = new char[50];

El problema es que la memoria creada con new debe liberarse manualmente cuando ya no se necesita. En el codigo original no se realiza esta liberacion.

Para liberar correctamente la memoria se debe hacer:

delete[] cuenta->titular;
delete cuenta;

Primero se libera el arreglo de caracteres y despues la cuenta.

Tambien se identifico un posible problema con:

strcpy(c->titular, nombre);

Debido a que el arreglo tiene un tamano fijo de 50 caracteres, un nombre mas largo podria causar problemas.

3. Problemas del diseno original

El sistema original utiliza un atributo llamado tipoCuenta para diferenciar las cuentas:

int tipoCuenta;

Despues utiliza condiciones para determinar que comportamiento debe realizar cada cuenta.

Esto significa que el programa debe comprobar constantemente que tipo de cuenta es antes de ejecutar su comportamiento.

Ademas, las operaciones se encuentran en funciones externas, como procesarRetiro, en lugar de que cada cuenta tenga su propio comportamiento.

4. Rediseno en Java

Para mejorar el diseno se creo una clase abstracta llamada CuentaBancaria.

Esta clase contiene los datos comunes de todas las cuentas:

Numero de cuenta.
Titular.
Saldo.

Los atributos se declararon como privados para aplicar encapsulamiento.

Tambien se creo el metodo depositar, ya que esta operacion funciona de forma similar para los diferentes tipos de cuenta.

Se declararon como metodos abstractos retirar y aplicarComisionMensual.

De esta forma, cada tipo de cuenta puede implementar estos metodos segun sus propias reglas.

5. Herencia y polimorfismo

A partir de CuentaBancaria se crearon dos clases:

CuentaAhorros.
CuentaCorriente.

CuentaAhorros no permite retirar una cantidad mayor al saldo disponible.

Por otro lado, CuentaCorriente permite utilizar un cupo de sobregiro.

Esto permite que cada clase tenga su propia implementacion del metodo retirar.

De esta forma se reemplazan los condicionales basados en tipoCuenta por herencia y polimorfismo.

6. Registro de auditoria

Tambien se creo la clase RegistroAuditoriaBancaria, la cual implementa AutoCloseable.

Esta clase permite trabajar con un archivo de auditoria y definir que ocurre cuando el recurso se cierra mediante el metodo close.

Al utilizar try-with-resources, Java llama automaticamente a close al terminar el bloque try.

Esto ayuda a garantizar que el archivo utilizado para la auditoria sea cerrado correctamente.

7. Conclusiones

La auditoria del codigo C++ permitio identificar problemas relacionados con el uso de memoria dinamica y la necesidad de liberar manualmente los recursos creados con new.

Tambien se identifico que el diseno original dependia de condicionales para diferenciar el comportamiento de cada tipo de cuenta.

El rediseno en Java permitio mejorar la organizacion del sistema mediante abstraccion, encapsulamiento, herencia y polimorfismo.

Finalmente, el uso de AutoCloseable y try-with-resources permite realizar un manejo mas seguro de los recursos utilizados para el registro de auditoria.