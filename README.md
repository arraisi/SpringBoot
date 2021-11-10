# Template Spring Boot with Spring security + jwt

# Setup Database #

* Run MariaDB on docker \
   run this script in your project location: 
  
        docker run --rm \
          --name theta-db \
          -e MARIADB_DATABASE=theta \
          -e MARIADB_USER=theta \
          -e MARIADB_PASSWORD=1xylixmaF7b7rTYqqQ2Q \
          -e MARIADB_ROOT_PASSWORD=1xylixmaF7b7rTYqqQ2Q \
          -v "$PWD/thetadb-data:/var/lib/mysql" \
          -p 127.0.0.1:3306:3306 mariadb:10


## Running the application

You can run your application in dev mode that enables live coding using:
```shell script
mvn clean spring-boot:run
```


## Packaging and running the application

The application can be packaged using:
```shell script
mvn clean package
```
It produces the `*.jar` file in the `target/theta-sb-template-#.#.#-SNAPSHOT.jar`.


# Auth Protocol #

Custom spring security protocol:

* **CustomAuthenticationFilter** extends **UsernamePasswordAuthenticationFilter**, custom flow of authentication:
   * **attemptAuthentication** -> called when user request authentication, then handle with
     authenticationmanager
   * **successfulAuthentication** -> called when user authentication success, then return token
* **CustomAuthorizationFilter** extends **OncePerRequestFilter**, flow custom of authorization:
   * doFilterInternal -> verification token, get info from jwt token and give authorization to user

# Annotation #

## Core Spring Framework Annotations

* **@Autowired** \
  Semua field/property yang memiliki anotasi @Autowired akan diisikan oleh Spring dengan object bertipe-data sesuai. \
  \
  This annotation is applied on fields, setter methods, and constructors. The @Autowired annotation injects object dependency implicitly. \
  When you use @Autowired on fields and pass the values for the fields using the property name, Spring will automatically assign the fields with the passed values. \
  \
  You can even use @Autowired on private properties, as shown below. (This is a very poor practice though!)

      public class Customer {
        @Autowired                               
        private Person person;                   
        private int type;
      }

  When you use @Autowired on setter methods, Spring tries to perform the by Type autowiring on the method. You are instructing Spring that it should initiate this property using setter method where you can add your custom code, like initializing any other property with this property.

      public class Customer {
          private Person person;
  
          @Autowired
          public void setPerson (Person person) {
              this.person=person;
          }
      }

  Consider a scenario where you need instance of class A, but you do not store A in the field of the class. You just use A to obtain instance of B, and you are storing B in this field. In this case setter method autowiring will better suite you. You will not have class level unused fields. \
  \
  When you use @Autowired on a constructor, constructor injection happens at the time of object creation. It indicates the constructor to autowire when used as a bean. One thing to note here is that only one constructor of any bean class can carry the @Autowired annotation.

      @Component
      public class Customer {
          private Person person;
  
          @Autowired
          public Customer (Person person) {
              this.person=person;
          }
      }
  NOTE: As of Spring 4.3, @Autowired became optional on classes with a single constructor. In the above example, Spring would still inject an instance of the Person class if you omitted the @Autowired annotation.


* **@Configuration** \
  This annotation is used on classes which define beans. @Configuration is an analog for XML configuration file – it is configuration using Java class. Java class annotated with @Configuration is a configuration by itself and will have methods to instantiate and configure the dependencies.


* **@Bean** \
  This annotation is used at the method level. @Bean annotation works with @Configuration to create Spring beans. As mentioned earlier, @Configuration will have methods to instantiate and configure dependencies. Such methods will be annotated with @Bean. The method annotated with this annotation works as bean ID and it creates and returns the actual bean.


## Spring Framework Stereotype Annotations

* **@Component** \
  This annotation is used on classes to indicate a Spring component. The @Component annotation marks the Java class as a bean or say component so that the component-scanning mechanism of Spring can add into the application context.


* **@Controller** \
  The @Controller annotation is used to indicate the class is a Spring controller. This annotation can be used to identify controllers for Spring MVC or Spring WebFlux.


* **@Service** \
  This annotation is used on a class. The @Service marks a Java class that performs some service, such as execute business logic, perform calculations and call external APIs. This annotation is a specialized form of the @Component annotation intended to be used in the service layer.


