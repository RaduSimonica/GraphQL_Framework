package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;

import java.util.UUID;

public class TestDeleteSkill extends BaseClass {

    @Test
    public void testDeleteSkill() {
        GraphQLResponse response = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery("Created Test Skill " + UUID.randomUUID())
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response, Skill.class);

        GraphQLResponse deleteSkillResponse = client.sendRequest(
                Mutation.SKILL_DELETE_ONE.getQuery(createdSkill.getId())
        );
        DeleteResult deleteResult = responseProcessor.assertAndReturn(deleteSkillResponse, DeleteResult.class);

        Assert.assertEquals(deleteResult.getAffected(), 1);

        GraphQLResponse refreshedSkillResponse = client.sendRequest(
                Query.SKILL_FIND_ONE.getQuery(createdSkill.getId())
        );

        Skill refreshedSkill = responseProcessor.assertAndReturn(refreshedSkillResponse, Skill.class);

        Assert.assertNull(
                refreshedSkill,
                "Skill found after deletion"
        );

    }
}
