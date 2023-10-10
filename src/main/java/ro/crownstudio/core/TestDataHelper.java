package ro.crownstudio.core;

import org.testng.Assert;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleCreateOne;
import ro.crownstudio.api.factory.operations.RoleFindOne;
import ro.crownstudio.api.factory.operations.RoleSkillsOverwrite;
import ro.crownstudio.api.factory.operations.SkillCreateOne;
import ro.crownstudio.api.pojo.Asd;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.api.pojo.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataHelper {

    private final ApiClient client;
    private final ResponseProcessor responseProcessor;

    public TestDataHelper(ApiClient client, ResponseProcessor responseProcessor) {
        this.client = client;
        this.responseProcessor = responseProcessor;
    }

    public Role createTestRole(String roleName) {
        GraphQLResponse response = client.sendRequest(
                RequestFactory.builder()
                        .operation(new RoleCreateOne())
                        .withArgs(roleName)
                        .asJson()
        );
        return responseProcessor.assertAndReturn(response, Role.class);
    }

    public Skill createTestSkill(String skillName) {
        GraphQLResponse response = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillCreateOne())
                        .withArgs(skillName)
                        .asJson()
        );
        return responseProcessor.assertAndReturn(response, Skill.class);
    }

    public List<Role> createTestRoles(int numberOfRoles) {
        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < numberOfRoles; i++) {
            roles.add(createTestRole("Test Role " + UUID.randomUUID()));
        }
        return roles;
    }

    public List<Skill> createTestSkills(int NumberOfSkills) {
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < NumberOfSkills; i++) {
            skills.add(createTestSkill("Test Skill " + UUID.randomUUID()));
        }
        return skills;
    }

    public List<Role> assignSkillsToRoles(List<Role> roles, List<Skill> skills) {
        for (Role role : roles) {
            List<Asd> roleSkills = new ArrayList<>();
            if (skills.size() < 1) {
                break;
            }
            float weight = (float) (1.0 / skills.size());
            for (Skill skill : skills) {
                roleSkills.add(new Asd(weight, skill.getId()));
            }
            GraphQLResponse response = client.sendRequest(
                    RequestFactory.builder()
                            .operation(new RoleSkillsOverwrite())
                            .withArgs(role.getId(), roleSkills)
                            .asJson()
            );
            Assert.assertNull(response.getError(), "Cannot assign skills to role");
        }
        return refreshRoles(roles);
    }

    public List<Role> refreshRoles(List<Role> roles) {
        List<Role> refreshedRoles = new ArrayList<>();
        for (Role role : roles) {
            GraphQLResponse response = client.sendRequest(
                    RequestFactory.builder()
                            .operation(new RoleFindOne())
                            .withArgs(role.getId())
                            .asJson()
            );
            refreshedRoles.add(responseProcessor.assertAndReturn(response, Role.class));
        }
        return refreshedRoles;
    }
}
