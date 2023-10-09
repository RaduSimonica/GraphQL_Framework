package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;

import java.util.UUID;

public class TestGetSingleDeletedSkill extends BaseClass {

    // There's a bit of confusion in this scenario. We have "deletedAt" property, that seems to not be used.
    // After a skill is deleted, it cannot be found anymore. What's the scope of "deletedAt" in this case?

    @Test
    public void testgetSingleDeletedSkill() {
        String skillName = "Created Test Skill " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillName)
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response, Skill.class);

        GraphQLResponse deleteSkillResponse = client.sendRequest(
                Mutation.SKILL_DELETE_ONE.getQuery(createdSkill.getId())
        );
        DeleteResult deleteResult = responseProcessor.assertAndReturn(deleteSkillResponse, DeleteResult.class);

        Assert.assertEquals(deleteResult.getAffected(), 1);

        GraphQLResponse getResponse = client.sendRequest(
                Query.SKILL_FIND_ONE.getQuery(createdSkill.getId())
        );

        Skill skillAfterDeletion = responseProcessor.assertAndReturn(getResponse, Skill.class);

        Assert.assertNull(
                skillAfterDeletion,
                "Skill found after deletion"
        );
    }
}
