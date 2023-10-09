# This readme file is WIP.
### Local Docker Run:

Before you run: `docker build -t graphql_framework`

#### Run on Windows:
`docker run -it -v ${pwd}:/app graphql_framework sh gradlew test test -PsuiteName="smoke_suite.xml"`
#### Run on Linux / Mac:
TO BE TESTED!!!!
`docker run -it -v ${pwd}:/app graphql_framework sh gradlew test test -PsuiteName="smoke_suite.xml"`

#### Reports:
`docker run -p 5050:5050 -e CHECK_RESULTS_EVERY_SECONDS=60 -e KEEP_HISTORY=1 -d -v ${PWD}/allure-results:/app/allure-results -v ${PWD}/allure-reports:/app/default-reports frankescobar/allure-docker-service`

Then go to: http://localhost:5050/allure-docker-service/projects/default/reports/latest/index.html

## Planning:
- Write documentation
- Store each and every created role or skill ID in a local JSON then create an independent script that performs a cleanup after all tests are done, just in case of catastrophic failure.
- Polish the code
- Add more unit-tests


- *Bonus*: If time permits, refactor Query and Mutation enum into a Factory for better scalability
- *Bonus*: If time permits, create more test scenarios. There are still a few cases uncovered.