# Tutor

### GET /tutor/buscar/todo
Esta solicitud retorna todos los tutores registrados, si hay tutores registrados la respuesta es como sigue.
```Javascript
[
    {
        "nombre": "Johana Riascos"
    } ...
]
```
Si no hay tutores registrados la respuesta es como sigue.
```Javascript
[]
```

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|

### GET /tutor/buscar/nombre/:nombre
Esta solicitud retorna los tutores que contengan en sus nombre el parámetro `nombre` dado por URL, si hay tutores que tengan el nombre dado, la respuesta es como sigue.
```Javascript
[
    {
        "nombre": "Johana Riascos"
    } ...
]
```
Si no hay tutores que tengan el nombre dado, la respuesta es como sigue.
```Javascript
[]
```

#### Códigos de estado
|Código de estado|Descripcion|
|---|---|
|`200`|Exitoso|
|`500`|Error interno del servidor|