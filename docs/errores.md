# Errores

### Códigos de error generales
|Código del campo|Código de error|Descripción|
|---|---|---|
|1|1|Faltan datos para procesar la solicitud|

### Códigos de error
|Código de error|Descripcion|
|---|---|
|100|Longitud muy corta|
|101|Longitud muy larga|
|102|El recurso ya existe|
|103|El recurso no existe|
|104|El formato no es válido|
|105|El valor es superior a su valor permitido|
|106|El tipo de dato es incorrecto|
|107|El valor es inferior a su valor permitido|

#### Estudiante
|Campo|Código del campo|
|---|---|
|codigo|100|
|nombres|101|
|apellidos|102|
|correo|103|
|cohorte|104|
|semestre|105|
|estado|106|
|creditos|107|
|pertenece|108|
|id|109|
|contraseña|110|

#### Tutor
|Campo|Código del campo|
|---|---|
|nombre|200|

#### Usuario
|Campo|Código del campo|
|---|---|
|usuario|300|
|contrasena|301|
|estado|302|

### Publicacion
|Campo|Código del campo|
|---|---|
|autor|400|
|autoresSecundarios|401|
|fechaPublicacion|402|
|fechaAceptacion|403|
|extencionIndice|404|
|id|405|
|creditos|406|
|estado|407|
|comentario|408|

### Publicacion_Revista
|Campo|Código del campo|
|---|---|
|tituloArtículo|500|
|nombreRevista|501|
|categoria|502|
|doi|503|
|extencionArticulo|504|
|extencionCorreoAceptacion|505|
|extencionClasificacionRevista|506|
|archivo|507|

### Publicacion_Evento
|Campo|Código del campo|
|---|---|
|doi|600|
|fechaInicio|601|
|fechaFin|602|
|issn|603|
|tituloPonencia|604|
|nombreEvento|605|
|TipoEvento|606|
|pais|607|
|ciudad|608|
|archivo|609|

### Publicacio_Libro
|Campo|Código del campo|
|---|---|
|isbn|700|
|tituloLibro|701|
|editorial|702|
|pais|703|
|ciudad|704|
|extencionLibro|704|
|extencionCertificadoEditorial|705|
|archivo|706|

### Publicacion_Capitulo_Libro
|Campo|Código del campo|
|---|---|
|isbn|800|
|tituloCapituloLibro|801|
|tituloLibro|802|
|editorial|803|
|pais|804|
|ciudad|805|
|archivo|806|

### Practica_Docente
|Campo|Código del campo|
|---|---|
|fechaInicio|900|
|fechaFin|901|
|tipoPracticaDocente|902|
|id|903|
|archivo|904|
|id|905|
|horas|906|
|estado|907|
|observacion|908|

### Pasantia
|Campo|Código del campo|
|---|---|
|fechaInicio|1000|
|fechaFin|1001|
|tipoPasantia|1002|
|instutucion|1003|
|dependencia|1004|
|nombreDependencia|1005|
|responsable|1006|
|id|1007|
|archivo|1008|
|creditos|1009|
|estado|1010|
|observacion|1011|