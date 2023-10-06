package unitTests.ro.crownstudio.core;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.core.ApiClient;


import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ApiClientTest {

    @Mock
    private OkHttpClient httpClient;
    @Mock
    private Call call;
    @Mock
    private Response response;
    @Mock
    private ResponseBody responseBody;

    private GraphQLResponse graphQLResponse;
    private Gson gson;

    private ApiClient apiClient;

    @BeforeMethod
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();

        when(httpClient.newCall(any())).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(response.body()).thenReturn(responseBody);

        apiClient = new ApiClient(httpClient, gson);
    }

    @Test
    public void testSendRequestNoErrors() throws IOException {
        when(response.code()).thenReturn(200);

        when(responseBody.string()).thenReturn(
                """
                        {
                          "data": {
                            "RoleCreateOne": {
                              "id": 12,
                              "name": "name"
                            }
                          }
                        }
                        """
        );
        GraphQLResponse actualResponse = apiClient.sendRequest("someQuery");

        Assert.assertNull(actualResponse.getError());
        Assert.assertNotNull(actualResponse.getData());
        Assert.assertEquals(((LinkedTreeMap) actualResponse.getData().get("RoleCreateOne")).get("id"), 12.0);
        Assert.assertEquals(((LinkedTreeMap) actualResponse.getData().get("RoleCreateOne")).get("name"), "name");
    }

    @Test
    public void testSendRequestWrongResponseCode() throws IOException {
        when(response.code()).thenReturn(404);

        when(responseBody.string()).thenReturn(
                """
                        {
                          "data": {
                            "RoleCreateOne": {
                              "id": 12,
                              "name": "name"
                            }
                          }
                        }
                        """
        );

        Assert.assertThrows(AssertionError.class, () -> apiClient.sendRequest("someQuery"));
    }

    @Test
    public void testSendRequestGetErrors() throws IOException {
        when(response.code()).thenReturn(200);

        when(responseBody.string()).thenReturn(
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
                        """
        );
        GraphQLResponse actualResponse = apiClient.sendRequest("someQuery");

        Assert.assertNull(actualResponse.getData());
        Assert.assertNotNull(actualResponse.getError());
        Assert.assertEquals(actualResponse.getError().size(), 1);
    }
}