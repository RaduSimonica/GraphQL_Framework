package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;

public class TestGetSingleSkillZeroId extends BaseClass {

    @Test
    public void testGetSingleSkillZeroId() {
        GraphQLResponse graphQLResponse = client.sendRequest(Query.SKILL_FIND_ONE.getQuery(0));
        Skill actualSkill = responseProcessor.assertAndReturn(graphQLResponse, Skill.class);

        Assert.assertNull(actualSkill, "Found a Skill with Id zero (0)");
    }
}
