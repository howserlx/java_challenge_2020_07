Java Challenge / Java School / Nearsoft 2020-07
==========================================

## Generar Alias para URL
METHOD: **POST**
ENDPOINT: **/api/create/**

| PARAM | DESCRIPTION | EJEMPLO |
|--|--|--|
| url | URL del alias a generar | "https://example.com"

    {"url" : "https://example.com"}

#### RESPONSE

    {
        "alias": "{alias}",
        "message": "{message}",
        "status": "[HTTP_STATUS]"
    }


| STATUS | DESCRIPTION  | ALIAS |  |
|--|--|--|--|
| 200 | La URL existe y se generó alias | alias |  |
| 400 | URL Malformada - No se genera alias| --- |  |
| 403 | Forbidden - No se genera alias | --- |  |
| 404 | Not Found - No se genera alias | ---|  |
| 408 | Timeout - No se genera alias | --- |  |
| 500 | Error del sistema - No se genera alias | --- |  |

Cuando se manda una **ulr** a insertar, se hace primero una petición GET , el resultado de esa petición determinará si se agrega o no a la lista de URL acortadas a través de un alias, solo los códigos 200 y 302 serán insertados, cualquier otro código diferente regresará el mensaje y código de error.

Los alias se generan de la siguiente manera:
| CONTIENE | REGLA |
|--|--|
| google | cadena *alpha* de 5 caracteres |
| yahoo| cadena *alphanumeric* de 7 caracteres |
| cualquiera que no contengo lo anterior | se forma con la url en minúsculas removiendo caracteres especiales, digitos y vocales |




## Obtener redirección de alias
METHOD: **GET**
ENDPOINT: **/{alias}**

| PARAM | DESCRIPTION | EJEMPLO |
|--|--|--|
| alias| alias generado | /BdfGT

| STATUS | DESCRIPTION   |  |
|--|--|--|
| --- | Si el alias existe realiza la redirección |  |
| 400 | URL Malformada - No hay alias| --- |  |
| 404 | No existe alias registrado | ---|  |
| 500 | Error del sistema | --- |  |

