/* 
AUDITORIA:
En el codigo se pueden identificar dos lugares donde se asigna memoria
dinamica utilizando new.

El primero ocurre al crear la cuenta:

    CuentaLegacy* c = new CuentaLegacy();

Aqui se crea un objeto de tipo CuentaLegacy en el heap y c almacena la
direccion de memoria de ese objeto.

El segundo ocurre en:

    c->titular = new char[50];

En este caso se crea un arreglo dinamico de 50 caracteres. El atributo
titular no almacena directamente el nombre de la persona, sino que almacena
la direccinn de memoria donde se encuentra ese arreglo.

El problema es que la memoria creada con new debe ser liberada manualmente.
En el codigo original no se observa un momento donde se libere la memoria
de titular ni la memoria de la cuenta cuando estas ya no se necesitan.

Si una funcion termina y el unico puntero que permitia acceder a un espacio
reservado en el heap desaparece, la memoria reservada puede quedar sin una
referencia accesible para el programa. Esa memoria continua ocupando espacio
hasta que termina el proceso, produciendo una fuga de memoria.

Para liberar correctamente la memoria de una cuenta creada se debe hacer:

    delete[] cuenta->titular;
    delete cuenta;

Primero se debe liberar el arreglo de caracteres al que apunta titular y
despues se libera la estructura CuentaLegacy. Esto debe hacerse cuando ya
se haya terminado de utilizar la cuenta, por ejemplo al final de su uso en
el programa, y no dentro de crearCuenta antes del return, ya que despues
de ejecutar return la funcion termina y las instrucciones posteriores no
se ejecutan.

Tambien existe un posible problema con:

    strcpy(c->titular, nombre);

El arreglo reservado para titular tiene un tamaño fijo de 50 caracteres.
Si nombre supera la capacidad disponible, strcpy no realiza una comprobación
del tamaño y puede producirse un desbordamiento de bufer.

Desde el punto de vista de la arquitectura, el sistema utiliza un struct
con punteros crudos y funciones externas como procesarRetiro. Ademas, el
tipo de comportamiento de la cuenta se determina mediante tipoCuenta y
condicionales if y else if.

Por ejemplo, el programa debe preguntar si la cuenta es de tipo 1 o tipo 2
para decidir que operacion realizar. Esto hace que el diseño sea mas
procedimental y que, al agregar nuevos tipos de cuentas, posiblemente se
deban agregar mas condicionales.

Una posible mejora mediante Programacion Orientada a Objetos seria utilizar
una clase base CuentaBancaria y clases derivadas como CuentaAhorros y
CuentaCorriente. Mediante herencia, abstraccion y especialmente polimorfismo,
cada tipo de cuenta podria implementar su propia logica de retiro sin
necesitar depender de tantos condicionales basados en tipoCuenta.

En C++ moderno tambien se podrian mejorar algunos problemas de gestion de
memoria utilizando string en lugar de char*, evitando la necesidad de
reservar manualmente un arreglo de 50 caracteres y de utilizar strcpy.
Ademas, para objetos con propiedad unica se podrian utilizar punteros
inteligentes como unique_ptr para reducir la necesidad de utilizar delete
manualmente.
*/