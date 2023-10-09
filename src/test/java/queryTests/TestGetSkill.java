package queryTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSkill extends BaseClass {

    @Test
    public void testGetSingleSkill() {
        Skill expectedSkill = testData.getTestSkills().get(0);
        GraphQLResponse graphQLResponse = client.sendRequest(Query.SKILL_FIND_ONE.getQuery(expectedSkill.getId()));
        Skill actualSkill = responseProcessor.assertAndReturn(graphQLResponse, Skill.class);
        TestLogger.info("Got skill named: {}", actualSkill.getName());

        Assert.assertEquals(
                actualSkill,
                expectedSkill,
                "Actual skill does not match the expected role"
        );

        TestLogger.info("Test passed!");
    }
}
