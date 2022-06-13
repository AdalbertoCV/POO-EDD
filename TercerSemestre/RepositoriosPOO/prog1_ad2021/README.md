# PRACTICA 08 Y PROGRAMA 1. CLASES DE ENTIDAD Y DAO PARA LA BASE DE DATOS controlconcursos.

Este proyecto será usado tanto para el Programa 1 como para la Práctica 8. En la práctica 8, bajo la guía del maestro se implementará el Dao para las tablas municipio e institucion, las cuales son un subconjunto de lo que se hará para el Programa 1.

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
   - Clone el repositorio privado dando el comando `git clone URL programa_01`
   (donde **URL** es el URL que copió en el paso 1)\
   Este comando creará dentro de la carpeta creada en el paso 2) una subcarpeta de nombre **programa_01** donde estará una copia local del repositorio remoto.\
   Los comandos posteriores de git tendrán que darse desde tal carpeta, por tanto, cámbiate a la carpeta usando `cd programa_01`

## EJECUCIÓN DE SCRIPTS PARA CREAR LA BASE DE DATOS NECESARIA Y COLOCARLE DATOS

Deberá ejecutar los scripts **creaconcursos.sql** y **llenaconcursos.sql** que se encuentran en la carpeta raíz del proyecto que acaba de clonar. La ejecución de estos scripts debe ser realizado  de la siguiente manera desde la terminal de comandos, **habiéndose colocado primero en la carpeta raíz del proyecto**:
```
mysql -u root -p  --default-character-set=utf8 < creaconcursos.sql  
mysql -u root -p  --default-character-set=utf8 < llenaconcursos.sql
```

Para cada uno de estos se le pedirá la clave de root, tecléela y presione Enter. El segundo comando puede tomar algunos segundos pues coloca varios registros en las tablas.

## MODIFICACIÓN DEL CÓDIGO PROPORCIONADO CON RESPECTO A LA PRÁCTICA 08

Una vez que tengas el repositorio local, el trabajo consiste en completar las clases de entidad para la base de datos **controlconcursos** asociadas con las tablas **municipio** e **institucion**,  implementar la interface **Dao<T>** para hacer las operaciones CRUD en tales tablas y los métodos asociados con municipio e institucion de la interface  **DAOConcursos**, la implementación de la interface **Dao<T>** se hará en las clases **DaoMunicipio** y **DaoInstitucion** y la implementación de la interface **DAOConcursos** en la clase **DaoConcursosImpl**, todas a colocarse en el paquete **poo2.progs.basedatos** .

En el paquete **poo2.progs.main** se te recomienda (*aunque no es obligatorio ni es algo que se vaya a calificar*) crear clases para que pruebes que tus clases **DaoMunicipio**, **DaoInstitucion** y **DaoConcursosImpl** funcionan correctamente.


## CALIFICACIÓN PARA LA PRÁCTICA 08
Cada uno de los métodos que debes implementar de las interfaces **Dao<T>** y **DAOConcursos**, además de las clases de entidad de **municipio** e **institucion** aportan parte de la calificación:

1. La clases de entidad aportan  **6 puntos**
2. La implementación de Dao<Municipio> en la clase DaoMunicipio aporta **14 puntos**
3. La implementación de Dao<Institucion> en la clase DaoInstitucion aporta **50 puntos**
4. La implementación del método obtenMunicipios() en DaoConcursosImpl aporta **20 puntos**
5. La implementación de los métodos que tienen que ver con la clase entidad Institucion en DaoConcursosImpl aportan **10 puntos**

Para ejecutar las pruebas de tu programa selecciona el archivo correspondiente a lo que quieres probar de entre  los archivos del paquete **poo2.prac08.main** en la sección **test** del proyecto, dale click con el botón derecho y selecciona **Run** (la opción tendrá un triángulo verde ), te mostrará el resultado de las pruebas y la calificación obtenida

**RECUERDA QUE NO DEBES TOCAR EL CÓDIGO DE LAS PRUEBAS, A MENOS QUE EL INSTRUCTOR TE LO INDIQUE** 

## NOTAS IMPORTANTES PARA LA PRACTICA 08
1. Tanto la clase que implemente la interface DAO<T> como la clase que implemente la interface DAOConcursos deben tener 3 constructores:
   - uno que no reciba argumentos, el cual creará un objeto Connection con los valores por default (servidor en localhost, usando la base de datos controlconcursos y conectandose con el usuario **IngSW** con clave **UAZsw2021**) ayudándose de DriverManager
   - otro que reciba como argumentos la ubicacion del servidor, nombre de la base de datos a usar, usuario y clave con el que se conectará (todos String) y a partir de ellos crear la conexión ayudándose de DriverManager
   - otro que reciba como argumento el objeto Connection a usar, en cuyo caso simplemente lo asigna a su atributo Connection, generando una excepción en caso de que el objeto recibido sea null

