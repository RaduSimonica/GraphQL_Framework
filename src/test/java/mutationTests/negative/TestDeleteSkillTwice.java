package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillCreateOne;
import ro.crownstudio.api.factory.operations.SkillDeleteOne;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.UUID;

public class TestDeleteSkillTwice extends BaseClass {

    @Test
    public void testDeleteSkill() {
        String skillName = "Created Test Skill " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillCreateOne())
                        .withArgs(skillName)
                        .asJson()
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response, Skill.class);
        TestLogger.info("Created skill named: {} with id: {}", createdSkill.getName(), createdSkill.getId());

        GraphQLResponse deleteSkillResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillDeleteOne())
                        .withArgs(createdSkill.getId())
                        .asJson()
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

        GraphQLResponse deleteSkillSecondResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillDeleteOne())
                        .withArgs(createdSkill.getId())
                        .asJson()
        );
        DeleteResult deleteSecondResult = responseProcessor.assertAndReturn(deleteSkillSecondResponse, DeleteResult.class);
        TestLogger.info(
                "Tried to deleted skill with id: {} again. Entities affected: {}",
                createdSkill.getId(),
                deleteSecondResult.getAffected()
        );
        Assert.assertEquals(
                deleteSecondResult.getAffected(),
                0,
                "Affected entities after deletion does not match the expected one"
        );

        TestLogger.info("Test passed!");
    }
}
