<h2><b>1. Tablas de la base de datos que usaré</b></h2>
<p>Para el CRUD de alquiler necesito estas tablas:</p>
<ul>
<li>RESERVA (principal).</li>
<li>CLIENTE</li> 
<li>LIBRO</li>
<li>TRABAJADOR</li>
<li>FICHAJE</li>
</ul>
<h2><b>2.  Campos de la tabla RESERVA y mapeo de la API </b></h2>

| Campo                   | Tipo         | ¿En API? | Razón                                                     |
| ----------------------- | ------------ | -------- | --------------------------------------------------------- |
| ID_reserva              | INT          | Sí       | Para poder identificar cada reserva                       |
| DNI_cliente             | VARCAHR (FK) | Sí       | Para identificar quién ha realizado la reserva            |
| ISBN                    | VARCHAR (FK) | Sí       | Para identificar el libro reservado                       |
| Fecha_Inicio            | TIMESTAMP    | Sí       | Fecha en la que se inicia la reserva                      |
| Fecha_Devolucion        | TIMESTAMP    | Sí       | Fecha en la que el libro ha de ser devuelto               |
| Estado_Libro_Inicio     | VARCHAR      | Sí       | Información del estado del libro cuando la reserva inicia |
| Estado_Libro_Devolución | VARCHAR      | SÍ       | Información del estado del libro cuando es devuelto       |
<p>De estos campos, el backend generará automáticamente:</p>
<ul>
<li>ID_reserva (AUTO_INCREMENT)</li>
<li>Fecha_Inicio, Fecha_Devolución (Timestamp automáticos)</li>
</ul>
<h2><b>3. Validaciones principales </b></h2>
<p>Al crear/modificar reservas se validarán los siguientes campos:</p>
<h3>Campos obligatorios</h3>
<ul>
<li>DNI_cliente</li>
<li>ISBN</li>
<li>Estado_Libro_Inicio</li>
</ul>
<p>Lógica: </p>
<ul>
<li>Fecha_Inicio < Fecha_Devolucion </li>
<li>Fecha_Inicio >= fecha actual (no se permiten fechas pasadas)</li>
<li>ID_reserva: valor único</li>
<li>ISBN: debe estar registrado en la tabla libro</li>
</ul>
<p>Estados permitidos:</p>
<p>Los estados los gestionará el backend, pero las únicas opciones válidas al iniciar la reserva de un libro serán: </p>
<ul>
<li>Libre</li>
<li>Reservado</li>
</ul>
<h2>Ejemplos de JSON</h2>
<p>Crear reserva: petición que envía el cliente para crear una reserva</p>

<pre> ```json
{
	"DNI_cliente": "75354598v",
	"ISBN": "978-1-4028-9462-6",
	"Estado_Libro_Inicio": "nuevo"
}
```
</pre>
<p>Respuesta que el backend devolvería si la creación de la reserva ha sido exitosa:</p>
<pre> ```json
{
	"ID_Reserva" : 561,
	"DNI_cliente": "75354598v",
	"ISBN": "978-1-4028-9462-6",
	"Estado": "Reservado",
	"Fecha_Inicio": "2026-12-09T12:35:00Z",
	"Fecha_Devolucion": "2026-12-23T12:35:00Z",
	"Estado_Libro_Inicio": "nuevo"
}
```
</pre>
<p>Lista de reservas del usuario autenticado:</p>
<pre> ```json
[
	{
		"ID_Reserva": 561,
		"ISBN": "978-1-4028-9462-6",
		"Estado" : "Reservado",
		"Fecha_Inicio": "2026-12-09T12:35:00Z",
		"Fecha_Devolucion": "2026-12-23T12:35:00Z",
		"Estado_Libro_Inicio": "nuevo"
	},
	{
		"ID_Reserva": 589,
		"ISBN": "238-1-4958-5483-8",
		"Estado" : "Libre",
		"Fecha_Inicio": "2026-02-01T17:24:01Z",
		"Fecha_Devolucion": "2026-02-15T17:24:01Z",
		"Estado_Libro_Inicio": "rasguño en la portada y pequeñas marcas en algunas páginas"
	}
]
```
</pre>
<p>Respuesta cuando hay error de validación:</p>
<pre> ```json
{
"Error": "Datos de entrada inválidos",
"Detalles": 
	[
		{
		"Campo": "ISBN",
		"Mensaje": "El ISBN solo puede contener números."
		},
		
		{
		"Campo": "Estado_Libro_Inicio",
		"Mensaje": "Campo obligatorio. Introduce una breve descripción del libro."
		},
		
		{
		"Campo": "DNI_Cliente",
		"Mensaje": "DNI incorrecto. Inserte de nuevo."
		}
	]
}
```
</pre>
<![[Pasted image 20260302200127.png](/imagenes/Pasted%20image%2020260302200012.png)]>