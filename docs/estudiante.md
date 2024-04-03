# Estudiante

### GET /estudiante/buscar/todo
Esta solicitud retorna todos los estudiantes registrados, con estudiantes registrados la respuesta será como sigue.
```Javascript
[
    {
        "id": "1",
        "codigo": "107266377",
        "nombres": "Carlos Hernesto",
        "apellidos": "Balderrama",
        "correo": "carlos@unicauca.edu.co",
        "cohorte": "2018",
        "semestre": "2",
        "estado": "Activo",
        "creditos": "14",
        "pertenece": "Maestría",
        "tutor": {
            "id": "1",
            "nombre": "Johana Riascos"
        }
    } ...
]
```
Sin estudiantes registrados la respuesta será como sigue.
```Javascript
[]
```

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|

### GET /estudiante/buscar/codigo/:codigo
Esta solicitud retorna un estudiante que tenga el código dado en el parametro por URL `codigo`, si el estudiante es encontrado la respuesta será como sigue.
```Javascript
{
    "id": "1",
    "codigo": "107266377",
    "nombres": "Carlos Hernesto",
    "apellidos": "Balderrama",
    "correo": "carlos@unicauca.edu.co",
    "cohorte": "2018",
    "semestre": "2",
    "estado": "Activo",
    "creditos": "14",
    "pertenece": "Maestría",
    "tutor": {
        "id": "1",
        "nombre": "Johana Riascos"
    }
}
```
Si no se encuentra el estudiante, el cuerpo de la respuesta estará vacío.

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|

### GET /estudiante/buscar/match/:match
Esta solicitud retorna los estudiantes que tengan el parámetro `match` dado por URL en sus nombres, apellidos o código, si se encuentran estudiantes la respuesta será como sigue.
```Javascript
[
    {
        "id": "1",
        "codigo": "107266377",
        "nombres": "Carlos Hernesto",
        "apellidos": "Balderrama",
        "correo": "carlos@unicauca.edu.co",
        "cohorte": "2018",
        "semestre": "2",
        "estado": "Activo",
        "creditos": "14",
        "pertenece": "Maestría",
        "tutor": {
            "id": "1",
            "nombre": "Johana Riascos"
        }
    } ...
]
```
Si no se encuentran estudiantes la respuesta será como sigue.
```Javascript
[]
```

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|

### GET /estudiante/buscar/nombre/:nombre
Esta solicitud retorna los estudiantes que contengan en sus nombres o en sus apellidos el parametro `nombre` dado por URL, si se encuentran estudiantes con ese nombre, la respuesta será como sigue.
```Javascript
[
    {
        "id": "1",
        "codigo": "107266377",
        "nombres": "Carlos Hernesto",
        "apellidos": "Balderrama",
        "correo": "carlos@unicauca.edu.co",
        "cohorte": "2018",
        "semestre": "2",
        "estado": "Activo",
        "creditos": "14",        
        "pertenece": "Maestría",
        "tutor": {
            "id": "1",
            "nombre": "Johana Riascos"
        }
    } ...
]
```
Si no se encuentran estudiantes con ese nombre la respuesta será como sigue.
```Javascript
[]
```

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|

