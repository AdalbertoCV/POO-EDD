# PRÁCTICAS 6 y 7. USO DE LA CLASE DriverManager Y DE LA INTERFACE DataSource DE JDBC PARA ACCEDER A MySQL.

## COPIA DEL REPOSITORIO REMOTO EN SU COMPUTADORA LOCAL
Como primer paso, será necesario crear una copia local del repositorio remoto creado en Github al aceptar la tarea. Para ello, es necesario hacer los siguientes pasos:
1. Entrar a la página cuyo URL les fue proporcionado al aceptar la tarea, en tal página dé click en el botón Code y copie el URL que aparece en el cuadro de texto de nombre **Clone with HTTPS** (comienza con *https://*)

2. Si usarás IntellijIDEA entonces haz los siguientes pasos:
   - Abre IntelliJ IDEA e indica que harás un clon local de tu repositorio:
   - Si no tienes ningún repositorio abierto selecciona la opción **Get From VCS** de la Ventana de Bienvenida, o si tienes un proyecto abierto, puedes entrar al menú **VCS**  y seleccionar la opción **Get From Version Control**
   - En el cuadro de diálogo que aparece:
     - Selecciona Git
     - Pega el URL que copiaste en el paso 1  en el cuadro de texto **URL**
     - Selecciona en Directory la carpeta donde lo colocarás, es importante que crees una nueva carpeta o se colocará (da click en el icono de carpeta , navega a donde lo quieres colocar y da click en el icono de New Folder para crear una nueva carpeta)
     - Da click en **Clone**
     - Si te pide usuario y clave de Github proporciona esos datos
     - Si te sale un cuadro de diálogo preguntándote si confías en el proyecto (Trust and Open Gradle Project) dá click en **Trust Project**
     - Después de unos segundos tendrás listo tu un clon de tu repositorio listo para trabajar en Intellij IDEA

3. Si NO usarás IntellijIDEA y harás todo desde la ventana de comandos usando un editor de texto tal como Sublime haz los siguientes pasos:

   - En una consola de Git Bash en Windows (o en una terminal en Linux o Mac), cree una carpeta donde quiera que se contengan sus prácticas del semestre (si es que aún no la has creado) y colócate en tal carpeta. La carpeta la puedes crear desde el Git Bash o terminal Linux/Mac usando el comando `mkdir` (o con el explorador de archivos de su sistema operativo) y en la consola de Git Bash o terminal de Linux/Mac te puedes cambiar a la carpeta mencionada usando el comando `cd`
   - Clone el repositorio privado dando el comando `git clone URL practica06_07`
   (donde **URL** es el URL que copió en el paso 1)\
   Este comando creará dentro de la carpeta creada en el paso 2) una subcarpeta de nombre **practica06_07** donde estará una copia local del repositorio remoto.\
   Los comandos posteriores de git tendrán que darse desde tal carpeta, por tanto, cámbiate a la carpeta usando `cd practica06_07`

## REQUERIMIENTOS

La lista de requerimientos que deben cumplirse en estas prácticas los puede encontrar en el archivo **PracticasLaboratorio06_07_20Sep2021.pdf**


## CALIFICACIÓN

1. La calificación de la práctica 6 vendrá de los dos constructores, y de los métodos **creaTablas** y **llenaTablaCarrera** de la clase **CreaTablasConDriverManager**. Cada constructor vale 10 puntos, el método **creaTablas** vale 20 puntos y el método **llenaTablaInstitucion** vale 60 puntos.

Dando un **total de 100 puntos para la práctica 6**. **SI SU PROGRAMA NO COMPILA O NO ESTABLECE SU NOMBRE Y MATRICULA EN LAS CLAUSULAS author DE LOS COMENTARIOS OBTENDRÁ CERO PUNTOS**

2. La calificación de la práctica 7 vendrá del  constructor, y de los métodos **llenaTablaSede** y **llenaTablaConcurso** de la clase **ColocaDatosUsandoDataSource**. El constructor vale 10 puntos, el método **llenaTablaSede** vale 50 puntos y el método **llenaTablaConcurso** vale 40 puntos.

