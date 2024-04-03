# Pasantia

### GET /pasantia/buscar/codigoEstudiante/{codigoEstudiante}
Esta solicitud consulta todos los registros de pasantía que esten asociados a un estudiante con el código de estudiante dado por parámetro de URL.  
Si se encuentran registros de pasantía, la respuesta será como sigue.
```Javascript
[
    {
        "id": 1,
        "fechaRegistro": "20-11-2018 09:06:27",
        "fechaInicio": "20-03-2018",
        "fechaFin": "25-03-2018",
        "tipoPasantia": "Nacional",
        "institucion": "Universidad del Cauca",
        "dependencia": "Grupo de investigacion",
        "nombreDependencia": "IDIS",
        "responsable": "Julian Alberto Calvache",
        "creditos": 0,
        "estado": "Por verificar",
        "observacion": "observación hecha por el coordinador",
        "estudiante": {
            "id": 1,
            "codigo": "1025448966",
            "nombres": "Omaira",
            "apellidos": "Campo Acebedo",
            "correo": "omairacampo@unicauca.edu.co",
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
Si no se encuentran registros de pasantía la respuesta será como sigue.
```Javascript
[]
```

#### Códigos de campo, códigos de error y códigos de estado
|Código de estado|Código de campo|Código de error|Descripcion|
|---|---|---|---|
|`200`|||Exitoso|
|`400`|100|103|El código del estudiante no existe|
|`500`|||Error interno del servidor|

### GET /pasantia/buscar/todo
Esta solicitud consulta todos los registros de pasantía.  
Si se encuentran registros de pasantía, la respuesta será como sigue.
```Javascript
[
    {
        "id": 1,
        "fechaRegistro": "21-11-2018 09:17:17",
        "fechaInicio": "20-03-2018",
        "fechaFin": "25-03-2018",
        "tipoPasantia": "Nacional",
        "institucion": "Universidad del Cauca",
        "dependencia": "Grupo de investigacion",
        "nombreDependencia": "IDIS",
        "responsable": "Julian Alberto Calvache",
        "creditos": 0,
        "estado": "Por verificar",
        "observacion": "observación hecha por el coordinador",
        "estudiante": {
            "id": 1,
            "codigo": "1061779399",
            "nombres": "David",
            "apellidos": "Sotelo",
            "correo": "davidfsotelom@unicauca.edu.co",
            "cohorte": 2018,
            "semestre": 1,
            "estado": "Activo",
            "creditos": 0,
            "pertenece": "Maestría",
            "tutor": {
                "id": 1,
                "nombre": "Carlos Cobos"
            }
        }
    } ...
]
```
Si no se encuentran registros de pasantía la respuesta será como sigue.
```Javascript
[]
```

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|

### GET /pasantia/descargar/{idPasantia}/{archivo}
Esta solicitud descarga un archivo de una pasantía, el parámetro `archivo` dado por URL solo puede ser: `certificado` o `informe`.
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
|`400`|1007|103|Id de la pasantía no existe|
|`400`|1008|103|El archivo no existe|
|`500`|||Error interno del servidor|

### POST /pasantia/registrar
Esta solicitud registra una nueva pasantía, usted debe enviar estos datos sin el encabezado "Content Type" y en formato "FormData" los datos que usted debe proveer son los siguientes:  
Nombre de la variable: **datos**, contenido:
```Javascript
{
    "codigoEstudiante": "1061445326",
    "fechaInicio": "20-03-2018",
    "fechaFin": "25-03-2018",
    "tipoPasantia": "Nacional",
    "institucion": "Universidad del Cauca",
    "dependencia": "Grupo de investigación",
    "nombreDependencia": "IDIS",
    "responsable": "Julian Alberto Calvache",
    "extensionInforme": "png",
    "extensionCertificado": "png"
}
```
Nombre de la variable: **informe**, informe con el visto bueno del tutor.
Nombre de la variable: **certificado**, certificado emitido por la universidad o institución donde se hizo la pasantía.  
En caso de error, la respuesta será como sigue.
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
|`400`|1000|104|La fecha de inicio del evento no es válida|
|`400`|1000|105|La fecha de inicio es superior a la fecha de fin|
|`400`|1001|104|La fecha de fin del evento no es válida|
|`400`|1001|105|La fecha de fin es superior al día actual|
|`400`|1002|104|Tipo de pasantía no es válido|
|`400`|1003|100|Institución muy corta|
|`400`|1003|101|Institución muy larga|
|`400`|1004|104|Dependencia no es válida|
|`400`|1005|100|Nombre de la dependencia muy corto|
|`400`|1005|101|Nombre de la dependencia muy largo|
|`400`|1006|100|Responsable muy corto|
|`400`|1006|101|Responsable muy largo|
|`400`|1006|104|Formato del responsable no es válido|
|`500`|||Error interno del servidor|

### POST /pasantia/actualizar/estado
Esta solicitud modifica el estado de una pasantía.  
Los datos que usted debe enviar en el cuerpo de la solicitud son los siguientes.  
```Javascript
{
    "idPasantia": "1",
    "creditos": "4",
    "estado": "Aprobado",
    "observacion": "Observación realizada por el coordinador"
}
``` 
En caso de error, la respuesta será como sigue.  
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
|`400`|1007|103|La pasantía no existe|
|`400`|1007|106|El id de la pasantía no es un valor numérico|
|`400`|1009|104|Los créditos no pueden ser 0 dado que el estado es Aprobado|
|`400`|1009|105|Los créditos son un valor mayor a 4|
|`400`|1009|106|Los créditos no son un valor numérico|
|`400`|1009|107|Los créditos son un valor menor a 0|
|`400`|1010|104|El estado no es un valor válido {Aprobado, Por verificar, Rechazado}|
|`400`|1011|101|Observación muy larga|
|`500`|||Error interno del servidor|

### DELETE /pasantia/eliminar/idPasantia/{idPasantia}
Esta solicitud permite eliminar la pasantía con el id dado por parámetro de URL.
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
|`400`|1007|103|El id de la pasantía no existe|
|`400`|1010|104|La pasantía no se puede eliminar debido a su estado|
|`500`|||Error interno del servidor|