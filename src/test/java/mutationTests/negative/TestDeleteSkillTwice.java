package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;

import java.util.UUID;

public class TestDeleteSkillTwice extends BaseClass {

    @Test
    public void testDeleteSkill() {
        String skillName = "Created Test Skill " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillName)
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response, Skill.class);

        GraphQLResponse deleteSkillResponse = client.sendRequest(
                Mutation.SKILL_DELETE_ONE.getQuery(createdSkill.getId())
        );
        DeleteResult deleteResult = responseProcessor.assertAndReturn(deleteSkillResponse, DeleteResult.class);

        Assert.assertEquals(
                deleteResult.getAffected(),
                1,
                "Affected entities after deletion does not match the expected one"
        );

        GraphQLResponse deleteSkillSecondResponse = client.sendRequest(
                Mutation.SKILL_DELETE_ONE.getQuery(createdSkill.getId())
        );
        DeleteResult deleteSecondResult = responseProcessor.assertAndReturn(deleteSkillSecondResponse, DeleteResult.class);

        Assert.assertEquals(
                deleteSecondResult.getAffected(),
                0,
                "Affected entities after deletion does not match the expected one"
        );
    }
}