### POST /estudiante/crear
Esta solicitud crea un nuevo estudiante, se deben proveer los siguientes datos en el cuerpo de la solicitud.
```Javascript
{
    "codigo": "107266377",
    "nombres": "Carlos Hernesto",
    "apellidos": "Balderrama",
    "correo": "carlos@unicauca.edu.co",
    "cohorte": "2018",
    "semestre": "2",    
    "estado": "Activo",
    "pertenece": "Maestría",
    "tutor": "Johana Riascos"
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
|`400`|100|100|Código es muy corto|
|`400`|100|101|Código es muy largo|
|`400`|100|102|El código ya existe|
|`400`|100|104|Formato del código no es válido|
|`400`|101|100|Nombres muy cortos|
|`400`|101|101|Nombres muy largos|
|`400`|101|104|Formato de los nombres no es válido|
|`400`|102|100|Apellidos muy cortos|
|`400`|102|101|Apellidos muy largos|
|`400`|102|104|Formato de los apellidos no es válido|
|`400`|103|100|Correo muy corto|
|`400`|103|101|Correo muy largo|
|`400`|103|102|El correo ya existe|
|`400`|103|104|Formato del correo no es válido|
|`400`|104|104|Formato del cohorte no es válido|
|`400`|105|104|Formato del semestre no es válido|
|`400`|108|104|Formato del campo pertenece no es válido|
|`400`|200|103|El tutor no existe|
|`500`|||Error interno del servidor|

### POST /estudiante/actualizar
Esta solicitud actualiza los datos de un estudiante, los siguientes datos pueden ser provistos en el cuerpo de la solicitud.
```Javascript
{
    "id": "1",
    "codigo": "107266377",
    "nombres": "Carlos Hernesto",
    "apellidos": "Balderrama",
    "correo": "carlos@unicauca.edu.co",
    "cohorte": "2018",
    "semestre": "2",
    "estado": "Inactivo",    
    "pertenece": "Maestría",
    "tutor": "Johana Riascos"
}
```
El dato `id` es obligatorio.  
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
|`400`|100|100|Código es muy corto|
|`400`|100|101|Código es muy largo|
|`400`|100|102|El código ya existe|
|`400`|100|103|El estudiante no existe, el `id` no pertenece a ningún estudiante|
|`400`|100|104|Formato del código no es válido|
|`400`|101|100|Nombres muy cortos|
|`400`|101|101|Nombres muy largos|
|`400`|101|104|Formato de los nombres no es válido|
|`400`|102|100|Apellidos muy cortos|
|`400`|102|101|Apellidos muy largos|
|`400`|102|104|Formato de los apellidos no es válido|
|`400`|103|100|Correo muy corto|
|`400`|103|101|Correo muy largo|
|`400`|103|102|El correo ya existe|
|`400`|103|104|Formato del correo no es válido|
|`400`|104|104|Formato del cohorte no es válido|
|`400`|105|104|Formato del semestre no es válido|
|`400`|106|104|Formato del estado no es válido|
|`400`|108|104|Formato del campo pertenece no es válido|
|`400`|109|104|Formato del campo id no es válido|
|`400`|200|103|El tutor no existe|
|`500`|||Error interno del servidor|

### POST /estudiante/actualizar/contrasena
Esta solicitud actualiza la contraseña de un estudiante,los siguientes datos deben ser provistos en el cuerpo de la solicitud.

```Javascript
{
    "id": "1",
    "contrasena": "nuevaContrasena1"
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
|`400`|110|100|Contraseña es muy corta|
|`400`|110|101|Contraseña es muy larga|
|`400`|110|104|Formato de contraseña no es válido|
|`400`|100|103|El estudiante no existe, el `id` no pertenece a ningún estudiante|

### GET /estudiante/buscar/token/{token}
Esta solicitud retorna los datos del estudiante que esta utilizando el sistema

```Javascript
/estudiante/buscar/token/x-auth eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmdpZWRhbmllbGF2IiwiQXV0aG9yaXRoeSI6IkVzdHVkaWFudGUiLCJleHAiOjE1NDE3NzcyNzd9.exTtfntIKLxMZcemFVrYChkRoHNIsW1daNy3FBNI_Ehc-92ozSJD8oCjFeQOFR_ZYclMnGlIOADqlC3WDFtmOw
```
La respuesta es como sigue:

```Javascript
{
    "id": "1",
    "codigo": "123",
    "nombres": "daniela",
    "apellidos": "velasquez",
    "correo": "angiedanielav@unicauca.edu.co",
    "cohorte": "2018",
    "semestre": "1",
    "estado": "activo",
    "creditos": "1",
    "pertenece": "maestría",
    "tutor": {
        "id": 1,
        "nombre": "Carlos Cobos"
    }
}
```

#### Códigos de campo, códigos de error y códigos de estado
|Código de estado|Código de campo|Código de error|Descripcion|
|---|---|---|---|
|`200`|||Exitoso|
|`500`|||Error en el servidor|