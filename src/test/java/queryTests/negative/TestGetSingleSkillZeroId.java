package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillFindOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSingleSkillZeroId extends BaseClass {

    @Test
    public void testGetSingleSkillZeroId() {
        GraphQLResponse graphQLResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillFindOne())
                        .withArgs(0)
                        .asJson()
        );
        Skill actualSkill = responseProcessor.assertAndReturn(graphQLResponse, Skill.class);
        TestLogger.info("Tried to get skill with id: 0. Result is {}", actualSkill);

        Assert.assertNull(actualSkill, "Found a Skill with Id zero (0)");

        TestLogger.info("Test passed!");
    }
}
