Java Challenge / Java School / Nearsoft 2020-07
================================================

Obtener redirección de alias
GET
http://localhost:8080/{alias}

Redirect url -> Si encuentra el alias en memoria
404 -> El alias no ha sido registrado
400 -> no se mandó alias
500 -> Internar server en conversión de url




Crear alias para url
POST
http://localhost:8080/api/create/
POST BODY
{
 "url" : "https://example.com"
}

RESPONSE
{
    "alias": "ALIAS",
    "message": "Alias created",
    "status": "OK"
}
