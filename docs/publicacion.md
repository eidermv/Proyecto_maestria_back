# Publicacion

### GET /publicacion/buscar/codigoEstudiante/{codigoEstudiante}
Esta solicitud devuelve todas las publicaciones del estudiante que tiene el código dado por parametro de URL.  
Si hay publicaciones registradas para ese estudiante, la respuesta será como sigue.
```Javascript
[
    {
        "id": 1,
        "autor": "Johana Ramirez",
        "autoresSecundarios": "Alberto Bastidaz, Andrés Muñoz",
        "fechaPublicacion": "28-04-2018",
        "fechaAceptacion": "15-03-2018",
        "tipoDocumento": "Libro",
        "creditos": 0,
        "estado": "Por verificar",
        "comentario": "",
        "fechaRegistro": "2018-11-16 10:37:23",
        "estudiante": {
            "id": 1,
            "codigo": "107654673",
            "nombres": "Carlos Alberto",
            "apellidos": "Ruiz",
            "correo": "carlos@unicauca.edu.co",
            "cohorte": 2018,
            "semestre": 2,
            "estado": "Activo",
            "creditos": 0,
            "pertenece": "Maestría",
            "tutor": {
                "id": 1,
                "nombre": "Johana Riascos"
            }
        }
    } ...
]
```
Si no existen publicaciones registradas la respuesta es como sigue.
```Javascript
[]
```
En caso de error, el servicio devolverá una respuesta como sigue.
```Javascript
{
    "campo": "código del campo",
    "error": "código del error"
}
```

#### Códigos de campo, códigos de error y códigos de estado
|Código de estado|Código de campo|Código de error|Descripcion|
|---|---|---|---|
|`200`|||Exitoso|
|`400`|100|103|El código del estudiante no existe|
|`500`|||Error interno del servidor|

### GET /publicacion/buscar/todo
Esta solicitud devuelve todas las publicaciones registradas.  
Si hay publicaciones registradas la respuesta será como sigue.
```Javascript
[
    {
        "id": 1,
        "autor": "Johana Ramirez",
        "autoresSecundarios": "Alberto Bastidaz, Andrés Muñoz",
        "fechaPublicacion": "28-04-2018",
        "fechaAceptacion": "15-03-2018",
        "tipoDocumento": "Libro",
        "creditos": 0,
        "estado": "Por verificar",
        "fechaRegistro": "2018-11-16 10:37:23",
        "estudiante": {
            "id": 1,
            "codigo": "107654673",
            "nombres": "Carlos Alberto",
            "apellidos": "Ruiz",
            "correo": "carlos@unicauca.edu.co",
            "cohorte": 2018,
            "semestre": 2,
            "estado": "Activo",
            "creditos": 0,
            "pertenece": "Maestría",
            "tutor": {
                "id": 1,
                "nombre": "Johana Riascos"
            }
        }
    } ...
]
```
Si no existen publicaciones registradas la respuesta es como sigue.
```Javascript
[]
```

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|

### POST /publicacion/actualizar/estado
Esta solicitud permite cambiar el estado de una publicación.  
El cuerpo de la solicitud debe estar como sigue: 
```Javascript
{
    "idPublicacion": 1,
    "creditos": 2,
    "estado": "Aprobado",
    "comentario": "Explicación por qué la publicación obtuvo el estado asignado"
}

```
Si el estado de la publicación no es `Aprobado` los creditos se asignarán en 0.  
En caso de error, el servicio devolverá una respuesta como sigue.
```Javascript
{
    "campo": "código del campo",
    "error": "código del error"
}
```

#### Códigos de campo, códigos de error y códigos de estado
|Código de estado|Código de campo|Código de error|Descripcion|
|---|---|---|---|
|`200`|||Exitoso|
|`400`|1|1|Faltan datos para procesar la solicitud|
|`400`|405|106|El id de la publicación no es un valor numérico|
|`400`|405|103|La publicación no existe|
|`400`|406|106|Los créditos no son un valor numérico|
|`400`|406|105|Los créditos son un valor mayor a 6|
|`400`|406|107|Los créditos son un valor menor a 0|
|`400`|406|104|Los créditos no pueden ser 0 dado que el estado es aprobado|
|`400`|407|104|El estado no es un valor válido {Aprobado, Por verificar, Rechazado}|
|`400`|408|101|El comentario supera los 300 carácteres|
|`500`|||Error interno del servidor|

### POST /publicacion/eliminar
Esta solicitud permite eliminar una publicación.
El cuerpo de la solicitud debe estar como sigue: 
```Javascript
{
    "idPublicacion": 1
}
```
En caso de error, el servicio devolverá una respuesta como sigue.
```Javascript
{
    "campo": "código del campo",
    "error": "código del error"
}
```

#### Códigos de campo, códigos de error y códigos de estado
|Código de estado|Código de campo|Código de error|Descripcion|
|---|---|---|---|
|`200`|||Exitoso|
|`400`|1|1|Faltan datos para procesar la solicitud|
|`400`|405|106|El id de la publicación no es un valor numérico|
|`400`|405|103|La publicación no existe|
|`500`|||Error interno del servidor|