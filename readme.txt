http://localhost:8189/api
http://localhost:8189/api/auth
http://localhost:8189/api/reg
----------------------
http://localhost:8189/api/auth
401-Unauthorized
----------------------
http://localhost:8189/api/reg
    POST: username, password, confirmPassword, email.
----------------------
http://localhost:8189/api/auth
    POST: username, password.
    RESPONSE: token
----------------------
http://localhost:8189/api/unsecured
http://localhost:8189/api/secured
http://localhost:8189/api/admin
http://localhost:8189/api/admin/catalog
    GET: token.
