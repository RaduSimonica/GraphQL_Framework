package ro.crownstudio.core;

import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleCreateOne;
import ro.crownstudio.api.factory.operations.RoleFindOne;
import ro.crownstudio.api.factory.operations.RoleSkillsOverwrite;
import ro.crownstudio.api.factory.operations.SkillCreateOne;
import ro.crownstudio.api.pojo.Asd;
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

    public List<Role> createTestRoles(int numberOfRoles) {
        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < numberOfRoles; i++) {
            roles.add(
                    RequestFactory.builder()
                            .apiClient(client)
                            .responseProcessor(responseProcessor)
                            .operation(RoleCreateOne.getInstance())
                            .withArgs("Test Role " + UUID.randomUUID())
                            .assertError()
                            .getResponseObject()
            );
        }
        return roles;
    }

    public List<Skill> createTestSkills(int NumberOfSkills) {
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < NumberOfSkills; i++) {
            skills.add(
                    RequestFactory.builder()
                            .apiClient(client)
                            .responseProcessor(responseProcessor)
                            .operation(SkillCreateOne.getInstance())
                            .withArgs("Test Skill " + UUID.randomUUID())
                            .assertError()
                            .getResponseObject()
            );
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
            RequestFactory.builder()
                    .apiClient(client)
                    .responseProcessor(responseProcessor)
                    .operation(RoleSkillsOverwrite.getInstance())
                    .withArgs(role.getId(), roleSkills)
                    .assertError()
                    .getResponseObject();
        }
        return refreshRoles(roles);
    }

    public List<Role> refreshRoles(List<Role> roles) {
        List<Role> refreshedRoles = new ArrayList<>();
        for (Role role : roles) {
            refreshedRoles.add(
                    RequestFactory.builder()
                            .apiClient(client)
                            .responseProcessor(responseProcessor)
                            .operation(RoleFindOne.getInstance())
                            .withArgs(role.getId())
                            .assertError()
                            .getResponseObject()
            );
        }
        return refreshedRoles;
    }
}
