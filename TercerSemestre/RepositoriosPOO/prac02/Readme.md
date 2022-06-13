# PRACTICA 2. MANEJO DE COLECCIONES Y FORMATEO DE VALORES NUMÉRICOS.

## COPIA DEL REPOSITORIO REMOTO EN SU COMPUTADORA LOCAL
Como primer paso, será necesario crear una copia local del repositorio remoto creado en Github al aceptar la tarea. Para ello, es necesario hacer los siguientes pasos:
1)	Entrar a la página cuyo URL les fue proporcionado al aceptar la tarea, en tal página dé click en el botón Code y copie el URL que aparece en el cuadro de texto de nombre **Clone with HTTPS** (comienza con https://)
2)	En una consola de Git Bash en Windows (o en una terminal en Linux o Mac), cree una carpeta donde quiera que se contengan sus prácticas del semestre (si es que aun no la creado) y colóquese en tal carpeta. La carpeta la puede crear desde el Git Bash o terminal Linux/Macusando el comando mkdir (o con el explorador de archivos de su sistema operativo) y en la consola de Git Bash o terminal de Linux/Mac se puede cambiar a la carpeta mencionada usando el comando cd
3)	Clone el repositorio privado dando el comando **git clone URL practica02**
 (donde **URL** es el URL que copió en el paso 1)
 Este comando creará dentro de la carpeta creada en el paso 2) una subcarpeta de nombre practica02 donde estará una copia local del repositorio remoto, el cual contiene un proyecto Gradle de Java en el cual solo debe modificar el código de la clase de acuerdo a las instrucciones de la siguiente sección.
4)  Colóquese en el directorio recién creado dando el comando **cd practica02** 


## MODIFICACIÓN DEL CÓDIGO PROPORCIONADO
Una vez que tenga el repositorio local y esté ubicado en la consola o terminal, su trabajo consiste en lo siguiente:

1. Usando el editor de su preferencia, completar el código de la clase ProcesadorDatos del archivo **ProcesadorDatos.java** ubicado en la carpeta app/src/main/java/poo2/prac02, el cual contiene un solo método de nombre **procesaListaNumeros**, esté metodo tiene la siguiente firma:

`public List<String> procesaListaNumeros(List<String> lineas,String codIdioma, String codPais)`

   a) El método recibe 3 argumentos, el primero contiene una lista de Strings que pudieran contener cualquier texto, el segundo representa un código de idioma de dos letras y el tercero un código de país de dos letras (que puede ser null para indicar que no se desea especificar un código de país.)
   b) El método debe recorrer la lista de Strings recibida como primer argumento y solo debe tomar en cuenta los strings que contengan un valor double (se puede ayudar de Double.parseDouble para determinar esto).
   c) Las valores double obtenidos de las líneas que se aceptarán de acuerdo al inciso anterior deben ser ordenadas del mayor valor al menor y debe regresarse una lista de Strings que representan esos valores formateados como valor monetario de acuerdo al código de idioma y país proporcionados como segundo y tercer argumentos.
   d) Por ejemplo si tenemos una lista de Strings de nombre datos con los siguientes Strings (cada línea representa uno de los Strings la lista):
   ```hola
   46.2
   17015
   34.246
   nuevo amigo mio
   juan gana 2891.23 a la semana
   2107.71
   un millon
   781.23
   ```
   y dos Strings codigoIdioma="es", codigoPais="MX", y hacemos una llamada como la siguiente:
   ```
   List<String> r = procesaListaNumeros(datos, codigoIdioma, codigoPais);
   ```
   Entonces, si imprimieramos los Strings en r deberíamos verlos de la siguiente manera:
   ```
   $17,015.00
   $2,107.71
   $781.23
   $46.20
   $34.25
   ``` 

2. Se proporciona un archivo (**ProcesadorDatosTest.java**, el cual **NO DEBEN DE MODIFICAR**) para hacer pruebas  de su código, ejecute **gradle test** para ver el resultado de tales pruebas y determinar la calificación que obtendría.

## NOTAS IMPORTANTES
1.	Es necesario estar guardando los cambios realizados en el punto 1 de manera atómica usando **git add** y **git commit**
2.	Una vez que esté seguro de que funciona correctamente (verificando con **gradle test**) suba su copia local al repositorio remoto usando el comando **git push**
3.	Cada vez que haga git push se realizaran automáticamente pruebas sobre su código para verificar si funciona correctamente. La calificación será mostrada al final de la prueba en un mensaje de salida que tiene el formato `ProcesadorDatosTest STANDARD_OUT Calificacion:XX/100` (**NO ES LA QUE DICE `POINTS XX/100`**). Verifique que en la página de su repositorio en la sección **Pull Requests**, se encuentra una subsección de nombre **Feedback**, ahí podrá encontrar los resultados de las pruebas en la sección **Check** (dando click en Github Classroom Workflow->Autograding->Run education/autograding@v1) y cualquier comentario general que el profesor tenga sobre su código en la pestaña **Conversation**. También es posible que haya comentarios sobre líneas de código específicas en la pestaña **Files Changed**. **NO BORRE ESTA SUBSECCIÓN DE FEEDBACK NI CIERRE EL PULL REQUEST**. Si tiene preguntas sobre los comentarios que le haya dejado el profesor, escríbalas en la pestaña **Conversation** usando el área de texto que se encuentra hasta abajo asegurándose de dar click en **Comment** una vez tecleada la pregunta.
