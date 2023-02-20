

## To run
### IntelliJ IDEA
*Run > Run 'EcommerceApplication'*
### Command Line
```shell
./mvnw clean spring-boot:run
```

## Making requests
This application exposes a simple REST API. After starting the Spring Application locally,
requests can be made to the port on which Tomcat has initialized.
By default, this should be `localhost:8080`.


### Examples: `curl`
*Note: All requests will be piped through `json_pp`
to make the output more readable.*
#### Create
+ [Create new customer](#create-new-customer)
#### Read
+ [Get all customers](#get-all-customers)
+ [Get customer by ID](#get-customer-by-id)
#### Update
+ [Replace customer by ID](#replace-customer-by-id)
#### Delete
+ [Delete customer by ID](#delete-customer-by-id)

#### Create new customer
##### Request
```shell
# /customers {"name":"", "address":""}
curl -X POST localhost:8080/customers -H \
        'Content-type:application/json' -d \
        '{"name": "Sir Buysmore", "address": "1 Shopalot Place"}'
```
##### Response
```json
{
   "address" : "1 Shopalot Place",
   "id" : 3,
   "name" : "Sir Buysmore"
}
```
[Go back to examples](#examples--curl)

#### Get all customers
##### Request
```shell
# /customers
curl -X GET localhost:8080/customers
```
##### Response
```json
{
   "_embedded" : {
      "customerList" : [
         {
            "_links" : {
               "customers" : {
                  "href" : "http://localhost:8080/customers"
               },
               "self" : {
                  "href" : "http://localhost:8080/customers/1"
               }
            },
            "address" : "123 Address Road",
            "id" : 1,
            "name" : "John Smith"
         },
         {
            "_links" : {
               "customers" : {
                  "href" : "http://localhost:8080/customers"
               },
               "self" : {
                  "href" : "http://localhost:8080/customers/2"
               }
            },
            "address" : "500 Street Avenue",
            "id" : 2,
            "name" : "Millan McGaelan"
         }
      ]
   },
   "_links" : {
      "self" : {
         "href" : "http://localhost:8080/customers"
      }
   }
}
```
[Go back to examples](#examples--curl)

#### Get customer by ID
##### Request
```shell
# /customers/{id}
curl -X GET localhost:8080/customers/1
```
##### Response
```json
{
   "_links" : {
      "customers" : {
         "href" : "http://localhost:8080/customers"
      },
      "self" : {
         "href" : "http://localhost:8080/customers/1"
      }
   },
   "address" : "123 Address Road",
   "id" : 1,
   "name" : "John Smith"
}
```
[Go back to examples](#examples--curl)

#### Get Customer by ID
##### Request
```shell
# /customers/{id}
curl localhost:8080/customers/1
```
##### Response
```json
{
   "_links" : {
      "customers" : {
         "href" : "http://localhost:8080/customers"
      },
      "self" : {
         "href" : "http://localhost:8080/customers/1"
      }
   },
   "address" : "123 Address Road",
   "id" : 1,
   "name" : "John Smith"
}
```
[Go back to examples](#examples--curl)

#### Replace customer by ID
##### Request
```shell
# /customers/{id}
curl -X PUT localhost:8080/customers/1 -H \
        'Content-type:application/json' -d \
        '{"name":"John Smith", "address":"456 New Address Boulevard"}'
```
##### Response
```json
{
  "address" : "456 New Address Boulevard",
  "id" : 1,
  "name" : "John Smith"
}
```
[Go back to examples](#examples--curl)

#### Delete customer by ID
##### Request
```shell
# /customers/{id}
curl -X DELETE localhost:8080/customers/1
```
##### Response
Deletetion will return an `HTTP 204 No Content` on success.

[Go back to examples](#examples--curl)
