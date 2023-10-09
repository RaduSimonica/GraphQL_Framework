package unitTests;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ro.crownstudio.core.TestLogger;

import java.io.File;
import java.io.IOException;

public class BaseUnitTestClass {

    private final File TEMP_RESULTS = new File("tmp-results");

    @BeforeSuite
    public void suiteSetup() {
        try {
            FileUtils.deleteDirectory(TEMP_RESULTS);
        } catch (IOException e) {
            TestLogger.error("Failed to delete %s results directory.");
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
}
