# PROGRAMA 3. IMPLEMENTACIÓN DE UN CLIENTE PARA LOS SERVICIOS REST DE  institucion y persona.

## PREREQUISITOS PARA ESTE PROGRAMA
1. Servicios REST desarrollados para el programa 2 **corriendo** en Glassfish
2. Android SDK instalado (proceso realizado en la práctica 10) 

## COPIA DEL REPOSITORIO REMOTO EN SU COMPUTADORA LOCAL

Como primer paso, será necesario crear una copia local del repositorio remoto creado en Github al aceptar la tarea. Para ello, es necesario hacer los siguientes pasos:
1. Entrar a la página cuyo URL les fue proporcionado al aceptar la tarea, en tal página dé click en el botón Code y copie el URL que aparece en el cuadro de texto de nombre **Clone with HTTPS** (comienza con *https://*)
2. Abre IntelliJ IDEA e indica que harás un clon local de tu repositorio:
   - Si no tienes ningún repositorio abierto selecciona la opción **Get From VCS** de la Ventana de Bienvenida, o si tienes un proyecto abierto, puedes entrar al menú **New->Project from Version Control...->**
   - En el cuadro de diálogo que aparece:
     - Selecciona Git
     - Pega el URL que copiaste en el paso 1  en el cuadro de texto **URL**
     - Selecciona en Directory la carpeta donde lo colocarás
     - Da click en **Clone**
     - Si te pide usuario y clave de Github proporciona esos datos
     - Después de unos segundos tendrás listo tu un clon de tu repositorio listo para trabajar en Intellij IDEA

## CONFIGURACIÓN DEL PROYECTO

1. Modifique en el archivo strings.xml ubicado dentro de res/values, el string nombre_autor, para que contenga su nombre completo
2. Una vez que se hayan instalado todas las dependencias, ejecute el programa, si le marca un error de que no puede encontrar el Android SDK, será necesario crear un archivo de nombre local.properties en la carpeta raíz de su proyecto (este archivo NO DEBE SUBIRSE AL REPOSITORIO REMOTO, NO LO INCLUYA EN LOS COMMIT) que contenga una línea como la siguiente (se muestre ejemplo para Windows, en Linux en vez de cada  \\\ se usa una /):

*sdk.dir=C\:\\\Users\\\nomusuario\\\AppData\\\Local\\\Android\\\Sdk*

Reemplace la ruta puesta en el ejemplo por la ruta donde usted tenga instalado el SDK de Android


## MODIFICACIÓN DEL CÓDIGO PROPORCIONADO

El proyecto ya contiene una aplicación funcional que muestra una lista de instituciones creadas manualmente, durante las sesiones del 17 al 19 de noviembre se mostrará como crear el código para bajar las instituciones del servicio REST correspondiente y como solicitar a tal servicio que agregue, actualice o elimine una institucion.

Su trabajo consistirá en realizar un trabajo similar para el servicio de Personas, para lo cual el proyecto ya tiene algunos de los archivos XML necesarios, aunque deberá realizar lo siguiente:
1. agregar todos los strings necesarios a strings.xml
2. completar persona_detalle.xml
3. generar la versión landscape de activity_detalle_persona.xml
4. completar los métodos que tienen que ver con Persona en DaoConcursosImpl
5. Escribir el código de las clases ListPersonaActivity y DetallePersonaActivity para que realicen el trabajo necesario (siguiendo la estructura de ListInstitucionActivity y DetalleInstitucionActivity)


## CALIFICACIÓN

Para este programa no habrá una prueba automatizada en Github y la realizará el instructor de manera manual. La fecha y hora límite para hacer el push final es a las **10:00 AM del viernes 3 de diciembre del 2021**, para alcanzar calificación en ordinario.


## NOTAS IMPORTANTES
1. Cuando vaya a probar su programa, deberá tener corriendo los servicios REST del Programa 2, y deberá accederlos usando la dirección IP de su computadora, la cual pondrá en lugar de localhost en el URL Base de los servicios. El URL Base deberá ser colocado en el string url_base del archivo strings.xml

   Para obtener su dirección IP puede usar el comando ipconfig en Windows o ifconfig en Linux/Mac. Una vez obtenida su dirección, en lugar entonces de poner el URL base como:

   **http://localhost:8080/RESTConcursos-361772/servicios/**

   Se pondría como:

   **http://192.168.4.117:8080/RESTConcursos-361772/servicios/**
   
   Si su dirección IP fuera 192.168.4.117
   
   Note también que la diagonal al final del URL base es **NECESARIA** dada la forma e que se usará el URL para contactar a los servicios REST.

2. Si la aplicación la corre en un dispositivo físico, su computadora y el dispositivo físico deben estar conectados en la misma red para que funcione la comunicación (o bien, hacer algo similar a lo que debió hacer en el programa 2 para que las pruebas de Github pudiera acceder a su computadora, lo cual implicaría tener corriendo ngrok y haber configurado su proyecto del Programa 2 para usar las direcciones asignadas por ngrok)


3. Cada vez que completes cada parte de tu proyecto (agregar campos, programar un evento, etc.) ejecuta tu programa para determinar si todo va saliendo bien

4. Una vez vez que verifiques que esa parte del proyecto que realizaste funciona correctamente haz un Commit : 
   - Para hacer commit, selecciona primero el proyecto, después entra al menú VCS y selecciona la opción Commit...
   - Te aparecerá una lista de archivos con los cambios detectados, verifica que incluye todos los archivos que deberían estar
   - Teclea un mensaje que describa los cambios realizados de manera clara y concisa (debe ser un mensaje que permita a otras personas darse cuenta de lo realizado)
   - Dé click en el ícono del engrane (Show Commit Options), escriba su nombre en el cuadro Author, deseleccione Perform Code Analysis y Check TODO (Esto es necesario solo en el primer commit que hagan)
   - Dé click en Commit

5. Después de haber hecho todos los commits que completan tu programa, súbelo al repositorio remoto haciendo lo siguiente:
   - Entre al menú VCS y seleccione la opción Git->Push...
   - Dé click en el botón Push en el cuadro de diálogo que aparece

6. Recuerda que en la página de tu repositorio en la sección **Pull Requests**, se encuentra una subsección de nombre **Feedback**, donde podrás encontrar  cualquier comentario general que el profesor tenga sobre tu código en la pestaña **Conversation**. 
