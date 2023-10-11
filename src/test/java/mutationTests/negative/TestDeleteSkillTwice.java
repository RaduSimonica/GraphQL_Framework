package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillCreateOne;
import ro.crownstudio.api.factory.operations.SkillDeleteOne;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.UUID;

public class TestDeleteSkillTwice extends BaseClass {

    @Test
    public void testDeleteSkill() {
        String skillName = "Created Test Skill " + UUID.randomUUID();
        Skill createdSkill = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(SkillCreateOne.getInstance())
                .withArgs(skillName)
                .assertError()
                .getResponseObject();

        TestLogger.info("Created skill named: {} with id: {}", createdSkill.getName(), createdSkill.getId());

        DeleteResult deleteResult = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(SkillDeleteOne.getInstance())
                .withArgs(createdSkill.getId())
                .assertError()
                .getResponseObject();

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

        DeleteResult deleteSecondResult = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(SkillDeleteOne.getInstance())
                .withArgs(createdSkill.getId())
                .assertError()
                .getResponseObject();

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
