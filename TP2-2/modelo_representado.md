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