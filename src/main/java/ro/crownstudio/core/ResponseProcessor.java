package ro.crownstudio.core;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.logging.log4j.Level;
import org.testng.Assert;
import ro.crownstudio.api.pojo.GraphQLResponse;

import java.lang.reflect.Type;

public class ResponseProcessor {
    
    private final Gson gson;

    public ResponseProcessor(Gson gson) {
        this.gson = gson;
    }

    public <T> T assertAndReturn(GraphQLResponse response, Type type) {
        if (response.getError() != null) {
            Assert.assertTrue(
                    response.getError().isEmpty(),
                    "Found the following errors:\n" + response.getError()
            );
        }
        LinkedTreeMap<?, ?> data = response.getData();
        if (data == null) {
            Assert.fail("Response data from API is null, but there's no error attached.");
        }
        String key = data.keySet().toArray()[0].toString();
        return gson.fromJson(gson.toJson(response.getData().get(key)), type);
    }

    public <T> T getResponseObject(GraphQLResponse response, Type type) {
        try {
            LinkedTreeMap<?, ?> data = response.getData();
            String key = data.keySet().toArray()[0].toString();
            return gson.fromJson(gson.toJson(response.getData().get(key)), type);
        } catch (Exception e) {
            TestLogger.log(Level.WARN, "Failed to extract response object. Perhaps response contains an error?");
        }
        return null;
    }
}
