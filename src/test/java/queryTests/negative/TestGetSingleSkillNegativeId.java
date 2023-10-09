package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;

public class TestGetSingleSkillNegativeId extends BaseClass {

    @Test
    public void testGetSingleSkillNegativeId() {
        GraphQLResponse graphQLResponse = client.sendRequest(Query.SKILL_FIND_ONE.getQuery(-20));
        Skill actualSkill = responseProcessor.assertAndReturn(graphQLResponse, Skill.class);

        Assert.assertNull(actualSkill, "Found a skill with Id negative 20 (-20)");
    }
}
