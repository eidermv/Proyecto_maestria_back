# CapituloLibro

### GET /publicacion/capituloLibro/buscar/idPublicacion/{idPublicacion}
Esta solicitud consulta una publicación de libro que tenga asociada una publicación con el id dado por parametro de URL.  
Si se encuentra la publicación, la respuesta será como sigue.
```Javascript
{
    "id": 1,
    "isbn": "19-12-65-54-6-56854",
    "tituloLibro": "El arte de reparar",
    "tituloCapituloLibro": "Reparar con la mente",
    "editorial": "Editorial planeta",
    "publicacion": {
        "id": 3,
        "autor": "Johana Ramirez",
        "autoresSecundarios": "Alberto Bastidaz, Andrés Muñoz",
        "fechaPublicacion": "28-04-2018",
        "fechaAceptacion": "15-03-2018",
        "tipoDocumento": "Capítulo de libro",
        "creditos": 0,
        "estado": "Por verificar",
        "comentario": "",
        "fechaRegistro": "2018-11-16 10:37:23",
        "estudiante": {
            "id": 1,
            "codigo": "107263547",
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

### GET /publicacion/capituloLibro/descargar/{idPublicacion}/{archivo}
Esta solicitud descarga un archivo de una publicación de tipo capítulo de libro, el parámetro `archivo` dado por URL debe ser uno de los siguientes tres valores `indice`, `capituloLibro` y `certificadoEditorial`.
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
|`400`|405|103|Id de la publicación no existe como publicación de capítulo de libro|
|`400`|806|103|El archivo no existe|
|`500`|||Error interno del servidor|

### POST /publicacion/capituloLibro/registrar
Esta solicitud registra una publicación de tipo capítulo de libro, usted debe enviar estos datos sin el encabezado "Content Type" y en formato "FormData" los datos que usted debe proveer son los siguientes:  
Nombre de la variable: **datos**, contenido:
```Javascript
{
    "codigoEstudiante": "1061445326",
    "autor": "Johana Ramirez",
    "autoresSecundarios": "Alberto Bastidaz, Andrés Muñoz",
    "fechaAceptacion": "15-03-2018",
    "fechaPublicacion": "28-04-2018",
    "isbn": "19-12-65-54-6-56854",
    "tituloCapituloLibro": "Reparar con la mente",
    "tituloLibro": "El arte de reparar",
    "editorial": "Editorial planeta",
    "extensionIndice": "pdf",
    "extensionCapituloLibro": "pdf",
    "extensionCertificadoEditorial": "png"
}
```
Nombre de la variable: **indice**, contenido: archivo del indice del libro en formato PDF.  
Nombre de la variable: **captituloLibro**, contenido: archivo con el capítulo del libro en formato PDF.  
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
|`400`|402|105|La fecha de publicación es superior al día actual|
|`400`|403|104|La fecha de aceptación no es válida|
|`400`|403|105|La fecha de aceptación es superior a la fecha de publicación|
|`400`|800|100|ISBN muy corto|
|`400`|800|101|ISBN muy largo|
|`400`|800|104|Formato del ISBN no es válido|
|`400`|801|101|Título del capítulo del libro muy largo|
|`400`|801|100|Título del capítulo del libro muy corto|
|`400`|801|102|Título del capítulo del libro ya existe|
|`400`|802|100|Título del libro muy corto|
|`400`|802|101|Título del libro muy largo|
|`400`|803|100|Editorial muy corta|
|`400`|803|101|Editorial muy larga|
|`500`|||Error interno del servidor|
