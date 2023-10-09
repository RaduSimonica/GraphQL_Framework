package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.UUID;

public class TestDeleteSkill extends BaseClass {

    @Test
    public void testDeleteSkill() {
        String skillName = "Created Test Skill " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillName)
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response, Skill.class);
        TestLogger.info("Created skill named: {} with id: {}", createdSkill.getName(), createdSkill.getId());

        GraphQLResponse deleteSkillResponse = client.sendRequest(
                Mutation.SKILL_DELETE_ONE.getQuery(createdSkill.getId())
        );
        DeleteResult deleteResult = responseProcessor.assertAndReturn(deleteSkillResponse, DeleteResult.class);
        TestLogger.info(
                "Deleted skill with id: {}. Entities affected: {}",
                createdSkill.getId(),
                deleteResult.getAffected()
        );
        Assert.assertEquals(
                deleteResult.getAffected(),
                1,
                "Affected entities after deletion does not match the expected one"
        );

        TestLogger.info("Test passed!");
    }
}
