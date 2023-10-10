package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillFindOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSingleSkillNonExistentId extends BaseClass {

    @Test
    public void testGetSingleSkillNonExistentId() {
        GraphQLResponse graphQLResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillFindOne())
                        .withArgs(Integer.MAX_VALUE)
                        .asJson()
        );
        Skill actualSkill = responseProcessor.assertAndReturn(graphQLResponse, Skill.class);
        TestLogger.info("Tried to get skill with id: {}. Result is: {}", Integer.MAX_VALUE, actualSkill);

        Assert.assertNull(actualSkill, "Found a Skill with Id " + Integer.MAX_VALUE);

        TestLogger.info("Test passed!");
    }
}
