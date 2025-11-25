# Grupo 20 – Arquitectura Web 2025

## Despliegue - en tramite de que funcione, paciencia

### Despliegue de microservicios Gateway
#### Pasos necesarios
- **1 mvn clean package -DskipTests**  <---- **Crea los .jar necesarios para hacer andar toda la build de docker**
- **2 docker system prune -a --volumes** **(opcional o si tira error de containers duplicados en cache)**
- **3 docker-compose down -v** (opcional remueve la build si es necesario)
- **4 docker-compose up** **Levanta el servicio**
- #### Importar el archivo Tpe Arq Grupo20.postman_collection.json a postman, va a tirar error en {{BASE_PATH}} rellenar con http://localhost:8080 **Este archivo tiene los endpoints del gateway y de los servicios individuales**
- #### Tambien va a tirar error en Authorization elegi el Auth Type: Bearer Token y copia en {{TOKEN}} el token sacado de AuthenticateUser 

# Sistema de Monopatines - Arquitectura de Microservicios

```mermaid
erDiagram
%% Microservicio de Usuario
    USUARIO ||--o{ USUARIO_CUENTA : tiene
    CUENTA ||--o{ USUARIO_CUENTA : pertenece
    USUARIO {
        Long id PK
        String nombre
        String apellido
        String email
        String telefono
        String password
        String rol
    }
    CUENTA {
        Long id PK
        Double saldo
        Timestamp fechaCreacion
        boolean active
        String tipo
    }
    USUARIO_CUENTA {
        Long usuarioId FK
        Long cuentaId FK
    }

%% Microservicio de Monopatín
    MONOPATIN }o--|| PARADA : esta_en
    MONOPATIN }o--o{ REGISTRO_MANTENIMIENTO : tiene
    MONOPATIN {
        Long id PK
        String codigoQR
        String marca
        double kmTotales
        Long usoTotalMinutos
        String estado
        LocalDateTime fechaUltimoMantenimiento
        String ubicacionActual
        Long paradaActualId FK
    }
    PARADA {
        Long id PK
        String nombre
        String ubicacion
        Integer capacidad
    }

%% Microservicio de Mantenimiento
    CONTROL_MANTENIMIENTO {
        Long id PK
        Double kilometraje
        Long usoMinutos
        Boolean activo
        LocalDateTime ultimoMantenimiento
    }
    REGISTRO_MANTENIMIENTO {
        Long id PK
        Long idMonopatin FK
        LocalDateTime fechaInicio
        LocalDateTime fechaFin
    }

%% Microservicio de Viaje
    VIAJE }o--|| MONOPATIN : utiliza
    VIAJE }o--|| USUARIO : realizado_por
    VIAJE }o--|| CUENTA : se_cobra_a
    VIAJE ||--o{ PAUSA : tiene
    VIAJE {
        Long id PK
        Long idUsuario FK
        Long idMonopatin FK
        Long idCuenta FK
        LocalDateTime fechaInicio
        LocalDateTime fechaFin
        String ubicacionInicio
        String ubicacionFin
        double distanciaRecorrida
        String estado
    }
    PAUSA {
        Long id PK
        Long viajeId FK
        LocalDateTime tiempoInicio
        LocalDateTime tiempoFin
        Long duracionSegundos
        Boolean extendido
    }

%% Microservicio de Facturación
    CARGA }o--|| VIAJE : por
    CARGA }o--|| CUENTA : se_carga_a
    SUBSCRIPTION }o--|| CUENTA : para
    CARGA {
        Long id PK
        Long viajeId FK
        Long cuentaId FK
        Long montoTotal
        Long cargaNormal
        Long cargaPausaExtendida
        Long duracionMinutos
        Long duracionPausaMinutos
        Double kmRecorridos
        LocalDateTime fechaCarga
    }
    SUBSCRIPTION {
        Long id PK
        Long idCuenta FK
        YearMonth periodo
        LocalDate fechaPago
        Long monto
        String estado
    }
    TARIFA {
        Long id PK
        Double precioPorMinuto
        Double precioPorMinutoExtendido
        Double mensualidadPremium
    }
```