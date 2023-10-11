package queryTests;

import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.Skills;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import static org.assertj.core.api.Assertions.assertThat;


public class TestGetAllSkills extends BaseClass {

    @Test
    public void testGetAllSkills() {
        Skill[] actualSkills = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(Skills.getInstance())
                .assertError()
                .getResponseObject();

        TestLogger.info("Got {} skills", actualSkills.length);

        assertThat(actualSkills)
                .withFailMessage("Not all expected roles could be found")
                .containsAll(testData.getTestSkills());

        TestLogger.info("Test passed!");
    }
}
