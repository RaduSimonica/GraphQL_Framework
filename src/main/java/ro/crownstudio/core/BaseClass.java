package ro.crownstudio.core;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;

public class BaseClass {

    private final File TEMP_RESULTS = new File("tmp-results");

    protected TestData testData;
    protected ApiClient client;
    protected ResponseProcessor responseProcessor;

    @BeforeSuite
    public void suiteSetup() {
        try {
            FileUtils.deleteDirectory(TEMP_RESULTS);
        } catch (IOException e) {
            TestLogger.error("Failed to delete %s results directory.", e);
        }
    }

    @AfterSuite
    public void teardownSuite() {
        File allureResultsDir = new File("allure-results");
        File[] resultsFiles = TEMP_RESULTS.listFiles();
        if (resultsFiles != null) {
            for (File result : resultsFiles) {
                try {
                    FileUtils.copyFileToDirectory(result, allureResultsDir);
                } catch (IOException e) {
                    TestLogger.error("Failed to copy result file: " + result);
                }
            }
        }
    }

    @BeforeMethod
    public void setup() {
        testData = TestData.getInstance();

        Gson gson = new Gson();
        client = new ApiClient(new OkHttpClient(), gson);
        responseProcessor = new ResponseProcessor(gson);
    }
}
