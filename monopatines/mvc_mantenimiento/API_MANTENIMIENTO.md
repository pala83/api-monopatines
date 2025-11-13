# API de Mantenimiento - Documentación

## Descripción General
Esta API permite gestionar el mantenimiento de monopatines, incluyendo el registro de entrada y salida de mantenimiento, asegurando que los monopatines no estén disponibles durante el proceso de mantenimiento.

---

## Endpoints

### 1. Registrar Monopatín en Mantenimiento

**Endpoint:** `POST /registroMantenimientos`

**Descripción:** Registra un monopatín en mantenimiento y lo marca como NO disponible automáticamente.

**Request Body:**
```json
{
  "idMonopatin": 1
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "idMonopatin": 1,
  "fechaInicio": "2025-11-13T10:30:00",
  "fechaFin": null
}
```

**Validaciones implementadas:**
- ✅ Verifica que el monopatín existe (consulta al microservicio de monopatines)
- ✅ Valida que no haya otro mantenimiento activo para ese monopatín
- ✅ Marca automáticamente el estado del monopatín como "MANTENIMIENTO"

**Errores posibles:**
- `404 Not Found`: El monopatín no existe
- `400 Bad Request`: El monopatín ya está en mantenimiento

---

### 2. Finalizar Mantenimiento

**Endpoint:** `PATCH /registroMantenimientos/{id}/finalizar`

**Descripción:** Finaliza un registro de mantenimiento y marca el monopatín como DISPONIBLE nuevamente.

**Request:** No requiere body

**Response (200 OK):**
```json
{
  "id": 1,
  "idMonopatin": 1,
  "fechaInicio": "2025-11-13T10:30:00",
  "fechaFin": "2025-11-13T14:45:00"
}
```

**Validaciones implementadas:**
- ✅ Verifica que el registro de mantenimiento existe
- ✅ Valida que el mantenimiento no haya sido finalizado previamente
- ✅ Marca automáticamente el estado del monopatín como "DISPONIBLE"

**Errores posibles:**
- `404 Not Found`: El registro de mantenimiento no existe
- `400 Bad Request`: El mantenimiento ya fue finalizado

---

### 3. Obtener Todos los Registros de Mantenimiento

**Endpoint:** `GET /registroMantenimientos`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "idMonopatin": 1,
    "fechaInicio": "2025-11-13T10:30:00",
    "fechaFin": "2025-11-13T14:45:00"
  },
  {
    "id": 2,
    "idMonopatin": 5,
    "fechaInicio": "2025-11-13T15:00:00",
    "fechaFin": null
  }
]
```

---

### 4. Obtener Registro de Mantenimiento por ID

**Endpoint:** `GET /registroMantenimientos/{id}`

**Response (200 OK):**
```json
{
  "id": 1,
  "idMonopatin": 1,
  "fechaInicio": "2025-11-13T10:30:00",
  "fechaFin": "2025-11-13T14:45:00"
}
```

---

### 5. Obtener Historial de Mantenimientos de un Monopatín

**Endpoint:** `GET /registroMantenimientos/monopatin/{idMonopatin}`

**Descripción:** Obtiene todos los registros de mantenimiento (finalizados y activos) de un monopatín específico.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "idMonopatin": 1,
    "fechaInicio": "2025-11-10T09:00:00",
    "fechaFin": "2025-11-10T12:00:00"
  },
  {
    "id": 5,
    "idMonopatin": 1,
    "fechaInicio": "2025-11-13T10:30:00",
    "fechaFin": null
  }
]
```

---

### 6. Obtener Mantenimientos Activos de un Monopatín

**Endpoint:** `GET /registroMantenimientos/monopatin/{idMonopatin}/activos`

**Descripción:** Obtiene solo los registros de mantenimiento activos (sin fecha fin) de un monopatín.

**Response (200 OK):**
```json
[
  {
    "id": 5,
    "idMonopatin": 1,
    "fechaInicio": "2025-11-13T10:30:00",
    "fechaFin": null
  }
]
```