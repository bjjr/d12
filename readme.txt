===============================================================================
DATOS DE INICIO DE SESI�N
===============================================================================

En nuestra aplicaci�n existen cuatro tipos roles: administrador, productor,
usuario y cr�tico. Para acceder a funcionalidades limitadas a ciertos actores
por favor use los siguientes datos:

admin/admin

user1/user1
user2/user2
user3/user3 (Para probar la paginaci�n en listados)
user4/user4
user5/user5
user6/user6
user7/user7
user8/user8

producer1/producer1
producer2/producer2
producer3/producer3 (Para probar la paginaci�n en listados)

critic1/critic1
critic2/critic2
critic3/critic3 (Para probar la paginaci�n en listados)
critic4/critic4
critic5/critic5
critic6/critic6
critic7/critic7
critic8/critic8

===============================================================================
BASE DE DATOS
===============================================================================

En caso de querer inicializar la base de datos desde cero (sin ninguna tabla)
se incluye el fichero "mainquery.sql" para ello.

===============================================================================
EVITACI�N DE CROSS-SCRIPTING (XSS)
===============================================================================

En todos los campos de texto libre evitamos el Cross-Scripting usando la
anotaci�n @SafeHmtl y usando lo m�ximo posible la etiqueta "<jstl:out>".
Para que la anotaci�n funcionara fue necesario la inclusi�n de la librer�a
"jsoup" en nuestro proyecto (pom.xml).

===============================================================================
USO DE DATABINDER Y BINDINGRESULT EN TESTS FUNCIONALES
===============================================================================

En algunos de nuestros tests funcionales se intenta simular en la mayor medida
posible la interacci�n con un formulario. El objetivo es probar las funciones
"reconstruct" escritas en los servicios correspondientes. Esto es posible
empleando las clases DataBinder y BindingResult.

===============================================================================
MEJORA EN LA PRESENTACI�N DE CONTENIDO MULTIMEDIA
===============================================================================

Como complemento a nuestro A+ hemos decidido mejorar la experiencia multimedia,
por ello cuando se muestra la informaci�n de una serie o pel�cula, las im�genes
y v�deos de Youtube se presentan en un formato m�s interactivo. Nota: debido a
que el atributo "allowfullscreen" no es reconocido por JSP se muestra un
warning el proyecto, esto no se trata de un fallo en el c�digo.