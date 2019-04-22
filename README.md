# Ponto de venda

This is a "microservice" application intended to be part of a microservice architecture.

This application is configured for Service Discovery and Configuration with . On launch, it will refuse to start if it is not able to connect to .

## Development

To start your application in the dev profile, simply run:

    ./mvnw


## Building for production

To optimize the microservice application for production, run:

    ./mvnw -Pprod clean package

To ensure everything worked, run:

    java -jar target/*.war

## Testing

To launch your application's tests, run:

    ./mvnw clean test
    
Note: mongoDB must be up in port 27017 before running all the tests.    

## Using Docker

For example, to start a mongodb database in a docker container, run:

    docker-compose -f src/main/docker/mongodb.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mongodb.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw clean package -Pprod
    docker build -t zedelivery/pontodevenda .

Then run:

    docker-compose -f src/main/docker/app.yml up -d

## Notes from Developer

The code uses the Harvesine formula to find the distance between 2 points, so it can find the closest PDV to make the deliver.

POST - To create a new PDV the ID must be removed, I used the mongoDB as the main Database for the application, so it creates a new _id for every entry

Example of requests for the application:

GET - HealthCheck:

    curl -X GET \
      http://localhost:8080/management/health \
      -H 'cache-control: no-cache'
      
GET - All PDVs:

    curl -X GET \
      http://localhost:8080/api/pdvs \
      -H 'cache-control: no-cache'
      
POST - New PDV:

    curl -X POST \
      http://localhost:8080/api/pdvs \
      -H 'Content-Type: application/json' \
      -H 'cache-control: no-cache' \
      -d '{
              "tradingName": "Adega Osasco",
              "ownerName": "Ze da Ambev",
              "document": "02.453.716/000170",
              "coverageArea": {
                 "type": "MultiPolygon",
                 "coordinates": [
                    [
                       [
                          [
                             -43.36556,
                             -22.99669
                          ],
                          [
                             -43.36539,
                             -23.01928
                          ],
                          [
                             -43.26583,
                             -23.01802
                          ],
                          [
                             -43.25724,
                             -23.00649
                          ],
                          [
                             -43.23355,
                             -23.00127
                          ],
                          [
                             -43.2381,
                             -22.99716
                          ],
                          [
                             -43.23866,
                             -22.99649
                          ],
                          [
                             -43.24063,
                             -22.99756
                          ],
                          [
                             -43.24634,
                             -22.99736
                          ],
                          [
                             -43.24677,
                             -22.99606
                          ],
                          [
                             -43.24067,
                             -22.99381
                          ],
                          [
                             -43.24886,
                             -22.99121
                          ],
                          [
                             -43.25617,
                             -22.99456
                          ],
                          [
                             -43.25625,
                             -22.99203
                          ],
                          [
                             -43.25346,
                             -22.99065
                          ],
                          [
                             -43.29599,
                             -22.98283
                          ],
                          [
                             -43.3262,
                             -22.96481
                          ],
                          [
                             -43.33427,
                             -22.96402
                          ],
                          [
                             -43.33616,
                             -22.96829
                          ],
                          [
                             -43.342,
                             -22.98157
                          ],
                          [
                             -43.34817,
                             -22.97967
                          ],
                          [
                             -43.35142,
                             -22.98062
                          ],
                          [
                             -43.3573,
                             -22.98084
                          ],
                          [
                             -43.36522,
                             -22.98032
                          ],
                          [
                             -43.36696,
                             -22.98422
                          ],
                          [
                             -43.36717,
                             -22.98855
                          ],
                          [
                             -43.36636,
                             -22.99351
                          ],
                          [
                             -43.36556,
                             -22.99669
                          ]
                       ]
                    ]
                 ]
              },
              "address": {
                 "type": "Point",
                 "coordinates": [
                    -43.297337,
                    -23.013538
                 ]
              }
           }'
           
PUT - Change existing PDV:

    curl -X PUT \
      http://localhost:8080/api/pdvs \
      -H 'Content-Type: application/json' \
      -H 'cache-control: no-cache' \
      -d '{
    	      "id": "5cb8d25b0b9dfe81459b08a8",
              "tradingName": "Adega Osasco",
              "ownerName": "Ze da Ambev",
              "document": "02.453.716/000170",
              "coverageArea": {
                 "type": "MultiPolygon",
                 "coordinates": [
                    [
                       [
                          [
                             -43.36556,
                             -22.99669
                          ],
                          [
                             -43.36539,
                             -23.01928
                          ],
                          [
                             -43.26583,
                             -23.01802
                          ],
                          [
                             -43.25724,
                             -23.00649
                          ],
                          [
                             -43.23355,
                             -23.00127
                          ],
                          [
                             -43.2381,
                             -22.99716
                          ],
                          [
                             -43.23866,
                             -22.99649
                          ],
                          [
                             -43.24063,
                             -22.99756
                          ],
                          [
                             -43.24634,
                             -22.99736
                          ],
                          [
                             -43.24677,
                             -22.99606
                          ],
                          [
                             -43.24067,
                             -22.99381
                          ],
                          [
                             -43.24886,
                             -22.99121
                          ],
                          [
                             -43.25617,
                             -22.99456
                          ],
                          [
                             -43.25625,
                             -22.99203
                          ],
                          [
                             -43.25346,
                             -22.99065
                          ],
                          [
                             -43.29599,
                             -22.98283
                          ],
                          [
                             -43.3262,
                             -22.96481
                          ],
                          [
                             -43.33427,
                             -22.96402
                          ],
                          [
                             -43.33616,
                             -22.96829
                          ],
                          [
                             -43.342,
                             -22.98157
                          ],
                          [
                             -43.34817,
                             -22.97967
                          ],
                          [
                             -43.35142,
                             -22.98062
                          ],
                          [
                             -43.3573,
                             -22.98084
                          ],
                          [
                             -43.36522,
                             -22.98032
                          ],
                          [
                             -43.36696,
                             -22.98422
                          ],
                          [
                             -43.36717,
                             -22.98855
                          ],
                          [
                             -43.36636,
                             -22.99351
                          ],
                          [
                             -43.36556,
                             -22.99669
                          ]
                       ]
                    ]
                 ]
              },
              "address": {
                 "type": "Point",
                 "coordinates": [
                    -43.297337,
                    -23.013538
                 ]
              }
           }'
           
GET - Single PDV:

    curl -X GET \
      http://localhost:8080/api/pdvs/5cb8f5b016a48885115d9807 \
      -H 'cache-control: no-cache'
      
DELETE - Single PDV:

    curl -X DELETE \
      http://localhost:8080/api/pdvs/5cb8f19497970284dac4f46a \
      -H 'cache-control: no-cache'
      
GET - Closest PDV:

    curl -X GET \
      'http://localhost:8080/api/pdvs/closer?lat=1.45&lon=2.43' \
      -H 'cache-control: no-cache'