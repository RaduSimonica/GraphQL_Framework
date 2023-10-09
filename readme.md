# GraphQL Testing framework

This is a framework for testing a GraphQL API, based on TestNG, OkHttp3 and Gson.
Uses Allure reporter to generate reports.

### Prerequisites:

- Git
- Docker (or Rancher)
- IntelliJ (optional, any other IDE works, or even the terminal)
- Java 17+ (optional)

### How to run the framework:
- Clone this repository locally in any directory using Git or IntelliJ
- Open Docker (or Rancher)
- Open a terminal in the root dir of the repository
- Build an image from Dockerfile with this command: `docker build -t graphql_framework`
- Run a test-suite with this command: 
`docker run -it -v ${pwd}:/app graphql_framework sh gradlew test test -PsuiteName="SUITE_FILE_NAME.xml"`
  - All the available suites can be found here: `src/test/java/suites`
  - Recommended suite: `regression_suite.xml`
  - Make sure to include `.xml` file extension
- Wait for the suite to complete (Should take around a minute or two)
  - A directory named `tmp-results` will be created. Leave it alone for now
  - When the suite is finished, all contents of `tmp-results` will be copied to `allure-results`
  - You can now delete `tmp-results` or it will be automatically deleted at next run.

Enjoy! :)

Oh... wait. Where's the report? :(

### View the Allure reports
The framework uses Allure to generate test-results and `fescobar`'s Allure Docker Service
to generate and vew the html reports (Thanks!).

https://github.com/fescobar/allure-docker-service

To run Allure Docker Service, simply run the following Docker container:
`docker run -p 5050:5050 -e CHECK_RESULTS_EVERY_SECONDS=5 -e KEEP_HISTORY=1 -d -v 
${PWD}/allure-results:/app/allure-results -v ${PWD}/allure-reports:/app/default-reports 
frankescobar/allure-docker-service`

It will create a web server mapped to port 5050. Make sure it's free on the machine where you are running this.

If other service needs that port, simply change it in the docker run command.
Then the container will map the `allure-results` dir (generated earlier) 
and `allure-reports` dir that will hold the reports.

The container will detach from the current terminal instance, 
so please take note of the container ID to later stop this service.

Wait a 2-3 minutes for the service to start,
then in any browser open: http://localhost:5050/allure-docker-service/projects/default/reports/latest/index.html
 to view the latest report.

You can now leave this container running and it will check every 5 seconds for new results in `allure-results` dir.
Any new run will be automatically placed in the "latest" directory and a history of 20 reports will be kept.

To stop the allure docker service container run `docker stop CONTAINER_ID`. If you forgot the container ID,
run `docker ps` and take the container id from there.


If anything goes wrong, an example report generated in 10/10/2023 [can be found here](example-allure-report/index.html)

### Don't hate, Automate!
Radu S.