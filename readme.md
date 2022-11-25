This is a clone rest api of Reddit.

Created with the help the tutorial of 
- https://programmingtechie.com/2020/05/14/building-a-reddit-clone-with-spring-boot-and-angular/

To set up the project you need to add the environment variables used in 

`application.properties` .

You can use the docker compose to create the database. 
 
`docker-compose up`

Rest API includes:
- Registration
  - a verification token is sent to the user email when registered and the account is only enabled after the token is activated.
- Login
  - Logging in with username and password returns a jwt which can be used for future authentication.
  - Every next request needs the bearer token in order to be authorized