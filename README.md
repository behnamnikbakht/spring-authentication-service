# UAA

This is a user authentication & authorization service implemented in Spring Boot, 
which uses [KeyCloak](https://www.keycloak.org/) to manage users and acts as a wrapper for KeyCloak to conceal complexities behind.

## Setup
1. create a Realm named "uaa"
2. create a Client named "uaa-client"
3. set client's access-type to "confidential" and save
4. in the "credentials" tab of the client's page, copy "secret" code, then put it in "uaa.keycloak.client_secret" config property.
5. set "uaa.keycloak.username", "uaa.keycloak.password" config properties equal to the value you use to login to the Keycloak.
6. run commands:
```shell script
mvn clean package
java -jar target/uaa-0.0.1.jar
```

## Usage
#### Register
```shell script
curl --location --request POST 'http://127.0.0.1:8090/api/v1/user/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "identifier": "device_id1",
    "phone_number": "989126031724"
}'
```
#### Register Verification
```shell script
curl --location --request POST 'http://127.0.0.1:8090/api/v1/user/register-verify' \
--header 'Content-Type: application/json' \
--data-raw '{
    "identifier": "device_id1",
    "pin_code": "1234"
}'
```
#### Login
```shell script
curl --location --request POST 'http://127.0.0.1:8090/api/v1/user/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "identifier": "device_id1",
    "phone_number": "989126031724"
}'
```
#### Login Verify
```shell script
curl --location --request POST 'http://127.0.0.1:8090/api/v1/user/login-verify' \
--header 'Content-Type: application/json' \
--data-raw '{
    "identifier": "device_id1",
    "pin_code": "1234"
}'
```
#### Output Token
```json
{
    "token": {
        "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJnbmg1WVZMd1dTNGt3MjFFUFU2NlZfVzdnSGZUU1VRWlZFTjg3LUJXejcwIn0.eyJleHAiOjE2MjUzMTUyNDEsImlhdCI6MTYyNTMxNDk0MSwianRpIjoiY2E0YjU5NDgtMzE2My00OGZlLWFlZmMtMmU0ODVjMDEwNTA0IiwiaXNzIjoiaHR0cDovLzEyNy4wLjAuMTo4MDgwL2F1dGgvcmVhbG1zL3VhYSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJlZDY1MDVmMC1iMmQ3LTQ2OTUtODBlZC02NGZhZDVkMTVmYjYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ1YWEtY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6ImZlZTY5NjQ5LTQ0ZmEtNDBmOC05NGZmLTkzNzQ5MDFiOWI0ZiIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsicmVhZGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkJlaG5hbSBOaWtiYWtodCIsInByZWZlcnJlZF91c2VybmFtZSI6Ijk4OTEyNjAzMTcyNCIsImdpdmVuX25hbWUiOiJCZWhuYW0iLCJmYW1pbHlfbmFtZSI6Ik5pa2Jha2h0IiwiZW1haWwiOiJpZF9tYXBwZXJAc2dhcy5pciJ9.W4HXoYsOo1Ctu8V1RCCHPvCY3JT18ad4wD7ogofy5p-d9GUsb6eYhBxvMC_dG3ulD30l9PjeFmsaBdX4FofP6WTr4efdPsahzpln1YX48LutnKjjNTV6EdLGuN4o3_224-aEZktuQ6cuVmA31ydShMahhMf9vzKecE_y9F8uSo38He65_6KfcurpnB29fKsr6p3ME18CNNIWFbhp5fJm_VcsOVQ0-LD5onBZCuIqeWTAIh7h0-2tFd8UR8NyIaHWKl3jTeNW13aBg4V8qfBWpGPB8X2n68tT6EasL2yzbNdwEhwJLQcqA0vNR-xHP9eC6jlGA0_reWh3wGsM32HVQg",
        "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3ZjhjZTBiOC05ZTMzLTRiMmYtOWY1MS1lM2UzODg3Zjk5M2YifQ.eyJleHAiOjE2MjUzMTY3NDEsImlhdCI6MTYyNTMxNDk0MSwianRpIjoiOGMwY2MwYzYtMTY1Yy00ZThkLTk3MmEtNmU1NmMxYjdhMzcwIiwiaXNzIjoiaHR0cDovLzEyNy4wLjAuMTo4MDgwL2F1dGgvcmVhbG1zL3VhYSIsImF1ZCI6Imh0dHA6Ly8xMjcuMC4wLjE6ODA4MC9hdXRoL3JlYWxtcy91YWEiLCJzdWIiOiJlZDY1MDVmMC1iMmQ3LTQ2OTUtODBlZC02NGZhZDVkMTVmYjYiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoidWFhLWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiJmZWU2OTY0OS00NGZhLTQwZjgtOTRmZi05Mzc0OTAxYjliNGYiLCJzY29wZSI6InByb2ZpbGUgZW1haWwifQ.W3wdywFbLU8e9HYJl2tNvFmNNpOgtbvYygETtw4PCvs"
    }
}
```