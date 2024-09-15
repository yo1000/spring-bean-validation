Spring Bean Validation demo
================================================================================

Demonstration of Bean Validation.


Requirements
--------------------------------------------------------------------------------

- Java 21


How to run
--------------------------------------------------------------------------------

```bash
./mvnw clean spring-boot:run
```


Demo
--------------------------------------------------------------------------------

### Handle MethodArgumentNotValidException

Invalid Request Body

```bash
curl -XPOST \
-H'Content-Type: application/json' \
localhost:8080/members -d '{
  "givenName": "Squall",
  "familyName": "Leonhart",
  "email": "invalid-email",
  "address": {
    "addressLevel4": "1-8-1",
    "addressLevel3": "Shimo-meguro",
    "addressLevel2": "Meguro-ku",
    "addressLevel1": "Tokyo-to",
    "country": "INVALID-COUNTRY",
    "countryName": "Japan",
    "postalCode": "153-8688"
  }
}'
```


### Handle ConstraintViolationException

Invalid PathVariable

```bash
curl -XGET \
-H'Content-Type: application/json' \
localhost:8080/members/invalid-path-variable
```

Invalid RequestParameter

```bash
curl -XGET \
-H'Content-Type: application/json' \
localhost:8080/members?email=invalid-email
```

Invalid Request Header

```bash
curl -XPOST \
-H'Content-Type: application/json' \
-H'Cache-Control: invalid-cache-request' \
localhost:8080/members -d '{
  "givenName": "Squall",
  "familyName": "Leonhart",
  "email": "squall-leonhart@balamb.ac.localhost",
  "address": {
    "addressLevel4": "1-8-1",
    "addressLevel3": "Shimo-meguro",
    "addressLevel2": "Meguro-ku",
    "addressLevel1": "Tokyo-to",
    "country": "JP",
    "countryName": "Japan",
    "postalCode": "153-8688"
  }
}'
```
