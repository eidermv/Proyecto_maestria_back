# Libro

### GET /publicacion/libro/buscar/idPublicacion/{idPublicacion}
Esta solicitud consulta una publicación de capítulo de libro que tenga asociada una publicación con el id dado por parametro de URL.  
Si se encuentra la publicación, la respuesta será como sigue.
```Javascript
{
    "id": 1,
    "isbn": "19-12-65-54-6-56854",
    "tituloLibro": "El arte de reparar",
    "editorial": "Editorial planeta",
    "pais": "Chile",
    "ciudad": "Santiago de Chile",
    "publicacion": {
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
            "codigo": "1067625364",
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
    }
}
```
Si no se encuentra la publicación la respuesta será como sigue.
```Javascript
**Cuerpo vacio**
```

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|

### GET /publicacion/libro/descargar/{idPublicacion}/{archivo}
Esta solicitud descarga un archivo de una publicación de tipo libro, el parámetro `archivo` dado por URL debe ser uno de los siguientes tres valores `indice`, `libro` y `certificadoEditorial`.
Si ocurre algún error, la respuesta será como sigue.
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
|`400`|405|103|Id de la publicación no existe como publicación de libro|
|`400`|706|103|El archivo no existe|
|`500`|||Error interno del servidor|

### POST /publicacion/libro/registrar
Esta solicitud registra una publicación de tipo libro, usted debe enviar estos datos sin el encabezado "Content Type" y en formato "FormData" los datos que usted debe proveer son los siguientes:  
Nombre de la variable: **datos**, contenido:
```Javascript
{
    "codigoEstudiante": "1061445326",
    "autor": "Johana Ramirez",
    "autoresSecundarios": "Alberto Bastidaz, Andrés Muñoz",
    "fechaAceptacion": "15-03-2018",
    "fechaPublicacion": "28-04-2018",
    "isbn": "19-12-65-54-6-56854",
    "tituloLibro": "El arte de reparar",
    "editorial": "Editorial planeta",
    "pais": "Chile",
    "ciudad": "Santiago de Chile",
    "extensionIndice": "pdf",
    "extensionLibro": "pdf",
    "extensionCertificadoEditorial": "png"
}
```
Nombre de la variable: **indice**, contenido: archivo del indice del libro en formato PDF.  
Nombre de la variable: **libro**, contenido: archivo con el libro en formato PDF.  
Nombre de la variable: **certificadoEditorial**, contenido: pantallazo del certificado de la editorial en formato PNG, JPG o PDF.  
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
|`400`|100|103|El código del estudiante no existe|
|`400`|400|100|El autor es muy corto|
|`400`|400|101|El autor es muy largo|
|`400`|400|104|Formato del autor no es válido|
|`400`|401|100|Autores secundarios muy corto|
|`400`|401|101|Autores secundarios muy largo|
|`400`|401|104|Formato de autores secundarios no es válido|
|`400`|402|104|La fecha de publicación no es válida|
|`400`|402|105|La fecha de publicación no puede ser superior al día actual|
|`400`|403|104|La fecha de aceptación no es válida|
|`400`|403|105|La fecha de aceptación no puede ser superior a la fecha de publicacion|
|`400`|700|100|ISBN muy corto|
|`400`|700|101|ISBN muy largo|
|`400`|700|104|Formato del ISBN no es válido|
|`400`|701|100|Título del libro muy corto|
|`400`|701|101|Título del libro muy largo|
|`400`|701|102|Título del libro ya existe|
|`400`|702|100|Editorial muy corta|
|`400`|702|101|Editorial muy larga|
|`400`|703|100|Pais muy corto|
|`400`|703|101|Pais muy largo|
|`400`|703|104|Formato del pais no es válido|
|`400`|704|100|Ciudad muy corta|
|`400`|704|101|Ciudad muy larga|
|`400`|704|104|Formato de la ciudad no es válido|
|`400`|404|104|Formato de extensión del indice no es válido|
|`400`|704|104|Formato de extensión del libro no es válido|
|`400`|705|104|Formato de extensión del certificado de la editorial no es válido|
|`500`|||Error interno del servidor| 