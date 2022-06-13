# PRÁCTICA 5. CREACIÓN Y MANIPULACIÓN DE BASES DE DATOS EN MYSQL USANDO EL LENGUAJE SQL.

## COPIA DEL REPOSITORIO REMOTO EN SU COMPUTADORA LOCAL
Como primer paso, será necesario crear una copia local del repositorio remoto creado en Github al aceptar la tarea. Para ello, es necesario hacer los siguientes pasos:
1)	Entrar a la página cuyo URL les fue proporcionado al aceptar la tarea, en tal página dé click en el botón Code y copie el URL que aparece en el cuadro de texto de nombre **Clone with HTTPS** (comienza con *https://*)
2)	En una consola de Git Bash en Windows (o en una terminal en Linux o Mac), cree una carpeta donde quiera que se contengan sus prácticas del semestre (si es que aún no la has creado) y colócate en tal carpeta. La carpeta la puedes crear desde el Git Bash o terminal Linux/Mac usando el comando `mkdir` (o con el explorador de archivos de su sistema operativo) y en la consola de Git Bash o terminal de Linux/Mac te puedes cambiar a la carpeta mencionada usando el comando `cd`
3)	Clone el repositorio privado dando el comando `git clone URL practica05`
 (donde **URL** es el URL que copió en el paso 1)\
 Este comando creará dentro de la carpeta creada en el paso 2) una subcarpeta de nombre **practica05** donde estará una copia local del repositorio remoto.\
 Los comandos posteriores de git tendrán que darse desde tal carpeta, por tanto, cámbiate a la carpeta usando `cd practica05`


## REQUERIMIENTOS

La lista de requerimientos que deben cumplirse en esta práctica los puede encontrar en el archivo **PracticasLaboratorio05_13Sep2021.pdf**


## CALIFICACIÓN
La calificación para esta práctica no les será emitida de manera inmediata al subir su solución, sin embargo se realizarán pruebas para verificar que se pueden ejecutar los scripts SQL tablas.sql y datos.sql sin problemas en MySQL.

**SI SUS SCRIPTS NO PUEDEN SER EJECUTADOS SIN ERRORES OBTENDRÁN CERO PUNTOS**


## NOTAS IMPORTANTES


1. Recuerda que de acuerdo a lo visto en las Prácticas Anteriores el proceso que debes estar haciendo es:
   - Haz cambios  en los archivos **tablas.sql** y **datos.sql**
   - **Verifica que funcionan correctamente ejecutándolos en la consola de MySQL usando SOURCE** (trata de ir haciendo cambios incrementales, por ejemplo, agrega algunas de las sentencias SQL que debe tener uno de los archivos, y prueba que funcione antes de agregar mas sentencias SQL)
   - Para avisar a tu repositorio local que registre los cambios emite los comandos `git add NNNNN.sql` (donde NNNNN es el nombre del archivo que modificaste) y `git commit -m "MENSAJE"` donde MENSAJE es un texto que describe brevemente, pero de manera clara los cambios que realizaste desde el último commit.

2. Después de haber hecho todos los commits que completan tus scripts, súbelos al repositorio remoto dando `git push`

3. Cada vez que hagas `git push` se realizarán automáticamente pruebas sobre tu código para verificar si pueden ser ejecutados sin errores en MySQL. Para esta práctica en particular no se te proporcionará una calificación de manera inmediata pues debe ser revisada con mayor detalle. Recuerda que en la página de tu repositorio en la sección **Pull Requests**, se encuentra una subsección de nombre **Feedback**, donde podrás encontrar los resultados de las pruebas en la pestaña denominada **Check** (expandiendo la parte que dice **Run education/autograding@v1**), y cualquier comentario general que el profesor tenga sobre tu código en la pestaña **Conversation**. 
