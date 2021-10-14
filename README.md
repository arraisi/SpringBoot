# Template Spring Boot with Spring security + jwt

# Setup Database #

* Run MariaDB on docker

   run this script in your project location
      ```
      docker run --rm \
      --name theta-db \
      -e MARIADB_DATABASE=theta \
      -e MARIADB_USER=theta \
      -e MARIADB_PASSWORD=1xylixmaF7b7rTYqqQ2Q \
      -e MARIADB_ROOT_PASSWORD=1xylixmaF7b7rTYqqQ2Q \
      -v "$PWD/thetadb-data:/var/lib/mysql" \
      -p 127.0.0.1:3306:3306 \
      mariadb:10
      ```

# Keterangan #

Custom spring security protocol:

* CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter, custom flow of authentication:
   * attemptAuthentication -> call when user request authentication, then handle with
     authenticationmanager
   * successfulAuthentication -> call when user authentication success, then return token
* CustomAuthorizationFilter extends OncePerRequestFilter, flow custom of authorization:
   * doFilterInternal -> verification token, get info from jwt token and give authorization to user

# Test API #

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
  
# ManyToMany Example #

* Many To Many Join Table "person_roles": 

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
  
# OneToMany Example #

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
