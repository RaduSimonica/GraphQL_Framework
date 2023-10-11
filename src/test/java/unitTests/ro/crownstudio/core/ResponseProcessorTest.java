package unitTests.ro.crownstudio.core;

import com.google.gson.Gson;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.ResponseProcessor;
import unitTests.BaseUnitTestClass;


public class ResponseProcessorTest extends BaseUnitTestClass {

    private Gson gson;
    private ResponseProcessor responseProcessor;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();
        responseProcessor = new ResponseProcessor(gson);
    }

    @Test
    public void testAssertAndReturnRoleNoErrors() {
        GraphQLResponse response = gson.fromJson(
                """
                        {
                          "data": {
                            "RoleCreateOne": {
                              "id": 100,
                              "name": "name"
                            }
                          }
                        }
                        """,
                GraphQLResponse.class
        );

        Role role = responseProcessor.assertAndReturn(response, Role.class);

        Assert.assertEquals(role.getId(), 100);
        Assert.assertEquals(role.getName(), "name");
    }

    @Test
    public void testAssertAndReturnRoleWithError() {
        GraphQLResponse response = gson.fromJson(
                """
                        {
                          "data": null,
                          "errors": [
                            {
                              "message": "error",
                              "locations": [
                                {
                                  "line": 2,
                                  "column": 23
                                }
                              ]
                            }
                          ]
                        }
                        """,
                GraphQLResponse.class
        );

        Assert.assertThrows(AssertionError.class,() -> responseProcessor.assertAndReturn(response, Role.class));
    }

    @Test
    public void testAssertAndReturnRoleNoErrorNoData() {
        GraphQLResponse response = gson.fromJson(
                """
                        {
                          "data": null,
                          "errors": null
                        }
                        """,
                GraphQLResponse.class
        );

        Assert.assertThrows(AssertionError.class,() -> responseProcessor.assertAndReturn(response, Role.class));
    }

    @Test
    public void testGetResponseObject() {
        GraphQLResponse response = gson.fromJson(
                """
                        {
                          "data": {
                            "RoleCreateOne": {
                              "id": 100,
                              "name": "name"
                            }
                          }
                        }
                        """,
                GraphQLResponse.class
        );

        Role role = responseProcessor.getResponseObject(response, Role.class);

        Assert.assertEquals(role.getId(), 100);
        Assert.assertEquals(role.getName(), "name");
    }

    @Test
    public void testGetResponseObjectWithError() {
        GraphQLResponse response = gson.fromJson(
                """
                        {
                          "data": null,
                          "errors": [
                            {
                              "message": "error",
                              "locations": [
                                {
                                  "line": 2,
                                  "column": 23
                                }
                              ]
                            }
                          ]
                        }
                        """,
                GraphQLResponse.class
        );

        Role role = responseProcessor.getResponseObject(response, Role.class);

        Assert.assertNull(role);
    }
}