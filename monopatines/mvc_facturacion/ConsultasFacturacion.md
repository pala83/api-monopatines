# 💰 Microservicio de Facturación

## 📍 **Base URL:** `http://localhost:8088/facturacion`

---

## 📡 Endpoints disponibles

| Método | Endpoint        | Descripción                       |
|:------:|-----------------|-----------------------------------|
|  GET   | `/pagos`        | Listar todos los pagos            |
|  GET   | `/pagos/{id}`   | Obtener un pago por su ID         |
|  POST  | `/pagos`        | Registrar un nuevo pago           |
|  PUT   | `/pagos/{id}`   | Actualizar un pago existente      |
| DELETE | `/pagos/{id}`   | Eliminar un pago                  |
|        |                 |                                   |
|  GET   | `/tarifas`      | Listar todas las tarifas vigentes |
|  GET   | `/tarifas/{id}` | Obtener una tarifa por su ID      |
|  POST  | `/tarifas`      | Crear una nueva tarifa            |
|  PUT   | `/tarifas/{id}` | Actualizar una tarifa existente   |
| DELETE | `/tarifas/{id}` | Eliminar una tarifa               |

---

## 🧩 Ejemplos JSON

### 1. Listar todas las tarifas
- **Método:** GET  
- **Ruta:** `http://localhost:8088/tarifas`  
- **Request Body:** —  
- **Response (200 OK):**
```json
[
  {
    "id": 1,
    "precioPorMinuto": 5.50,
    "precioPausaPorMinuto": 2.00,
    "fechaInicioVigencia": "2025-01-01T00:00:00"
  },
  {
    "id": 2,
    "precioPorMinuto": 6.00,
    "precioPausaPorMinuto": 2.50,
    "fechaInicioVigencia": "2025-06-01T00:00:00"
  }
]
```

---

### 2. Obtener una tarifa por ID
- **Método:** GET  
- **Ruta:** `http://localhost:8088/tarifas/{id}`  
- **Request Body:** —  
- **Response (200 OK):**
```json
{
  "id": 1,
  "precioPorMinuto": 5.50,
  "precioPausaPorMinuto": 2.00,
  "fechaInicioVigencia": "2025-01-01T00:00:00"
}
```

---

### 3. Crear una nueva tarifa
- **Método:** POST  
- **Ruta:** `http://localhost:8088/tarifas`  
- **Body: JSON**
```json
{
  "precioPorMinuto": 6.50,
  "precioPausaPorMinuto": 3.00,
  "fechaInicioVigencia": "2025-12-01T00:00:00"
}
```
- **Response (201 Created):**
```json
{
  "id": 3,
  "precioPorMinuto": 6.50,
  "precioPausaPorMinuto": 3.00,
  "fechaInicioVigencia": "2025-12-01T00:00:00"
}
```

---

### 4. Actualizar una tarifa
- **Método:** PUT  
- **Ruta:** `http://localhost:8088/tarifas/{id}`  
- **Body: JSON**
```json
{
  "precioPorMinuto": 7.00,
  "precioPausaPorMinuto": 3.50,
  "fechaInicioVigencia": "2025-12-15T00:00:00"
}
```
- **Response (200 OK):**
```json
{
  "id": 3,
  "precioPorMinuto": 7.00,
  "precioPausaPorMinuto": 3.50,
  "fechaInicioVigencia": "2025-12-15T00:00:00"
}
```

---

### 5. Eliminar una tarifa
- **Método:** DELETE  
- **Ruta:** `http://localhost:8088/tarifas/{id}`  
- **Request Body:** —  
- **Response (204 No Content):**  
—

---

# 🅿️ Pagos de Tarifas

## 📍 **Base URL:** `http://localhost:8088/pagos`

### 1. Listar todos los pagos
- **Método:** GET  
- **Ruta:** `http://localhost:8088/pagos`  
- **Request Body:** —  
- **Response (200 OK):**
```json
[
  {
    "id": 1,
    "idCuenta": 1001,
    "monto": 2500.75,
    "fechaPago": "2025-11-08T14:30:00",
    "medio": "TARJETA_CREDITO"
  },
  {
    "id": 2,
    "idCuenta": 1002,
    "monto": 1850.00,
    "fechaPago": "2025-11-07T11:15:00",
    "medio": "TRANSFERENCIA"
  }
]
```

---

### 2. Obtener un pago por ID
- **Método:** GET  
- **Ruta:** `http://localhost:8088/pagos/{id}`  
- **Request Body:** —  
- **Response (200 OK):**
```json
{
  "id": 1,
  "idCuenta": 1001,
  "monto": 2500.75,
  "fechaPago": "2025-11-08T14:30:00",
  "medio": "TARJETA_CREDITO"
}
```

---

### 3. Crear un nuevo pago
- **Método:** POST  
- **Ruta:** `http://localhost:8088/pagos`  
- **Body: JSON**
```json
{
  "idCuenta": 1003,
  "monto": 3200.50,
  "fechaPago": "2025-11-08T16:00:00",
  "medio": "EFECTIVO"
}
```
- **Response (201 Created):**
```json
{
  "id": 3,
  "idCuenta": 1003,
  "monto": 3200.50,
  "fechaPago": "2025-11-08T16:00:00",
  "medio": "EFECTIVO"
}
```

---

### 4. Actualizar un pago
- **Método:** PUT  
- **Ruta:** `http://localhost:8088/pagos/{id}`  
- **Body: JSON**
```json
{
  "idCuenta": 1003,
  "monto": 3300.00,
  "fechaPago": "2025-11-08T18:00:00",
  "medio": "TRANSFERENCIA"
}
```
- **Response (200 OK):**
```json
{
  "id": 3,
  "idCuenta": 1003,
  "monto": 3300.00,
  "fechaPago": "2025-11-08T18:00:00",
  "medio": "TRANSFERENCIA"
}
```

---

### 5. Eliminar un pago
- **Método:** DELETE  
- **Ruta:** `http://localhost:8088/pagos/{id}`  
- **Request Body:** —  
- **Response (204 No Content):**  
—
