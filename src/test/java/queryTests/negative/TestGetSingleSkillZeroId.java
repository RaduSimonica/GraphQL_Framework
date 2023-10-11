package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillFindOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSingleSkillZeroId extends BaseClass {

    @Test
    public void testGetSingleSkillZeroId() {
        GraphQLResponse response = RequestFactory.builder()
                .apiClient(client)
                .operation(SkillFindOne.getInstance())
                .withArgs(0)
                .sendRequest();

        TestLogger.info("Tried to get skill with id: 0");

        Assert.assertNotNull(
                response.getError(),
                "No error was returned while trying to get a skill with id 0"
        );
        Assert.assertFalse(
                response.getError().isEmpty(),
                "No error was returned while trying to get a skill with id 0"
        );
        Assert.assertEquals(
                response.getError().get(0).getMessage(),
                "runtime error: invalid memory address or nil pointer dereference"
        );

        TestLogger.info("Test passed!");
    }
}
