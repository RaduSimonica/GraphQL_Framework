package ro.crownstudio.core;


import com.google.gson.Gson;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.config.MainConfig;

import java.io.IOException;

public class ApiClient {

    private final MainConfig CONFIG = MainConfig.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ApiClient.class);

    private final OkHttpClient httpClient;
    private final Gson gson;

    public ApiClient(OkHttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public GraphQLResponse sendRequest(String query) {
        LOGGER.trace(query);
        Request request = new Request.Builder()
                .url(CONFIG.getApiUrl())
                .post(RequestBody.create(query, MediaType.parse("application/json; charset=utf-8")))
                .build();

        Call call = httpClient.newCall(request);

        try (Response response = call.execute()) {
            Assert.assertEquals(
                    response.code(),
                    200,
                    "Response for query: '%s' returned unexpected code.".formatted(query)
            );

            if (response.body() != null) {
                String responseString = response.body().string();
                LOGGER.trace("Response from api: {}", responseString);

                return gson.fromJson(responseString, GraphQLResponse.class);
            }

            LOGGER.debug("Response body for query: {} is null.", query);
        } catch (IOException e) {
            Assert.fail("Failed to execute api call for query: %s".formatted(query), e);
        }

        return null;
    }
}
