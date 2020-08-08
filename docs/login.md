# Login

### POST /login
Esta solicitud permite ingresar al sistema de gestión documental de la universidad del Cauca.

El cuerpo de la solicitud debe ser como sigue:

```Javascript
{
    "usuario": "nombreusuario",
    "contrasena": "contrasena_usuario"
}
```
Si la solicitud es exitosa, en el cuerpo de la respuesta se encuentra el token de autenticación: 

```Javascript
{
    "token" : "x-auth eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwbWFnZSIsIkF1dGhvcml0aHkiOiJDb29yZGluYWRvciIsImV4cCI6MTU0MTYyNDg1M30.9i0iM4BPc4tpS53L614g_V52Ub6hHitUwvltuGe1KJBxOE6HFBTaUllwpFs-MLklnYHrJ5XkRvLQjBaTEdFcyw",
    "roles" : "Coordinador"
}
```
En caso de que el usuario tenga más de un rol asignado la respuesta de la solicitud será como sigue:

```Javascript
{
    "token" : "x-auth eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwbWFnZSIsIkF1dGhvcml0aHkiOiJDb29yZGluYWRvciIsImV4cCI6MTU0MTYyNDg1M30.9i0iM4BPc4tpS53L614g_V52Ub6hHitUwvltuGe1KJBxOE6HFBTaUllwpFs-MLklnYHrJ5XkRvLQjBaTEdFcyw",
    "roles" : "Coordinador, Admin"
}
```

#### Status codes

|Estado solicitud|Descripción|
|---|---|
|`200`|Exitosa|
|`403`|Credenciales incorrectas o el usuario está inactivo|

### Uso del token otras solicitudes
Para acceder a las funcionalidades de la aplicación es necesario enviar en el encabezado de la solicitud el token.


```Javascript
{
    "Headers":
    {
        ...
        "authorization" : "x-auth eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwbWFnZSIsIkF1dGhvcml0aHkiOiJDb29yZGluYWRvciIsImV4cCI6MTU0MTYyNDg1M30.9i0iM4BPc4tpS53L614g_V52Ub6hHitUwvltuGe1KJBxOE6HFBTaUllwpFs-MLklnYHrJ5XkRvLQjBaTEdFcyw"
        ...
    }
}
```
|Estado solicitud|Descripción|
|---|---|
|`200`|Exitosa|
|`403`|El token expiró|
|`500`|El usuario intentó acceder a una funcionalidad no disponible para su tipo de usuario o el token está  corrupto|