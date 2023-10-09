# This readme file is WIP.
### Local Docker Run:

Before you run: `docker build -t graphql_framework`

#### Run on Windows:
`docker run -it -v ${pwd}:/app graphql_framework sh gradlew test -DtestSuiteFile="src/test/java/suites/regression_suite.xml"`
#### Run on Linux / Mac:
TO BE TESTED!!!!
`docker run -it -v ${pwd}:/app graphql_framework sh gradlew test -DtestSuiteFile="src/test/java/suites/regression_suite.xml"`


## Planning:
- Store each and every created role or skill ID in a local JSON then create an independent script that performs a cleanup after all tests are done, just in case of catastrophic failure.
- Polish the code
- Verify and optimize imports
- Add Reporter
- Add more unit-tests
- Define more suites
- *Bonus*: If time permits, refactor Query and Mutation enum into a Factory for better scalability
- *Bonus*: If time permits, create more test scenarios. There are still a few cases uncovered.