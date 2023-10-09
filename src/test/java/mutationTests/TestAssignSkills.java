package mutationTests;

import org.apache.logging.log4j.Level;
import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.*;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class TestAssignSkills extends BaseClass {

    private static final int SKILLS_TO_ASSIGN = 3;

    @Test
    public void testAssignSkills() {

        while (testData.getTestSkills().size() < SKILLS_TO_ASSIGN) {
            TestLogger.log(Level.DEBUG, "Added one more skill.");
            testData.getTestSkills()
                    .add(testData.getHelper().createTestSkill("Test Skill " + UUID.randomUUID()));
        }

        List<Skill> assignedSkills = testData.getTestSkills()
                .stream()
                .limit(SKILLS_TO_ASSIGN)
                .toList();
        TestLogger.log(Level.DEBUG, "Created a list of {} assigned skills", assignedSkills.size());

        String roleName = "Created Test Role " + UUID.randomUUID();
        GraphQLResponse createdRoleResponse = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery(roleName)
        );
        Role createdRole = responseProcessor.assertAndReturn(createdRoleResponse, Role.class);
        TestLogger.log(
                Level.INFO,
                "Created role named: {} with id: {}",
                createdRole.getName(),
                createdRole.getId()
        );

        List<Asd> roleSkillsAssigned = new ArrayList<>();
        roleSkillsAssigned.add(new Asd(0.3f, assignedSkills.get(0).getId()));
        roleSkillsAssigned.add(new Asd(0.5f, assignedSkills.get(1).getId()));
        roleSkillsAssigned.add(new Asd(0.2f, assignedSkills.get(2).getId()));
        TestLogger.log(Level.INFO, "Created ASD object. Wights and ids: {}", roleSkillsAssigned);

        GraphQLResponse assignResponse = client.sendRequest(
                Mutation.ROLE_SKILLS_OVERWRITE.getQuery(createdRole.getId(), roleSkillsAssigned)
        );
        List<RoleSkill> actualRoleSkills = Arrays.asList(
                responseProcessor.assertAndReturn(assignResponse, RoleSkill[].class)
        );
        TestLogger.log(Level.INFO, "Assigned {} skills to role id: {}", actualRoleSkills.size(), createdRole.getId());

        for (RoleSkill roleSkill : actualRoleSkills) {
            TestLogger.log(Level.INFO, "Checking role skill: {}", roleSkill.getSkillId());
            Assert.assertTrue(
                    roleSkill.getCreatedAt().before(Date.from(Instant.now())),
                    "RoleSkill has creation date later than now"
            );
            Assert.assertNull(
                    roleSkill.getDeletedAt(),
                    "RoleSkill has deletion date"
            );
            Assert.assertTrue(
                    roleSkill.getId() > 0,
                    "RoleSkill ID is 0"
            );
            Assert.assertEquals(
                    roleSkill.getRoleId(),
                    createdRole.getId(),
                    "RoleSkill role id does not match the expected"
            );
            Assert.assertEquals(
                    roleSkill.getSkill().getId(),
                    roleSkill.getSkillId(),
                    "Skill id and skill's id does not match"
            );
            Assert.assertTrue(
                    assignedSkills.contains(roleSkill.getSkill()),
                    "Skill is not one of the expected"
            );
            Assert.assertTrue(
                    roleSkillsAssigned.contains(new Asd(roleSkill.getWeight(), roleSkill.getSkillId())),
                    "Weight and skill ID does not match the expected"
            );
        }

        TestLogger.log(Level.INFO, "Test passed!");
    }
}
