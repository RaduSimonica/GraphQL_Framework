package ro.crownstudio.core;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.testng.annotations.BeforeMethod;

public class BaseClass {

    protected TestData testData;
    protected ApiClient client;
    protected ResponseProcessor responseProcessor;

    @BeforeMethod
    public void setup() {
        testData = TestData.getInstance();

        Gson gson = new Gson();
        client = new ApiClient(new OkHttpClient(), gson);
        responseProcessor = new ResponseProcessor(gson);
    }
}
