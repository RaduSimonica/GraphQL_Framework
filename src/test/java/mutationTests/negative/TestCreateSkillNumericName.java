package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class TestCreateSkillNumericName extends BaseClass {

    @Test
    public void testCreateSkillNumericName() {
        String skillName = UUID.randomUUID() + " 1234567890";
        GraphQLResponse response = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillName)
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response, Skill.class);

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
    }
}
