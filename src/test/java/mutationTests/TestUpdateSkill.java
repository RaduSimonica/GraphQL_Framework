package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class TestUpdateSkill extends BaseClass {

    @Test
    public void testUpdateSkill() {
        String skillName = "Created Test Skill " + UUID.randomUUID();
        String updatedSkillname = "Updated Test Skill " + UUID.randomUUID();

        GraphQLResponse createdSkillResponse = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillName)
        );
        Skill createdSkill = responseProcessor.assertAndReturn(createdSkillResponse, Skill.class);
        TestLogger.info("Created skill named: {} with id: {}", createdSkill.getName(), createdSkill.getId());

        GraphQLResponse updatedSkillResponse = client.sendRequest(
                Mutation.SKILL_UPDATE_ONE.getQuery(createdSkill.getId(), updatedSkillname)
        );
        Skill updatedSkill = responseProcessor.assertAndReturn(updatedSkillResponse, Skill.class);
        TestLogger.info("Updated skill id: {}. New name is: {}", createdSkill.getId(), updatedSkill.getName());

        GraphQLResponse refreshedSkillResponse = client.sendRequest(
                Query.SKILL_FIND_ONE.getQuery(createdSkill.getId())
        );
        Skill refreshedSkill = responseProcessor.assertAndReturn(refreshedSkillResponse, Skill.class);

        Assert.assertEquals(
                updatedSkill.getCreatedAt(),
                createdSkill.getCreatedAt(),
                "Creation date changed after update"
        );
        Assert.assertNull(
                updatedSkill.getDeletedAt(),
                "Role has deleted date after update"
        );
        Assert.assertEquals(
                updatedSkill.getId(),
                createdSkill.getId(),
                "Role id changed after update"
        );
        Assert.assertEquals(
                updatedSkill.getName(),
                updatedSkillname,
                "Skill name did not change after update"
        );
        Assert.assertTrue(
                updatedSkill.getUpdatedAt().before(Date.from(Instant.now())),
                "Skill updated date is not before now"
        );
        Assert.assertTrue(
                updatedSkill.getUpdatedAt().after(updatedSkill.getCreatedAt()),
                "Skill updated date is not after created date"
        );

        // Bonus: Check if getting the skull by ID after update, equals the skull provided by the update mutation
        Assert.assertEquals(
                refreshedSkill,
                updatedSkill,
                "Skill after update does not match skill provided by update mutation"
        );

        TestLogger.info("Test passed!");
    }
}
