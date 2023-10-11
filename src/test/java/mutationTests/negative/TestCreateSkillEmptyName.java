package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillCreateOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestCreateSkillEmptyName extends BaseClass {

    @Test
    public void testCreateSkillEmptyName() {
        String skillName = "";
        GraphQLResponse response = RequestFactory.builder()
                .apiClient(client)
                .operation(SkillCreateOne.getInstance())
                .withArgs(skillName)
                .sendRequest();

        TestLogger.info("Tried to create a skill with empty name");

        Assert.assertNotNull(
                response.getError(),
                "No error was return while trying to create a skill with empty name"
        );
        Assert.assertFalse(
                response.getError().isEmpty(),
                "No error was return while trying to create a skill with empty name"
        );

        // The current error message: "an error occurred" is too generic.
        Assert.assertEquals(
                response.getError().get(0).getMessage(),
                "Cannot create a skill with empty name."
        );

        TestLogger.info("Test passed!");
    }
}
