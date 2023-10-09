### Local Docker Run:

Before you run: `docker build -t graphql_framework`

#### Run on Windows:
`docker run -it -v ${pwd}:/app graphql_framework sh gradlew test -DtestSuiteFile="src/test/java/suites/regression_suite.xml"`
#### Run on Linux / Mac:
TO BE TESTED!!!!
`docker run -it -v ${pwd}:/app graphql_framework sh gradlew test -DtestSuiteFile="src/test/java/suites/regression_suite.xml"`