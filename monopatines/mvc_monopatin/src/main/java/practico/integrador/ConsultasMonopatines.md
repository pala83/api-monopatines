
# 🛴 Microservicio de Monopatines

📍 **Base URL:** `http://localhost:8084/monopatines`

---

## 📡 Endpoints disponibles

| Método | Endpoint                   | Descripción                              |
|:-------:|----------------------------|------------------------------------------|
| GET     | `/monopatines`             | Listar todos los monopatines             |
| GET     | `/monopatines/{id}`        | Obtener un monopatín por su ID           |
| POST    | `/monopatines`             | Crear un nuevo monopatín                 |
| PUT     | `/monopatines/{id}`        | Actualizar un monopatín existente        |
| DELETE  | `/monopatines/{id}`        | Eliminar un monopatín                    |
| GET     | `/monopatines/disponibles` | Listar todos los monopatines disponibles |

---

## 🧩 Ejemplos JSON

- **Método:** GET `http://localhost:8084/monopatines`
- **Request Body:**
- **Response (200 OK):**
```json
[
  {
    "id": 1,
    "codigoQR": "QR123",
    "kmTotales": 100.5,
    "estado": "DISPONIBLE",
    "paradaActual": {
      "id": 10,
      "nombre": "Plaza Central"
    }
  },
  {
    "id": 2,
    "codigoQR": "QR456",
    "kmTotales": 300.1,
    "estado": "EN_USO",
    "paradaActual": null
  }
]

```

### 2. Obtener monopatín por ID
- **Método:** GET
- **Ruta:** `http://localhost:8084/monopatines/{id}`
- **Respuesta (200 OK):**

```json
[{
"id": 1,
"codigoQR": "QR123",
"kmTotales": 120.5,
"estado": "DISPONIBLE",
"paradaActual": {
"id": 10,
"nombre": "Plaza Central"
}
}]
```


### 3. Crear monopatín
- **Método:** POST
- **Ruta:** `http://localhost:8084/monopatines`
- **Body: JSON**
- **Ejemplo con curl:**

```json 
[
  {
   "codigoQR": "QR789",
   "kmTotales": 0,
   "estado": "DISPONIBLE",
   "paradaActual": { "id": 11 }
   }]
```
**Response (201 Created):**
```json 
    [{
    "id": 3,
    "codigoQR": "QR789",
    "kmTotales": 0,
    "estado": "DISPONIBLE",
    "paradaActual": {
    "id": 11,
    "nombre": "Terminal Norte"
    }
    }
]
```


### 4. Actualizar monopatín
- **Método:** PUT
- **Ruta:** `http://localhost:8084/monopatines/{id}`
- **Body: JSON**
- **Ejemplo con curl:**
  
```json 
[
  {
   "codigoQR": "QR789",
   "kmTotales": 15.2,
   "estado": "EN_USO",
   "paradaActual": null
  }
  
]
```

**Response (200 OK):**
```json 
    [
    {
    "id": 3,
    "codigoQR": "QR789",
    "kmTotales": 15.2,
    "estado": "EN_USO",
    "paradaActual": null
    }
    ]
```


### 5. Eliminar monopatín
   
- **Método:** DELETE
- **Ruta:** `http://localhost:8084/monopatines/{id}`
- **Body: JSON**
- **Ejemplo con curl:**

**Response (200 OK):**



### 6. Listar monopatines disponibles
- **Método:** GET
- **Ruta:** `http://localhost:8084/monopatines/disponibles`
- **Respuesta (200 OK):**

```json 
    [
  {
    "id": 1,
    "codigoQR": "QR123",
    "kmTotales": 120.5,
    "estado": "DISPONIBLE",
    "paradaActual": {
    "id": 10,
    "nombre": "Plaza Central"
    }
  }
]
```

# 🅿️ Paradas de Monopatines

📍 **Base URL:** `http://localhost:8084/paradas`

---

## 📡 Endpoints disponibles

| Método | Endpoint              | Descripción                        |
|:-------:|-----------------------|------------------------------------|
| GET     | `/paradas`            | Listar todas las paradas           |
| GET     | `/paradas/{id}`       | Obtener una parada por su ID       |
| POST    | `/paradas`            | Crear una nueva parada             |
| PUT     | `/paradas/{id}`       | Actualizar una parada existente    |
| DELETE  | `/paradas/{id}`       | Eliminar una parada                |

---

## 🧩 Ejemplos JSON

### 1. Listar todas las paradas
- **Método:** GET
- **Ruta:** `http://localhost:8084/paradas`
- **Request Body:** —
- **Response (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Plaza Central",
    "latitud": -37.321,
    "longitud": -59.132,
    "monopatines": [
      {
        "id": 1,
        "codigoQR": "QR123",
        "kmTotales": 100.5,
        "estado": "DISPONIBLE"
      },
      {
        "id": 2,
        "codigoQR": "QR456",
        "kmTotales": 300.1,
        "estado": "EN_USO"
      }
    ]
  },
  {
    "id": 2,
    "nombre": "Terminal Norte",
    "latitud": -37.315,
    "longitud": -59.120,
    "monopatines": []
  }
]
```
### 2. Obtener parada por ID
- **Método:** GET
- **Ruta:** `http://localhost:8084/paradas/{id}`
- **Request Body:** —
- **Response (200 OK):**

```json
{
  "id": 1,
  "nombre": "Plaza Central",
  "latitud": -37.321,
  "longitud": -59.132,
  "monopatines": [
    {
      "id": 1,
      "codigoQR": "QR123",
      "kmTotales": 100.5,
      "estado": "DISPONIBLE"
    }
  ]
}

```

### 3. Crear nueva parada
- **Método:** POST
- **Ruta:** `http://localhost:8084/paradas`
- **Body: JSON**
- **Ejemplo con curl:**

```json 
{
  "nombre": "Parque Independencia",
  "latitud": -37.322,
  "longitud": -59.140
}

```
- **Response (201 Created):**

```json 
{
  "id": 3,
  "nombre": "Parque Independencia",
  "latitud": -37.322,
  "longitud": -59.140,
  "monopatines": []
}

```

### 4. Actualizar parada
- **Método:** PUT
- **Ruta:** `http://localhost:8084/paradas/{id}`
- **Body: JSON**
- **Ejemplo con curl:**

```json 
{
  "nombre": "Parque Independencia - Norte",
  "latitud": -37.320,
  "longitud": -59.138
}

```
- **Response (200 OK):**
```json 
{
  "id": 3,
  "nombre": "Parque Independencia - Norte",
  "latitud": -37.320,
  "longitud": -59.138,
  "monopatines": []
}

```

### 5. Eliminar parada

- **Método:** DELETE
- **Ruta:** `http://localhost:8084/paradas/{id}`
- **Body: JSON**
- **Ejemplo con curl:**

- **Response (204 OK):**