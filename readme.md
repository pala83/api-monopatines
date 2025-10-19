# TP Integrador 3 - Grupo 20

## API REST - Documentacion

Base URL por defecto: http://localhost:8090

Todas las respuestas son JSON. Los errores se reportan con códigos HTTP estándar (400/404/500) y mensajes descriptivos.

---

## a) Dar de alta un estudiante

- Método: `POST`
- Ruta: `/estudiantes`
- Body: `JSON`
- Consulta rapida:
	- `http://localhost:8090/estudiantes`
	- 	``` json
		{
			"dni": 40373769,
			"lu": 25048,
			"nombre": "Ulises",
			"apellido": "Palazzo",
			"genero": "Macho",
			"ciudad": "Rauch"
		}
		```

| Campo 	| Tipo 	| Requerido |
|-----------|-------|-----------|
| dni		| Num	| x			|
| nombre	| Str	| x			|
| apellido	| Str	| x			|
| edad		| Num	|  			|
| genero	| Str	| x			|
| ciudad	| Str	| x			|
| lu		| Num	| x			|

Ejemplo usando `curl`:

```bash
curl -X POST http://localhost:8090/estudiantes \
	-H "Content-Type: application/json" \
	-d '{
		"dni": 40373769,
		"lu": 25048,
		"nombre": "Ulises",
		"apellido": "Palazzo",
		"genero": "Macho",
		"ciudad": "Rauch"
	}'
```

Respuesta `201` (ejemplo):
```json
{
	"dni": 40373769,
	"nombreCompleto": "Ulises Palazzo",
	"ciudad": "Rauch",
	"lu": 25048
}
```

---

## b) Matricular un estudiante en una carrera

- Método: `POST`
- Ruta: `/inscripciones`
- Body `JSON`:
- Consulta rapida:
	- `http://localhost:8090/inscripciones`
	- ``` json
		{
			"id_estudiante": 40373769,
			"id_carrera": 1,
			"inscripcion": 2023,
			"antiguedad": 2
		}
		```

| Campo 		| Tipo 	| Requerido |
|---------------|-------|-----------|
| id_estudiante	| Num	| x			|
| id_carrera	| Num	| x			|
| inscripcion	| Num	| x			|
| graduacion	| Num	|  			|
| antiguedad	| Num	| x			|

Ejemplo usando `curl`:
```bash
curl -X POST http://localhost:8090/inscripciones \
	-H "Content-Type: application/json" \
	-d '{
		"id_estudiante": 40373769,
		"id_carrera": 1,
		"inscripcion": 2023,
		"antiguedad": 2
	}'
```

Respuesta `201` (ejemplo):
```json
{
	"id_estudiante": 40373769,
	"id_carrera": 1,
	"inscripcion": 2023,
	"graduacion": null,
	"antiguedad": 2
}
```

---

## c) Listar estudiantes con ordenamiento simple

- Método: `GET`
- Ruta: `/estudiantes/ordenado`
- Query params:
	- orderBy: nombre del campo (por ejemplo: nombre, apellido, ciudad, genero, edad, lu, dni)
	- direction: ASC o DESC (opcional; por defecto ASC)

Ejemplo usando `curl`:
```bash
curl "http://localhost:8090/estudiantes/ordenado?orderBy=apellido&direction=DESC"
curl "http://localhost:8090/estudiantes/ordenado?orderBy=ciudad"
```

---

## d) Obtener un estudiante por LU

- Método: `GET`
- Ruta: `/estudiantes/lu/{lu}`

Ejemplo usando `curl`:
```bash
curl "http://localhost:8090/estudiantes/lu/25048"
```

---

## e) Listar estudiantes por género

- Método: `GET`
- Ruta: `/estudiantes/genero/{genero}`

Ejemplo usando `curl`:
```bash
curl "http://localhost:8090/estudiantes/genero/Macho"
```

---

## f) Carreras con cantidad de inscriptos (ordenado desc)

- Método: `GET`
- Ruta: `/carreras/inscriptos`

Ejemplo usando `curl`:
```bash
curl "http://localhost:8090/carreras/inscriptos"
```

---

## g) Estudiantes de una carrera, filtrado por ciudad (opcional)

- Método: `GET`
- Ruta: `/estudiantes/carrera/{carreraId}`
- Query param:
	- ciudad (opcional; si se omite, devuelve todos los estudiantes de la carrera)

Ejemplo usando `curl`:
```bash
# Con filtro de ciudad
curl "http://localhost:8090/estudiantes/carrera/1?ciudad=rauch"

# Sin filtro de ciudad
curl "http://localhost:8090/estudiantes/carrera/1"
```

---

## h) Reporte de carreras: inscriptos y egresados por año

- Método: `GET`
- Ruta: `/inscripciones/reporte`

Ejemplo usando `curl`:
```bash
curl "http://localhost:8090/inscripciones/reporte"
```

---

## Consideraciones generales
- Códigos de error:
	- 400 Bad Request: parámetros inválidos o cuerpo de solicitud mal formado.
	- 404 Not Found: recurso inexistente (por ejemplo, LU o IDs no encontrados).
	- 500 Internal Server Error: fallo inesperado en el servidor.
- Content-Type: usar `application/json` para los POST.
- Encoding: UTF-8.