* **@Repository** \
  This annotation is used on Java classes which directly access the database. The @Repository annotation works as marker for any class that fulfills the role of repository or Data Access Object. \
  This annotation has a automatic translation feature. For example, when an exception occurs in the @Repository there is a handler for that exception and there is no need to add a try catch block.


## Spring MVC and REST Annotations

* **@Controller** \
  This annotation is used on Java classes that play the role of controller in your application. The @Controller annotation allows autodetection of component classes in the classpath and auto-registering bean definitions for them. To enable autodetection of such annotated controllers, you can add component scanning to your configuration. The Java class annotated with @Controller is capable of handling multiple request mappings. \
  This annotation can be used with Spring MVC and Spring WebFlux.
  

* **@RequestMapping** \
  This annotation is used both at class and method level. The @RequestMapping annotation is used to map web requests onto specific handler classes and handler methods. When @RequestMapping is used on class level it creates a base URI for which the controller will be used. When this annotation is used on methods it will give you the URI on which the handler methods will be executed. From this you can infer that the class level request mapping will remain the same whereas each handler method will have their own request mapping. \
  Sometimes you may want to perform different operations based on the HTTP method used, even though the request URI may remain the same. In such situations, you can use the method attribute of @RequestMapping with an HTTP method value to narrow down the HTTP methods in order to invoke the methods of your class.


* **@RestController** \
  This annotation is used at the class level. The @RestController annotation marks the class as a controller where every method returns a domain object instead of a view. By annotating a class with this annotation you no longer need to add @ResponseBody to all the RequestMapping method. It means that you no more use view-resolvers or send html in response. You just send the domain object as HTTP response in the format that is understood by the consumers like JSON. \
  \
  @RestController is a convenience annotation which combines @Controller and @ResponseBody.


## Spring Framework DataAccess Annotations
  
* @Transactional \
  This annotation is placed before an interface definition, a method on an interface, a class definition, or a public method on a class. The mere presence of @Transactional is not enough to activate the transactional behaviour. The @Transactional is simply a metadata that can be consumed by some runtime infrastructure. This infrastructure uses the metadata to configure the appropriate beans with transactional behaviour. \
  \
  The annotation further supports configuration like: 
  * The Propagation type of the transaction
  * The Isolation level of the transaction 
  * A timeout for the operation wrapped by the transaction 
  * A read only flag – a hint for the persistence provider that the transaction must be read only
    The rollback rules for the transaction 
    
Source : `https://springframework.guru/spring-framework-annotations/`