2. En cada uno de los constructores de las interfaces DAO<T> y DAOConcursos que implementarás, debes capturar las posibles excepciones que se den al conectarse a MySQL (si es que aplica) y en su lugar emitirás una excepción de tipo DaoException con los mismos mensajes y causas que la excepción original, esto para que en caso de que quien use el constructor tenga la opción de determinar las causas del error.

3. La clase que implemente DAOConcursos debe apoyarse en las clases que implementen DAO<T> para realizar su trabajo, por lo cual deberá contener objetos de tales clases los cuales serán inicializados al construirse el objeto en un método de nombre inicializaDaos.

4. Al agregar o actualizar es necesario asegurarse que los campos tienen un rango válido de valores, en caso contrario debería devolver false el método correspondiente. En el caso de los id de municipio o estado, verificar que existen en la tabla municipio y entidad respectivamente (en las pruebas del repositorio remoto solo se pueden usar las entidades 1 a 5 y la 32 con sus respectivos municipios)

5. Al eliminar una institucion es necesario verificar primero que no haya registros asociados con la institucion a eliminar, es decir, primero hay que ver que ningun registro de persona, equipo o sede esté asociado a la institucion a eliminar, si existe algun registro asociado, no se deberá eliminar la institucion y se regresará false en tal caso.

6. La clases de entidad de institucion Y municipio deberán cumplir lo siguiente:
   - deberán tener 3 constructores: uno vacío, uno que reciba como argumento solo la llave primaria y otro que reciba como argumentos los valores obligatorios (marcados como NOT NULL en la creación de la tabla), los cuales deberán ser recibidos en el orden en que están declarados en la tabla
   - deben implementar la interface Serializable
   - para la clase Municipio el metodo toString deberá regresar el nombre del municipio, y los métodos equals y hashCode solo deben considerar el atributo idMunicipio
   - para la clase Institucion el metodo toString deberá regresar el valor del nombre de la institucion, y los métodos equals y hashCode solo deben considerar el atributo idInstitucion

7. Cada vez que completes un método ejecuta las pruebas para verificar que las pruebas relacionadas con el método completado son exitosas

8. Una vez vez que completes un método y verifiques que pasa las pruebas haz un commit usando el comando que corresponda de acuerdo a la forma en que estés trabajando en el proyecto (desde Intellij IDEA o desde la ventana de comandos). Asegúrate de incluir en el commit el archivo involucrado (usando git add en la terminal de comandos o seleccionandolo en IntelliJ) y de teclear un mensaje que describa los cambios realizados de manera clara y concisa (debe ser un mensaje que permita a otras personas darse cuenta de lo realizado)

9. Después de haber hecho todos los commits que completan tu programa, súbelo al repositorio remoto haciendo un git push

10. Cada vez que subas tu proyecto al repositorio privado con un push, se aplicarán automáticamente las pruebas sobre tu código para verificar si funciona correctamente. Recuerda que en la página de tu repositorio en la sección **Pull Requests**, se encuentra una subsección de nombre **Feedback**, donde podrás encontrar los resultados de las pruebas en la pestaña denominada **Check** (expandiendo la parte que dice **Run education/autograding@v1**), y cualquier comentario general que el profesor tenga sobre tu código en la pestaña **Conversation**. 

11. Se tendrá hasta las 23:59 horas del día en en cual se haya concluido por parte del instructor la práctica 08, para hacer el último push que se tomará en cuenta para evaluar la práctica.

## INSTRUCCIONES PARA INICIALIZAR TU PROYECTO DEL PROGRAMA 1

NOTA: **Es importante hacer estos pasos en el orden que se especifica**

