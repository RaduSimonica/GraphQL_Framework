package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;

public class TestGetSingleSkillNonExistentId extends BaseClass {

    @Test
    public void testGetSingleSkillNonExistentId() {
        GraphQLResponse graphQLResponse = client.sendRequest(Query.SKILL_FIND_ONE.getQuery(Integer.MAX_VALUE));
        Skill actualSkill = responseProcessor.assertAndReturn(graphQLResponse, Skill.class);

        Assert.assertNull(actualSkill, "Found a Skill with Id " + Integer.MAX_VALUE);
    }
}
