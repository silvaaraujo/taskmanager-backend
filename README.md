# taskmanager-backend
API ReST for a simple task manager application

##Created with:  
Spring Tools Suite  
JAVA 8  
SPRING BOOT  
SPRING MVC  
SPRING SECURITY  
SPRING DATA  
JWT (json web token)  
MYSQL  
  
 
This API uses JWT to keep safe all requests.  
  
  
To test this API, you'll need first authenticate at the '/login' with a valid user and password and than uses the generated token at the next request.  
Postman payload example:  
{
    "email": "mail@mail.com",
    "senha": "yourpassword"
}  

At each request is generated a new token and added at the response.    

