# PracticaDocente

### GET /practicaDocente/buscar/codigoEstudiante/{codigoEstudiante}
Esta solicitud consulta todas los registros de práctica docente que esten asociados a un estudiante con el código de estudiante dado por parámetro de URL.  
Si se encuentran registros de práctica docente, la respuesta será como sigue.
```Javascript
[
    {
        "id": 1,
        "tipoPracticaDocente": "Direccion de Trabajo de Grado en pregrado/posgrado",
        "fechaRegistro": "20-11-2018 08:47:28",
        "fechaInicio": "04-04-2018",
        "fechaFin": "27-06-2018",
        "horas": 0,
        "estado": "Por verificar",
        "creditos": 0,
        "observacion": "observación hecha por el coordinador",
        "estudiante": {
            "id": 1,
            "codigo": "1025447899",
            "nombres": "Carlos Alberto",
            "apellidos": "Osorio",
            "correo": "carlos@unicauca.edu.co",
            "cohorte": 2018,
            "semestre": 2,
            "estado": "Activo",
            "creditos": 0,
            "pertenece": "Doctorado",
            "tutor": {
                "id": 1,
                "nombre": "Johana Riascos"
            }
        }
    } ...
]
```
Si no se encuentran registros de práctica docente la respuesta será como sigue.
```Javascript
[]
```

#### Códigos de campo, códigos de error y códigos de estado
|Código de estado|Código de campo|Código de error|Descripcion|
|---|---|---|---|
|`200`|||Exitoso|
|`400`|100|103|El código del estudiante no existe|
|`500`|||Error interno del servidor|

### GET /practicaDocente/buscar/todo
Esta solicitud consulta todas los registros de práctica docente.  
Si se encuentran registros de práctica docente, la respuesta será como sigue.
```Javascript
[
    {
        "id": 1,
        "tipoPracticaDocente": "Direccion de Trabajo de Grado en pregrado/posgrado",
        "fechaRegistro": "21-11-2018 09:12:04",
        "fechaInicio": "04-04-2018",
        "fechaFin": "27-06-2018",
        "horas": 0,
        "estado": "Por verificar",
        "creditos": 0,
        "observacion": "observación hecha por el coordinador",
        "estudiante": {
            "id": 1,
            "codigo": "1025447899",
            "nombres": "Carlos Alberto",
            "apellidos": "Osorio",
            "correo": "carlososorio@unicauca.edu.co",
            "cohorte": 2018,
            "semestre": 1,
            "estado": "Activo",
            "creditos": 0,
            "pertenece": "Maestría",
            "tutor": {
                "id": 1,
                "nombre": "Jhoana Riascos"
            }
        }
    } ...
]
```
Si no se encuentran registros de práctica docente la respuesta será como sigue.
```Javascript
[]
```

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|

### GET /practicaDocente/descargar/{idPracticaDocente}/{archivo}
Esta solicitud descarga un archivo de una práctica docente, el parámetro `archivo` dado por URL solo puede ser `certificado`.
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
|`400`|903|103|Id de la práctica docente no existe|
|`400`|904|103|El archivo no existe|
|`500`|||Error interno del servidor|


### GET /practicaDocente/consultar/creditosyhoras/codigoEstudiante/{codigoEstudiante}
Esta solicitud consulta las horas y creditos que tiene el estudiante con el código dado por parametro de la URL y la respuesta será como sigue.  
```Javascript
{
    "horas": 120,
    "creditos": 2.5
}
```
En caso de error, la respusta será como sigue.

#### Códigos de campo, códigos de error y códigos de estado
|Código de estado|Código de campo|Código de error|Descripcion|
|---|---|---|---|
|`200`|||Exitoso|
|`400`|100|103|El estudiante no existe|
|`500`|||Error interno del servidor|

### POST /practicaDocente/registrar
Esta solicitud registra una nueva práctica docente, usted debe enviar estos datos sin el encabezado "Content Type" y en formato "FormData" los datos que usted debe proveer son los siguientes:  
Nombre de la variable: **datos**, contenido:
```Javascript
{
    "codigoEstudiante": "1061445326",
    "tipoPracticaDocente": "Dirección de Trabajo de Grado en pregrado/posgrado",
    "fechaInicio": "20-03-2018",
    "fechaFin": "25-03-2018",
    "extensionCertificado": "png"
}
```
Nombre de la variable: **certificado**, contenido: certificado, firmado por el docente al que ayudó con la práctica docente.  
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
|`400`|900|104|La fecha de inicio del evento no es válida|
|`400`|900|105|La fecha de inicio es superior a la fecha de fin|
|`400`|901|104|La fecha de fin del evento no es válida|
|`400`|901|105|La fecha de fin es superior al día actual|
|`400`|902|104|Tipo de práctica docente no es válido|
|`500`|||Error interno del servidor|

### POST /practicaDocente/actualizar/estado
Esta solicitud modifica el estado de una práctica docente.  
Los datos que usted debe enviar en el cuerpo de la solicitud son los siguientes.  
```Javascript
{
    "idPracticaDocente": "1",
    "horas": "24",
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
|`400`|905|103|La práctica docente no existe|
|`400`|905|106|El id de la práctica docente no es un valor numérico|
|`400`|906|104|Las horas no pueden ser 0 dado que el estado es Aprobado|
|`400`|906|105|Las horas son un valor mayor a 288|
|`400`|906|106|Las horas no son un valor numérico|
|`400`|906|107|Las horas son un valor menor a 0|
|`400`|907|104|El estado no es un valor válido {Aprobado, Por verificar, Rechazado}|
|`400`|908|101|Observación muy larga|
|`500`|||Error interno del servidor|

### DELETE /practicaDocente/eliminar/idPracticaDocente/{idPracticaDocente}
Esta solicitud permite eliminar la práctica docente con el id dado por parámetro de URL.
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
|`400`|905|103|El id de la práctica docente no existe|
|`400`|907|104|La práctica docente no se puede eliminar debido a su estado|
|`500`|||Error interno del servidor|