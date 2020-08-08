# Revista

### GET /publicacion/revista/buscar/idPublicacion/{idPublicacion}
Esta solicitud consulta una publicación en revista que tenga asociada una publicación con el id dado por parametro de URL.  
Si se encuentra la publicación, la respuesta será como sigue.
```Javascript
{
    "id": 1,
    "doi": "10.1145/1067268.1067287",
    "tituloArticulo": "Diseño industrial",
    "nombreRevista": "Industry",
    "categoria": "A1",
    "publicacion": {
        "id": 5,
        "autor": "Johana Ramirez",
        "autoresSecundarios": "Alberto Bastidaz, Andrés Muñoz",
        "fechaPublicacion": "28-04-2018",
        "fechaAceptacion": "15-03-2018",
        "tipoDocumento": "Revista",
        "creditos": 0,
        "estado": "Por verificar",        
        "comentario": "",
        "fechaRegistro": "2018-11-16 10:37:23",
        "estudiante": {
            "id": 1,
            "codigo": "106257474",
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

### GET /publicacion/revista/descargar/{idPublicacion}/{archivo}
Esta solicitud descarga un archivo de una publicación de tipo revista, el parámetro `archivo` dado por URL debe ser uno de los siguientes cuatro valores `indice`, `articulo`, `correoAceptacion` y `clasificacionRevista`.
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
|`400`|405|103|Id de la publicación no existe como publicación de revista|
|`400`|507|103|El archivo no existe|
|`500`|||Error interno del servidor|

### POST /publicacion/revista/registrar
Esta solicitud registra una nueva publicación en un revista, usted debe enviar estos datos sin el encabezado "Content Type" y en formato "FormData" los datos que usted debe proveer son los siguientes:  
Nombre de la variable: **datos**, contenido:
```Javascript
{
    "codigoEstudiante": "1061445326",
    "autor": "Johana Ramirez",
    "autoresSecundarios": "Alberto Bastidaz, Andrés Muñoz",
    "fechaAceptacion": "15-03-2018",
    "fechaPublicacion": "28-04-2018",
    "doi": "10.1145/1067268.1067287",
    "tituloArticulo": "Diseño industrial",
    "nombreRevista": "Industry",
    "categoria": "A1",
    "extensionIndice": "pdf",
    "extensionArticulo": "pdf",
    "extensionCorreoAceptacion": "png",
    "extensionClasificacionRevista": "jpg"
}
```
Nombre de la variable: **indice**, contenido: archivo del indice de la revista en formato PDF.  
Nombre de la variable: **articulo**, contenido: archivo con el artículo en formato PDF.  
Nombre de la variable: **correoAceptacion**, contenido: pantallazo del correo de aceptación del artículo en formato PNG, JPG o PDF.  
Nombre de la variable: **clasificacionRevista**, contenido: pantallazo con la clasificación de la revista al momento de publicar el artículo en formato PNG, JPG o PDF.  
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
|`400`|500|100|Título del artículo muy corto|
|`400`|500|101|Título del artículo muy largo|
|`400`|500|102|Título del artículo ya está registrado|
|`400`|501|100|Nombre de la revista muy corto|
|`400`|501|101|Nombre de la revista muy largo|
|`400`|502|104|Formato de la categoría no es válido|
|`400`|503|100|DOI muy corto|
|`400`|503|101|DOI muy largo|
|`400`|503|104|Formato del DOI no es válido|
|`400`|404|104|Formato de extensión del indice no es válido|
|`400`|504|104|Formato de extensión del árticulo no es válido|
|`400`|505|104|Formato de extensión del correo de aceptación no es válido|
|`400`|506|104|Formato de extensión de clasificación de la revista no es válido|
|`500`|||Error interno del servidor|