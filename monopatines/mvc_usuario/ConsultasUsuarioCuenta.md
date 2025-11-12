# 👥 Microservicio de Usuario

## 📍 **Base URLs:**  
- ### Usuarios → `http://localhost:8082/usuario`  
- ### Cuentas → `http://localhost:8082/cuenta`  

---

## 📡 Endpoints disponibles

| Método | Endpoints       | Descripción                     |
|:------:|-----------------|---------------------------------|
|  GET   | `/usuario`      | Listar todos los usuarios       |
|  GET   | `/usuario/{id}` | Obtener un usuario por su ID    |
|  POST  | `/usuario`      | Crear un nuevo usuario          |
|  PUT   | `/usuario/{id}` | Actualizar un usuario existente |
| DELETE | `/usuario/{id}` | Eliminar un usuario             |
|        |                 |                                 |
|  GET   | `/cuenta`       | Listar todas las cuentas        |
|  GET   | `/cuenta/{id}`  | Obtener una cuenta por su ID    |
|  POST  | `/cuenta`       | Crear una nueva cuenta          |
|  PUT   | `/cuenta/{id}`  | Actualizar una cuenta existente |
| DELETE | `/cuenta/{id}`  | Eliminar una cuenta             |

---

## 🧩 Ejemplos JSON

### 🔹 Usuario

#### 1. Listar todos los usuarios
- **Método:** GET  
- **Ruta:** `http://localhost:8082/usuarios`  
- **Request Body:** —  
- **Response (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@example.com",
    "telefono": "+54 9 1122334455",
    "password": "hashed_password",
    "rol": "USUARIO",
    "cuentas": [
      { "id": 1, "saldo": 5000.0, "activa": true }
    ]
  },
  {
    "id": 2,
    "nombre": "Ana",
    "apellido": "García",
    "email": "ana.garcia@example.com",
    "telefono": "+54 9 1166778899",
    "password": "hashed_password",
    "rol": "ADMIN",
    "cuentas": []
  }
]
```

---

#### 2. Crear un nuevo usuario
- **Método:** POST  
- **Ruta:** `http://localhost:8082/usuarios`  
- **Body: JSON**
```json
{
  "nombre": "María",
  "apellido": "Lopez",
  "email": "maria.lopez@example.com",
  "telefono": "+54 9 1155667788",
  "password": "secure_password",
  "rol": "USUARIO"
}
```
- **Response (201 Created):**
```json
{
  "id": 3,
  "nombre": "María",
  "apellido": "Lopez",
  "email": "maria.lopez@example.com",
  "telefono": "+54 9 1155667788",
  "rol": "USUARIO",
  "cuentas": []
}
```

---

#### 3. Actualizar un usuario
- **Método:** PUT  
- **Ruta:** `http://localhost:8082/usuarios/{id}`  
- **Body: JSON**
```json
{
  "nombre": "María",
  "apellido": "Lopez",
  "email": "maria.lopez@example.com",
  "telefono": "+54 9 1144455566",
  "rol": "ADMIN"
}
```
- **Response (200 OK):**
```json
{
  "id": 3,
  "nombre": "María",
  "apellido": "Lopez",
  "email": "maria.lopez@example.com",
  "telefono": "+54 9 1144455566",
  "rol": "ADMIN"
}
```

---

# 🔹 Cuenta

## 📍 **Base URL:** `http://localhost:8082/cuentas`

#### 1. Listar todas las cuentas
- **Método:** GET  
- **Ruta:** `http://localhost:8082/cuentas`  
- **Request Body:** —  
- **Response (200 OK):**
```json
[
  {
    "id": 1,
    "saldo": 5000.00,
    "fechaCreacion": "2025-10-01T10:00:00",
    "activa": true,
    "usuarios": [
      { "id": 1, "nombre": "Juan", "apellido": "Pérez", "email": "juan.perez@example.com" }
    ]
  },
  {
    "id": 2,
    "saldo": 2500.50,
    "fechaCreacion": "2025-11-05T09:30:00",
    "activa": false,
    "usuarios": []
  }
]
```

---

#### 2. Crear una nueva cuenta
- **Método:** POST  
- **Ruta:** `http://localhost:8082/cuentas`  
- **Body: JSON**
```json
{
  "saldo": 1000.00,
  "fechaCreacion": "2025-11-08T12:00:00",
  "activa": true,
  "tipo": "PREMIUM"
}
```
- **Response (201 Created):**
```json
{
  "id": 3,
  "saldo": 1000.00,
  "fechaCreacion": "2025-11-08T12:00:00",
  "activa": true,
  "usuarios": []
}
```

---

#### 3. Actualizar una cuenta
- **Método:** PUT  
- **Ruta:** `http://localhost:8082/cuentas/{id}`  
- **Body: JSON**
```json
{
  "saldo": 1500.75,
  "activa": false
}
```
- **Response (200 OK):**
```json
{
  "id": 3,
  "saldo": 1500.75,
  "fechaCreacion": "2025-11-08T12:00:00",
  "activa": false
}
```

---

#### 4. Eliminar una cuenta
- **Método:** DELETE  
- **Ruta:** `http://localhost:8082/cuentas/{id}`  
- **Request Body:** —  
- **Response (204 No Content):**  
—
