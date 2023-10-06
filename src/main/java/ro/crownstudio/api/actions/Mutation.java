package ro.crownstudio.api.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Mutation {

    ROLE_CREATE_ONE(
            """
                mutation %s {
                    RoleCreateOne(name: "%s") {
                        createdAt
                        deletedAt
                        id
                        name
                        skills {
                            id
                            weight
                        }
                        updatedAt
                    }
                }
                """,
            "CreateRole"
    ),
    ROLE_DELETE_ONE(
            """
                mutation %s {
                    RoleDeleteOne(id: %s) {
                        affected
                    }
                }
                """,
            "DeleteRoleById"
    ),
    ROLE_SKILLS_OVERWRITE(
            """
                    mutation %s {
                        RoleSkillsOverwrite(roleId: %s, skills: %s) {
                            createdAt
                            deletedAt
                            roleId
                            id
                            skill {
                                id
                                name
                            }
                            updatedAt
                        }
                    }
                    """,
            "OverwriteRoleSkills"
    ),
    ROLE_UPDATE_ONE(
            """
                mutation %s {
                    RoleUpdateOne(id: %s, name: "%s") {
                        createdAt
                        deletedAt
                        id
                        name
                        skills {
                            id
                            weight
                        }
                        updatedAt
                    }
                }
                """,
            "UpdateRoleById"
    ),
    SKILL_CREATE_ONE(
            """
                    mutation %s {
                        SkillCreateOne(name: "%s") {
                            createdAt
                            deletedAt
                            id
                            name
                            updatedAt
                        }
                    }
                    """,
            "CreateSkill"
    ),
    SKILL_DELETE_ONE(
            """
                mutation %s {
                    SkillDeleteOne(id: %s) {
                        affected
                    }
                }
                """,
            "DeleteSkillById"
    ),
    SKILL_UPDATE_ONE(
            """
                mutation %s {
                    SkillUpdateOne(id: %s, name: "%s") {
                        createdAt
                        deletedAt
                        id
                        name
                        updatedAt
                    }
                }
                """,
            "UpdateSkillById"
    );

    private final String queryFormat;
    private final String operationName;

    Mutation(String queryFormat, String operationName) {
        this.queryFormat = queryFormat;
        this.operationName = operationName;
    }

    private Operation getOperation(Object... args) {
        List<String> arguments = Arrays.stream(args)
                .map(Object::toString)
                .collect(Collectors.toCollection(ArrayList::new));
        arguments.add(0, operationName);
        return new Operation(queryFormat.formatted(arguments.toArray()), operationName);
    }

    public String getQuery(Object... args) {
        return getOperation(args).toString();
    }
}
