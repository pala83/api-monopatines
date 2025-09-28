# JPA - Relaciones entre entidades
implementar los mapeos de las entidades `Turno`, `Direccion`, `Persona` y `Socio` a tablas en (Derby o MySQL) asi como las relaciones entre ellas.

``` mermaid
erDiagram
    PERSONA {
        INT id PK
        INT anios
        VARCHAR nombre
        INT domicilio_id FK
    }

    SOCIO {
        VARCHAR tipo FK
        INT persona_id
    }

    DIRECCION {
        INT id PK
        VARCHAR calle
        VARCHAR ciudad
    }

    TURNO {
        INT id PK
        DATETIME fecha
    }

    TURNO_PERSONA {
        INT turnos_id FK
        INT jugadores_id FK
    }

    hIBERNATE_SEQUENCE {
        BIGINT next_val
    }

    %% Relaciones
    DIRECCION ||--o{ PERSONA : ""
    PERSONA ||--|| SOCIO : ""
    PERSONA ||--O{ TURNO_PERSONA : ""
    TURNO ||--O{ TURNO_PERSONA : ""
```


    PERSONA ||--|| DIRECCION : ""
    SOCIO ||--|| PERSONA : ""
    PERSONA ||--o{ TURNO_PERSONA : ""
    TURNO ||--o{ TURNO_PERSONA : ""

# 2.d)

### Qué hace `FetchType.LAZY` y `FetchType.EAGER`

- `LAZY`: no carga la relación hasta que la accedés. Se crea un proxy y recién consulta la BD cuando invocás el getter.
- `EAGER`: carga la relación inmediatamente junto con la entidad principal (usando join o consultas extra).

### Diferencias de comportamiento en la app

- Si cambiás a **LAZY** una relación que luego accedés fuera del EntityManager/Transacción, obtendrás LazyInitializationException.
    - Ejemplo directo: en `Persona`, `direccion` (ManyToOne) por defecto es **EAGER**. Vos imprimís `persona.getDireccion().getCiudad()` después de cerrar el `EntityManager` en repos (p.ej. `PersonaRepositoryImpl.buscarTodos`). Si cambiás a **LAZY** sin ajustar la consulta, va a romper.
    - Con **EAGER** en colecciones (OneToMany/ManyToMany) podés terminar cargando muchísima data sin necesitarla, e incluso multiplicando filas (cartesianas) en joins; más memoria/tiempo, peor rendimiento.

### Ventajas / desventajas

- **LAZY**
    - Carga bajo demanda, mejor rendimiento por defecto.
    - Evita payloads enormes y problemas al serializar.
    - Requiere manejar el contexto: usar DTOs o fetch join para inicializar antes de cerrar el EntityManager.
    - Puede producir N+1 queries si accedés repetidamente a relaciones sin optimizar la consulta.
- **EAGER**
    - Simplicidad: evitás LazyInitializationException si accedés fuera de transacción.
    - Puede sobrecargar queries (mucha data no usada), generar joins caros y duplicar resultados.
    - Con colecciones es especialmente peligroso (memoria/tiempo).

### Recomendación para el proyecto

- Por defecto: **LAZY** en todas las relaciones, especialmente colecciones:
    - Ya tenés **LAZY** en `Direccion.personas` (OneToMany) y `Turno.jugadores` (ManyToMany) → bien.
    - Considerá cambiar `Persona.direccion` (ManyToOne) y `Socio.persona` (OneToOne) a **LAZY** SIEMPRE que adaptes las consultas.
- Inicializá explícitamente según el caso de uso:
    - Usá proyecciones DTO (ya lo hacés: `PersonaDTO`, `PersonaSocioDTO`, etc.). Esto evita problemas de lazy y reduce columnas.
    - O usá fetch join cuando necesitás entidades completas:
        - Ejemplo seguro para tu uso actual si ponés `Persona.direccion` en **LAZY**:
        > `SELECT p FROM Persona p JOIN FETCH p.direccion` (en `PersonaRepositoryImpl.buscarTodos` o la variante por ciudad).
- Evitá **EAGER** en colecciones. Si lo ponés **EAGER**, consultas como “todos los turnos con jugadores” pueden explotar en tamaño/tiempo.
- Para evitar N+1 con **LAZY**:
    - Añadí fetch join en las consultas que iteran y acceden a la relación (p.ej., personas y su dirección).
    - O seguí con DTOs como ya hacés en **selectJugadoresConSocioPorId** y **personasPorCiudadDTO**.

### Aplicado a tus archivos

- `ej2.model.Persona`:
    - ManyToOne direccion: mantener **EAGER** te funciona hoy, pero es más escalable cambiar a **LAZY** y ajustar consultas con JOIN FETCH o DTO.
- `ej2.model.Turno` y `ej2.model.Direccion`:
    - Colecciones **LAZY**: correcto.
- `ej2.model.Socio` (OneToOne): por defecto **EAGER**. Si lo usás poco, considerar **LAZY** + ajustar consultas (Hibernate lo soporta).

### Resumen

Usa **LAZY** por defecto y trae lo necesario por consulta (DTOs o fetch join).
Mantener **EAGER** en to-one solo si realmente simplifica tu flujo y el volumen es pequeño. En tu caso, con los DTOs ya implementados, conviene **LAZY** + consultas optimizadas.