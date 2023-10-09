package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class TestCreateSkillSpecialCharName extends BaseClass {

    @Test
    public void testCreateSkillSpecialCharName() {
        String skillName = "ЊѼҊ߮ " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillName)
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response, Skill.class);
        TestLogger.info("Created skill named: {} with id: {}", createdSkill.getName(), createdSkill.getId());

        Assert.assertTrue(
                createdSkill.getCreatedAt().before(Date.from(Instant.now())),
                "Skill creation date is not earlier than now"
        );
        Assert.assertNull(
                createdSkill.getDeletedAt(),
                "Skill has deletion date"
        );
        Assert.assertTrue(
                createdSkill.getId() > 0,
                "Skill Id is less or equal to 0"
        );
        Assert.assertEquals(
                createdSkill.getName(),
                skillName,
                "Skill name does not match the expected name"
        );
        Assert.assertTrue(
                createdSkill.getUpdatedAt().before(Date.from(Instant.now())),
                "Skill updated date is not earlier than now"
        );

        TestLogger.info("Test passed!");
    }
}