Dando un **total de 100 puntos para la práctica 7**. **SI SU PROGRAMA NO COMPILA O NO ESTABLECE SU NOMBRE Y MATRICULA EN LAS CLAUSULAS author DE LOS COMENTARIOS OBTENDRÁ CERO PUNTOS**

Para ejecutar las pruebas de la práctica 6 selecciona **CreaTablasConDriverManagerTest** dentro de src/test/java, dale click con el botón derecho y selecciona **Run** (la opción tendrá un triángulo verde ), te mostrará el resultado de las pruebas y la calificación obtenida. Si lo estás haciendo de manera manual da el comando `gradle test --tests poo2.prac06.CreaTablasConDriverManagerTest`

Para ejecutar las pruebas de la práctica 7 selecciona **ColocaDatosUsandoDataSourceTest** dentro de src/test/java, dale click con el botón derecho y selecciona **Run** (la opción tendrá un triángulo verde ), te mostrará el resultado de las pruebas y la calificación obtenida. Si lo estás haciendo de manera manual da el comando `gradle test --tests poo2.prac07.ColocaDatosUsandoDataSourceTest`

**DE MANERA OPCIONAL**, si quieres ver los resultados que va creando tu código puedes ejecutar la clase **MainPractica06_07**, para ello seleccionala de src/main/java/poo2.prac06_07, dale click con el botón derecho y selecciona **Run** (la opción tendrá un triángulo verde). Si lo estás haciendo de manera manual da el comando `gradle run`

**RECUERDA QUE SOLO DEBES HACER CAMBIOS EN LAS CLASES CreaTablasConDriverManager y ColocaDatosUsandoDataSource, Y SOLO PONER TU MATRICULA EN EL STRING matricula de la CLASE MainPractica06_07. NO DEBES CAMBIAR NINGUNA OTRA PARTE DEL CÓDIGO (DE HECHO, DONDE TU DEBES ESCRIBIR CÓDIGO ESTA MARCADO CON UN COMENTARIO `//TODO` )** 

## NOTAS IMPORTANTES

1. Recuerda que el proceso que debes estar haciendo es:
   - Haz cambios en los archivos de acuerdo a lo indicado en las instrucciones del archivo PDF inciso por inciso 
   - **Ejecuta la prueba correspondiente al archivo para determinar si no hay errores** (trata de ir haciendo cambios incrementales, por ejemplo, completa uno de los métodos primero y prueba que compile antes de hacer cambios en los otros métodos)
   - Si la prueba correspondiente a lo que hiciste es exitosa, avisa a tu repositorio local que registre esos cambios dando los comandos `git add` y `git commit -m "MENSAJE"` donde MENSAJE es un texto que describe brevemente, pero de manera clara los cambios que realizaste desde el último commit (si es que lo estás haciendo desde la terminal de comandos) o usando las herramientas de Intellij para hacer los commits. 
 
2. Una vez completadas las prácticas, súbelas al repositorio remoto dando `git push` (o usando la opción de menú correspondiente de Intellij). De otra manera, corrige los errores (haciendo las compilaciones, git add y git commit correspondientes)

3. Cada vez que hagas `git push` se realizarán automáticamente pruebas sobre tu código para verificar si funciona correctamente. La calificación será mostrada al final de la prueba en dos mensajes de salida que tienen el formato `Calificacion Test de NNNNNNNNNNN STANDARD_OUT Calificacion:XX/100` (**NO ES LA QUE DICE `POINTS XX/100`**). Recuerda que en la página de tu repositorio en la sección **Pull Requests**, se encuentra una subsección de nombre **Feedback**, donde podrás encontrar los resultados de las pruebas en la pestaña denominada **Check** (expandiendo la parte que dice **Run education/autograding@v1**), y cualquier comentario general que el profesor tenga sobre tu código en la pestaña **Conversation**. 
