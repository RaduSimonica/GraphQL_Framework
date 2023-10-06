package ro.crownstudio.api.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Query {

    ROLE_FIND_ONE(
            """
                query %s {
                    RoleFindOne(id: %s) {
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
            "FindRoleById"
    ),
    ROLES(
            """
                query %s {
                    Roles {
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
            "GetAllRoles"
    ),
    SKILL_FIND_ONE(
            """
                query %s {
                    SkillFindOne(id: %s) {
                        createdAt
                        deletedAt
                        id
                        name
                        updatedAt
                    }
                }
                """,
            "FindSkillById"
    ),
    SKILLS(
            """
                query %s {
                    Skills {
                        createdAt
                        deletedAt
                        id
                        name
                        updatedAt
                    }
                }
                """,
            "GetAllSkills"
    );

    private final String queryFormat;
    private final String operationName;

    Query(String queryFormat, String operationName) {
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
