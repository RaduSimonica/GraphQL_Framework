package ro.crownstudio.core;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
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
                    "Found the following errors:\n" + new Gson().toJson(response.getError())
            );
        }
        LinkedTreeMap<?, ?> data = response.getData();
        String key = data.keySet().toArray()[0].toString();
        return gson.fromJson(gson.toJson(response.getData().get(key)), type);
    }
}