# Rest API Test #

  * Login 

    ```
    curl --location --request POST 'http://localhost:8080/theta/api/login' \
       --header 'Content-Type: application/x-www-form-urlencoded' \
       --data-urlencode 'username=arnold@mail.com' \
       --data-urlencode 'password=1234'
    ```

    Response: 

    ```
    {
        "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcm5vbGRAbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9URU5BTlQiLCJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3RoZXRhL2FwaS9sb2dpbiIsImV4cCI6MTYzMzMxNDEwMiwiZW1haWwiOiJ1c2VyZW1haWwifQ.RW8q8aMHPUQL6zAZISigWB87htzS2sUm8VMv8qkcCso",
        "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcm5vbGRAbWFpbC5jb20iLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvdGhldGEvYXBpL2xvZ2luIiwiZXhwIjoxNjMzMzE1MzAyfQ.0uNHb2on42ZSveBOOGvPVwLLTvIsxXN6mwYKl9I1UYE"
       }
    ```

  * Save Person

    ```
    curl --location --request POST 'localhost:8080/theta/api/person/save' \
       --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcm5vbGRAbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9URU5BTlQiLCJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3RoZXRhL2FwaS9sb2dpbiIsImV4cCI6MTYzMzMxNDEwMiwiZW1haWwiOiJ1c2VyZW1haWwifQ.RW8q8aMHPUQL6zAZISigWB87htzS2sUm8VMv8qkcCso' \
       --header 'Content-Type: application/json' \
       --data-raw '{
           "map": {},
           "transitMap": {},
           "name": "Geek",
           "email": "geek@mail.com",
           "password": "1234",
           "active": true,
           "attachmentList": []
       }'
    ```
  
  ### ManyToMany Example

  * Person List

    ```
    curl --location --request GET 'localhost:8080/theta/api/person/list' \
       --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcm5vbGRAbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9URU5BTlQiLCJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3RoZXRhL2FwaS9sb2dpbiIsImV4cCI6MTYzMzMxMTU3NCwiZW1haWwiOiJ1c2VyZW1haWwifQ.9Hh04dscYErAJ9zwFxMtyOMzTA5tc1B1bwDtcQJ1kFc'
    ```
  
    Response:

    ```
    [
        {
            "id": 1001,
            "map": {},
            "transitMap": {},
            "name": "John Travolta",
            "email": "john@mail.com",
            "password": "$2a$10$mtEAmwAl1SSg/cfuavxME.3wBqlsTSIv.jjdmq73k8TlHmPBTRCDi",
            "active": true,
            "roles": [
                {
                    "id": 1001,
                    "name": "ROLE_USER"
                },
                {
                    "id": 1002,
                    "name": "ROLE_TENANT"
                }
            ],
            "attachmentList": []
        },
        {
            "id": 1002,
            "map": {},
            "transitMap": {},
            "name": "Will Smith",
            "email": "will@mail.com",
            "password": "$2a$10$MKtR6IhurqMaLZW4IaWdtugcqjAElDpnXcSkmG.cpHhYA2o1dOyGu",
            "active": true,
            "roles": [
                {
                    "id": 1002,
                    "name": "ROLE_TENANT"
                }
            ],
            "attachmentList": []
        }
    ]
    ```
  
  * Role List

    ```
    curl --location --request GET 'localhost:8080/theta/api/role/list' \
    --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcm5vbGRAbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9URU5BTlQiLCJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3RoZXRhL2FwaS9sb2dpbiIsImV4cCI6MTYzNDE4NzgxMH0.h1iRJndB6hothbwBE-VZ-iT5V9iUC1aeM4KAK9Hx_94'
    ```

    Response:

    ```
    [
        {
            "id": 1001,
            "name": "ROLE_USER",
            "persons": [
                {
                    "id": 1001,
                    "map": {},
                    "transitMap": {},
                    "name": "John Travolta",
                    "email": "john@mail.com",
                    "password": "$2a$10$mtEAmwAl1SSg/cfuavxME.3wBqlsTSIv.jjdmq73k8TlHmPBTRCDi",
                    "active": true,
                    "attachmentList": []
                }
            ]
        },
        {
            "id": 1002,
            "name": "ROLE_TENANT",
            "persons": [
                {
                    "id": 1001,
                    "map": {},
                    "transitMap": {},
                    "name": "John Travolta",
                    "email": "john@mail.com",
                    "password": "$2a$10$mtEAmwAl1SSg/cfuavxME.3wBqlsTSIv.jjdmq73k8TlHmPBTRCDi",
                    "active": true,
                    "attachmentList": []
                },
                {
                    "id": 1002,
                    "map": {},
                    "transitMap": {},
                    "name": "Will Smith",
                    "email": "will@mail.com",
                    "password": "$2a$10$MKtR6IhurqMaLZW4IaWdtugcqjAElDpnXcSkmG.cpHhYA2o1dOyGu",
                    "active": true,
                    "attachmentList": []
                }
            ]
        }
    ]
    ```
  
  ### OneToMany Example 

  * Shop List

    ```
    curl --location --request GET 'http://localhost:8080/theta/api/shop/list' \
    --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcm5vbGRAbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9URU5BTlQiLCJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3RoZXRhL2FwaS9sb2dpbiIsImV4cCI6MTYzMzYxNzQyOX0.gZW_RlObb_3EHsSlMd80UuXOIgWG7zJ60HGZIfE2A1o'
    ```

    Response:
    
    ```
    [
        {
            "id": 1,
            "map": {
                "level1": {
                    "level2": {
                        "level3": {
                            "level4": {
                                "level5": {}
                            }
                        }
                    }
                }
            },
            "transitMap": {},
            "slug": "slug-001",
            "name": "Shop 001",
            "additionalProductList": [
                {
                    "id": 1,
                    "slug": "slug-001",
                    "name": "Product 001",
                    "quantity": 1
                },
                {
                    "id": 2,
                    "slug": "slug-001",
                    "name": "Product 002",
                    "quantity": 1
                },
                {
                    "id": 3,
                    "slug": "slug-001",
                    "name": "Product 003",
                    "quantity": 1
                }
            ],
            "date": null,
            "mainProduct": {
                "id": null,
                "shop": null,
                "slug": null,
                "name": null,
                "quantity": null
            }
        }
    ]
    ```