1. Acepta la tarea del Github para el programa 1, cuya liga está en el Moodle de la materia. Al aceptar la tarea, se les creará un repositorio remoto, y les proporcionará el link a tal repositorio (al recargar la página después de unos segundos). Entre a tal  URL, en tal página dé click en el botón Code y copie el URL que aparece en el cuadro de texto de nombre **Clone with HTTPS** (comienza con *https://*). **NOTE QUE NO CLONARÁ ESTE REPOSITORIO**

2. Copie el proyecto IntellijIDEA de la Práctica 08 haciendo lo siguiente:
    - Desde el navegador de archivos de su sistema operativo:
      - Copie la carpeta que contiene su proyecto de la Práctica 8 (usando copiar y pegar)
      - Renombre la carpeta que acaba de crear (la copia) al nombre que desee, por ejemplo, prog1_ad2021
      - Elimine de la carpeta creada las siguientes subcarpetas: .git, .github, .gradle, y .idea  (note que todas comienzan con un punto y probablemente tenga que activar que se vean los archivos ocultos para que pueda ver la carpeta .git)

3. Abra el proyecto nuevo:
    - Si le sale un cuadro de diálogo preguntándole si confía en el proyecto (Trust and Open Gradle Project) dé click en **Trust Project**

4. Habilite la integración con Git, para combinar lo que tiene el repositorio remoto con el código que copió de la Práctica 8:
    - Dé click en la opción **Enable Version Control Integration...** del menú **VCS**, seleccione **Git** y dé click en **OK**
    - Entre al menú **VCS -> Manage Remotes...**, de click en el + para agregar el URL del repositorio remoto (que es el proporcionado al aceptar la tarea en el botón Code), dar click en **Ok**
    - Dé click en el menú **VCS -> Fetch**
    - Dé click en el menú **VCS -> Pull**, seleccione el branch master y dé click en el botón **Pull**
    - Haga un primer Commit con el mensaje "Inicialización de Código para Programa 1" 
    - Entre al menu **VCS -> Push...** y en el cuadro de diálogo que aparece dé click en el botón Push

## REQUERIMIENTOS DEL PROGRAMA 1

La lista de requerimientos completa que deben cumplirse para el programa 01 los puede encontrar en el archivo **POO2_Progr1AgoDic2021.pdf**


## CALIFICACIÓN PARA EL PROGRAMA 1

La calificación para esta se calculará de manera automatizada al hacer push, se te recomienda ampliamente seguir el proceso que se detalla en la siguiente sección. En el documento PDF están los puntos asignados a cada componente del programa 1.

## NOTAS IMPORTANTES PARA EL PROGRAMA 1.

1. El proceso que se te sugiere seguir para completar el Programa 1 es el siguiente:
   - Selecciona una clase de entidad a realizar y completala (de acuerdo a las instrucciones del PDF) haciendo commit de tal clase de entidad
   - Desempaca del archivo ZIP que se encuentra en el paquete progs.main de la sección test del proyecto el test del DAO cuya clase estas completando (**solo desempaca el de la clase que estas completando o tu programa no compilara**). Haz un commit para agregar este archivo que acabas de descomprimir.
   - Crea la clase DAO correspondiente a esa clase de entidad haciendo que implemente la interface Dao para la clase de entidad, colocandole los constructores. Haz commit.
   - Cada que completes el código de un método de la clase DAO que estas implementando ejecuta las pruebas relacionadas a tal DAO para verificar que pasa las pruebas (relacionadas al método que estas completando) y una vez que está bien, haz commit
   - NOTA: Asegúrate de incluir en los commits el archivo involucrado (usando git add en la terminal de comandos o seleccionandolo en IntelliJ) y de teclear un mensaje que describa los cambios realizados de manera clara y concisa (debe ser un mensaje que permita a otras personas darse cuenta de lo realizado)
   - Una vez que tengas todos los DAOs desempaca del archivo ZIP que se encuentra en el paquete progs.main de la sección test, el archivo de prueba de las Clases de Entidad, haz un commit para agregarlo y ejecutalo para verificar que todas las clases de Entidad cumplen con las instrucciones, corrige lo que sea necesario haciendo commits de tus correcciones, si es que las hay
   - Desempaca del archivo ZIP que se encuentra en el paquete progs.main de la sección test, el archivo de prueba de las DaoConcursosImpl, y ve completando uno a uno los métodos que te faltan implementar, por cada uno que implementes ejecuta la prueba para ver si la pasa y haz commit.

2. Sube tus cambios locales al repositorio remoto de manera periódica, cada hora por ejemplo, mientras estás trabajando, dando `git push`. 

3. Recuerda que cada vez que hagas `git push` se realizarán automáticamente pruebas sobre tu código para verificar si funciona correctamente. Recuerda que en la página de tu repositorio en la sección **Pull Requests**, se encuentra una subsección de nombre **Feedback**, donde podrás encontrar los resultados de las pruebas en la pestaña denominada **Check** (expandiendo la parte que dice **Run education/autograding@v1**), y cualquier comentario general que el profesor tenga sobre tu código en la pestaña **Conversation**. 

5. Se tendrá hasta las 23:59 horas del 10 de octubre del 2021 para hacer el último push.


