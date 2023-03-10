# ecommerce
A hypermedia-driven REST back end for an e-commerce web application.
Supports the creation, reading, updating and deletion of customers, orders, products.
Uses [Spring Boot](https://spring.io/web-applications),
[Jakarta Persistence](https://jakarta.ee/specifications/persistence/3.0/), 
[H2 Database](https://www.h2database.com/html/main.html) and [Spring HATEOS](https://spring.io/guides/gs/rest-hateoas/).
Built with [Maven](https://maven.apache.org/what-is-maven.html).

## To run
### IntelliJ IDEA
In the menu bar, navigate to `Run` > `Run 'EcommerceApplication'`
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
+ [Place new order](#place-new-order)
#### Read
+ [Get all customers](#get-all-customers)
+ [Get customer by ID](#get-customer-by-id)
+ [Get all orders](#get-all-orders)
+ [Get order by ID](#get-order-by-id)
#### Update
+ [Replace customer by ID](#replace-customer-by-id)
+ [Complete order by ID](#complete-order-by-id)
#### Delete
+ [Delete customer by ID](#delete-customer-by-id)
+ [Cancel order by ID](#cancel-order-by-id)

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

#### Place new order
##### Request
```shell
# /orders {"description":""}
curl -X POST localhost:8080/orders -H \
        'Content-type:application/json' -d \
        '{"description": "A bunch of products"}'
```
##### Response
```json
{
  "_links" : {
    "cancel" : {
      "href" : "http://localhost:8080/orders/1/cancel"
    },
    "complete" : {
      "href" : "http://localhost:8080/orders/1/complete"
    },
    "orders" : {
      "href" : "http://localhost:8080/customers"
    },
    "self" : {
      "href" : "http://localhost:8080/customers/1"
    }
  },
  "description" : "A bunch of products",
  "id" : 1,
  "status" : "IN_PROGRESS"
}
```
[Go back to examples](#examples--curl)

### Get all customers
#### Request
```shell
# /customers
curl -X GET localhost:8080/customers
```
#### Response
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

### Get customer by ID
#### Request
```shell
# /customers/{id}
curl -X GET localhost:8080/customers/1
```
#### Response
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

### Get Customer by ID
#### Request
```shell
# /customers/{id}
curl localhost:8080/customers/1
```
#### Response
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

### Replace customer by ID
#### Request
```shell
# /customers/{id}
curl -X PUT localhost:8080/customers/1 -H \
        'Content-type:application/json' -d \
        '{"name":"John Smith", "address":"456 New Address Boulevard"}'
```
#### Response
```json
{
  "address" : "456 New Address Boulevard",
  "id" : 1,
  "name" : "John Smith"
}
```
[Go back to examples](#examples--curl)

### Delete customer by ID
#### Request
```shell
# /customers/{id}
curl -X DELETE localhost:8080/customers/1
```
#### Response
Deletion will return an `HTTP 204 No Content` on success.

[Go back to examples](#examples--curl)
