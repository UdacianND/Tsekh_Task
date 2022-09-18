# Tsekh_Task
Hello, thanks for reviewing this project. Here is instruction to use this app:

1) Any request path, except `/register` and  `/login`, is authenticated.
So, to send a request to the application, you have to log in and use access token
in every request.

2) Some request paths are authorized, i.e. you need to have
ADMIN or OWNER role. And you can only send request to the company
which you are OWNER or ADMIN of. For example, you can not add new washer
to somebody's company.

3) Initially, every user has role of USER, but once you add your own company, 
you will have role of OWNER, but this role permits you only to access your
own company. Only users with OWNER role can promote somebody to ADMIN.

4) As every request is authenticated, using swagger may not be convenient.
You'd better use Postman instead, although it is not handy 
as swagger.
