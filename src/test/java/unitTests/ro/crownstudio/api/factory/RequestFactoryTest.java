package unitTests.ro.crownstudio.api.factory;

import com.google.gson.Gson;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.Operation;
import ro.crownstudio.api.factory.operations.RoleFindOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.ApiClient;
import ro.crownstudio.core.ResponseProcessor;

import java.lang.reflect.Type;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RequestFactoryTest {

    @Mock
    private ApiClient apiClient;
    @Mock
    private ResponseProcessor responseProcessor;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuild() {
        String request = RequestFactory.builder()
                .operation(RoleFindOne.getInstance())
                .withArgs(2)
                .build();

        Assert.assertEquals(
                request,
                "query FindRoleById {RoleFindOne(id: 2) { createdAt deletedAt id name skills { id weight } updatedAt }}"
        );
    }

    @Test
    public void testAsJson() {
        String request = RequestFactory.builder()
                .operation(RoleFindOne.getInstance())
                .withArgs(2)
                .asJson();

        Assert.assertEquals(
                request,
                "{\"query\":\"query FindRoleById {RoleFindOne(id: 2) { createdAt deletedAt id name skills { id weight } updatedAt }}\",\"operationName\":\"FindRoleById\"}"
        );
    }

    @Test
    public void testSendRequestNullApiClient() {
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> RequestFactory.builder()
                        .operation(RoleFindOne.getInstance())
                        .withArgs(2)
                        .sendRequest()
        );
    }

    @Test
    public void testSendRequest() {
        GraphQLResponse expectedResponse = new GraphQLResponse();
        when(apiClient.sendRequest(any())).thenReturn(expectedResponse);

        GraphQLResponse response = RequestFactory.builder()
                .apiClient(apiClient)
                .operation(RoleFindOne.getInstance())
                .withArgs(2)
                .sendRequest();

        Assert.assertEquals(response, expectedResponse);
    }

    @Test
    public void testGetResponseObjectNullApiClient() {
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> RequestFactory.builder()
                        .responseProcessor(responseProcessor)
                        .operation(RoleFindOne.getInstance())
                        .withArgs(2)
                        .assertError()
                        .getResponseObject()
        );
    }

    @Test
    public void testGetResponseObjectNullResponseProcessor() {
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> RequestFactory.builder()
                        .operation(RoleFindOne.getInstance())
                        .withArgs(2)
                        .assertError()
                        .getResponseObject()
        );
    }

    @Test
    public void testGetResponseObjectNullReturnType() {
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> RequestFactory.builder()
                        .operation(new Operation() {
                            @Override
                            public String getType() {
                                return null;
                            }

                            @Override
                            public String getName() {
                                return null;
                            }

                            @Override
                            public String getString() {
                                return null;
                            }

                            @Override
                            public Type getReturnType() {
                                return null;
                            }
                        })
                        .withArgs(2)
                        .assertError()
                        .getResponseObject()
        );
    }

    @Test
    public void testGetResponseObjectAssertError() {
        GraphQLResponse response = new GraphQLResponse();
        when(apiClient.sendRequest(anyString())).thenReturn(response);
        when(responseProcessor.assertAndReturn(any(), any())).thenThrow(new AssertionError());
        Assert.assertThrows(
                AssertionError.class,
                () -> RequestFactory.builder()
                        .apiClient(apiClient)
                        .responseProcessor(responseProcessor)
                        .operation(RoleFindOne.getInstance())
                        .withArgs(2)
                        .assertError()
                        .getResponseObject()
        );
    }

    @Test
    public void testGetResponseObjectWithoutAssertError() {
        GraphQLResponse response = new GraphQLResponse();
        Role role = new Role();
        when(apiClient.sendRequest(anyString())).thenReturn(response);
        when(responseProcessor.assertAndReturn(any(), any())).thenThrow(new AssertionError());
        when(responseProcessor.getResponseObject(any(), any())).thenReturn(role);
        Role actualRole = RequestFactory.builder()
                .apiClient(apiClient)
                .responseProcessor(responseProcessor)
                .operation(RoleFindOne.getInstance())
                .withArgs(2)
                .getResponseObject();

        Assert.assertEquals(actualRole, role);
    }
}