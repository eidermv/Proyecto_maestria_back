# Evento

### GET /publicacion/evento/buscar/idPublicacion/{idPublicacion}
Esta solicitud consulta una publicación en un evento que tenga asociada una publicación con el id dado por parametro de URL.  
Si se encuentra la publicación, la respuesta será como sigue.
```Javascript
{
    "id": 1,
    "doi": "10.1145/1067268.1067287",
    "fechaInicio": "20-03-2018",
    "fechaFin": "25-03-2018",
    "issn": "1234-1234",
    "tituloPonencia": "La gran revolución",
    "nombreEvento": "Congreso nacional de revoluciones",
    "tipoEvento": "Congreso",
    "pais": "Colombia",
    "ciudad": "Cali",
    "publicacion": {
        "id": 4,
        "autor": "Johana Ramirez",
        "autoresSecundarios": "Alberto Bastidaz, Andrés Muñoz",
        "fechaPublicacion": "28-04-2018",
        "fechaAceptacion": "15-03-2018",
        "tipoDocumento": "Evento",
        "creditos": 0,
        "estado": "Por verificar",
        "comentario": "",
        "fechaRegistro": "2018-11-16 10:37:23",
        "estudiante": {
            "id": 1,
            "codigo": "107265363",
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

### GET /publicacion/evento/descargar/{idPublicacion}/{archivo}
Esta solicitud descarga un archivo de una publicación de tipo evento, el parámetro `archivo` dado por URL debe ser uno de los siguientes tres valores `indice`, `ponencia` y `certificadoEvento`.
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
|`400`|405|103|Id de la publicación no existe como publicación en evento|
|`400`|609|103|El archivo no existe|
|`500`|||Error interno del servidor|

### POST /publicacion/evento/registrar
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
    "fechaInicio": "20-03-2018",
    "fechaFin": "25-03-2018",
    "issn": "1234-1234",
    "tituloPonencia": "La gran revolución",
    "nombreEvento": "Congreso nacional de revoluciones",
    "tipoEvento": "Congreso",
    "pais": "Colombia",
    "ciudad": "Cali",
    "extensionIndice": "pdf",
    "extensionPonencia": "pdf",
    "extensionCertificadoEvento": "png"
}
```
Nombre de la variable: **indice**, contenido: archivo del indice del congreso en formato PDF.  
Nombre de la variable: **ponencia**, contenido: archivo con la ponencia en formato PDF.  
Nombre de la variable: **certificadoEvento**, contenido: pantallazo del certificado del evento en formato PNG, JPG o PDF.  
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
|`400`|403|105|La fecha de aceptaciónes superior ala fecha de publicación|
|`400`|600|100|DOI muy corto|
|`400`|600|101|DOI muy largo|
|`400`|600|104|Formato del DOI no es válido|
|`400`|601|104|La fecha de inicio del evento no es válida|
|`400`|601|105|La fecha de inicio es superior a la fecha de fin|
|`400`|602|104|La fecha de fin del evento no es válida|
|`400`|602|105|La fecha de fin es superior al día actual|
|`400`|603|100|ISSN muy corto|
|`400`|603|101|ISSN muy largo|
|`400`|603|104|Formato del ISSN no es válido|
|`400`|604|100|Título de la ponencia muy corto|
|`400`|604|101|Título de la ponencia muy largo|
|`400`|604|102|Título de la ponencia ya existe|
|`400`|605|100|Nombre del evento muy corto|
|`400`|605|101|Nombre del evento muy largo|
|`400`|606|104|Formato del tipo de evento no es válido|
|`400`|607|100|Pais muy corto|
|`400`|607|101|Pais muy largo|
|`400`|607|104|Formato del pais no es válido|
|`400`|608|100|Ciudad muy corta|
|`400`|608|101|Ciudad muy larga|
|`400`|608|104|Formato de ciudad no es válido|
|`500`|||Error interno del servidor|