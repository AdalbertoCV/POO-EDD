# Programación Orientada a Objetos y Estructuras de Datos — Java

Trabajo de dos semestres de la Licenciatura en Ingeniería de Software (Universidad Autónoma de Zacatecas): **~340 clases Java** que van desde la implementación desde cero de estructuras de datos clásicas hasta aplicaciones cliente-servidor con JDBC, servicios REST y Android.

Lo relevante aquí es que **nada se apoya en `java.util.Collections`**: listas, pilas, colas, árboles, grafos y montículos están implementados a mano, en versión estática y dinámica, con su propia suite de pruebas y documentación Javadoc.

---

## Cuarto semestre — Estructuras de Datos

Biblioteca propia de estructuras de datos y algoritmos, organizada por paquetes.

### `estructuraslineales`

Cada estructura en sus variantes estática (arreglo) y dinámica (nodos enlazados):

| Estructura | Implementaciones |
|---|---|
| **Listas** | `ListaEstatica`, `ListaDinamica`, `ListaEstaticaOrdenada`, `ListaDinamicaOrdenada`, `ListaDinamicaDoble` (doblemente enlazada), `ListaDinamicaClave` (acceso por clave), `ListaEstaticaNumerica`, `VectorLista` |
| **Pilas** | `PilaEstatica`, `PilaDinamica` |
| **Colas** | `ColaEstatica`, `ColaDinamica`, `ColaEstaticaDePrioridad` |
| **Almacenamiento** | `ListaAlmacenamiento`, `LoteAlmacenamiento` — persistencia de registros |

### `estructurasnolineales`

| Estructura | Descripción |
|---|---|
| `ArbolBinario` / `ArbolBinarioBusqueda` | Árboles binarios y de búsqueda con recorridos en pre/in/post orden |
| `ArbolExpAritm` | **Árbol de expresiones aritméticas**: tokeniza una expresión postfija y construye el árbol usando una pila de operadores |
| `ArbolExpParentesis` | Construcción de árbol a partir de notación con paréntesis |
| `ArbolIndices` | Árbol de índices para acceso a registros |
| `GrafoEstatico` / `GrafoDinamico` | Grafos por matriz de adyacencia y por listas |
| `Monticulo` | Heap binario |
| `Matriz2`, `Matriz3`, `Matriz2Numerica` | Matrices bidimensionales y tridimensionales |

### Paquetes de apoyo

- **`recursion`** — algoritmos recursivos clásicos (`metodosRecursivos`).
- **`herramientas`** — utilidades de `Estadistica`, `matematicas`, `ordenamiento` (algoritmos de ordenamiento) y `texto`.
- **`registros`** — modelos de dominio para los ejercicios: `Audios`, `Libros`, `Imagenes`, `Computadoras`, `Campesinos`, `ControlPintores`, `competenciasciclismo`, `escuelas`.
- **`entradasalida`** — `EntradaPorDefecto` / `SalidaPorDefecto`, capa de E/S desacoplada de la lógica.
- **`enumerados`** — tipos enumerados (`TipoDatos`, `TipoOrden`, `TipoTabla`, `TipoColumna`, `TipoLogaritmo`, `TipoRenglon`).
- **`Menus`** — `MenuProcesos` e `InterfazMOM`, capa de interacción por consola.
- **`pruebas`** — clases de prueba para cada estructura.
- **`Examen1` / `Examen2`** — evaluaciones: balanceo de expresiones, regresión lineal, distribución normal y lista dinámica paralela.

> El repositorio incluye **Javadoc generado** en `CuartoSemestre/docs/`, navegable como sitio HTML.

---

## Tercer semestre — POO 2 y persistencia

Prácticas de laboratorio construidas con **Gradle** alrededor de un sistema de *control de concursos* académicos.

| Proyecto | Tema |
|---|---|
| `prac02` | Fundamentos de POO en Java con Gradle y entradas de prueba |
| `practica05` | Diseño y creación del esquema relacional (`tablas.sql`, `datos.sql`) |
| `practica06_07` | **JDBC**: conexión con `DriverManager` y `DataSource`, carga desde TSV y XML, logging con log4j |
| `practica08` / `prog1_ad2021` | **Capa DAO completa**: `DaoConcurso`, `DaoEquipo`, `DaoSede`, `DaoPersona`, `DaoInstitucion`, `DaoMunicipio`, `DaoEntidad`, `DaoDatosEstudiante` — CRUD sobre MySQL con serialización XML de entrada y salida |
| `practica09` / `prog2_ad2021` | **Servicios REST** sobre el mismo dominio, desplegados en GlassFish (`glassfish-resources.xml`), con pruebas de integración |
| `programa3` | Aplicación **Android** cliente del sistema de control de concursos |
| `PrimeraAppAndroid…` | Primera aplicación Android: actividades y navegación |
| `Programa2ACV`, `programa02`, `practica-03-lab-poo2` | Ejercicios intermedios de POO |

---

## Estructura del repositorio

```
.
├── CuartoSemestre/                 # Estructuras de Datos
│   ├── src/
│   │   ├── estructuraslineales/    #   listas, pilas, colas
│   │   ├── estructurasnolineales/  #   árboles, grafos, matrices, montículo
│   │   ├── recursion/  operaciones/  herramientas/
│   │   ├── registros/  entradasalida/  enumerados/  Menus/
│   │   ├── pruebas/                #   pruebas de cada estructura
│   │   └── Examen1/  Examen2/
│   └── docs/                       #   Javadoc generado (HTML)
└── TercerSemestre/RepositoriosPOO/ # POO 2: JDBC, DAO, REST, Android
    ├── practica05/ … practica09/
    ├── prog1_ad2021/  prog2_ad2021/
    └── programa3/  PrimeraAppAndroid…/   # Android
```

---

## Cómo ejecutarlo

### Estructuras de datos (cuarto semestre)

Proyecto Java plano, sin gestor de dependencias:

```bash
cd CuartoSemestre
javac -d out $(find src -name "*.java")
java -cp out Menus.MenuProcesos
```

También puede abrirse directamente en IntelliJ IDEA (incluye el `.iml` del proyecto).

Para consultar la documentación, abre `CuartoSemestre/docs/index.html` en el navegador.

### Prácticas de tercer semestre

Cada práctica es un proyecto Gradle independiente:

```bash
cd TercerSemestre/RepositoriosPOO/practica08
./gradlew build
./gradlew run
```

Las prácticas con persistencia requieren **MySQL** con el esquema `controlconcursos`; los scripts de creación y carga (`creaconcursos.sql`, `llenaconcursos.sql`) están en la carpeta de cada práctica. La configuración de conexión vive en `ConfigAccesoBaseDatos.java`.

Los proyectos Android se abren con **Android Studio**.

---

## Stack

`Java` · `Gradle` · `JDBC` · `MySQL` · `XML` · `log4j` · `JUnit` · `Android SDK` · `GlassFish` · `Javadoc`

---

## Autor

**Adalberto Cerrillo Vázquez** — Ingeniería de Software, Universidad Autónoma de Zacatecas.
