package ro.crownstudio.api.factory;

import ro.crownstudio.api.factory.operations.Operation;

public class RequestFactory {

    private Operation operation;
    private Object[] args;

    private RequestFactory() {}

    public static RequestFactory builder() {
        return new RequestFactory();
    }

    public RequestFactory operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public RequestFactory withArgs(Object... args) {
        this.args = args;
        return this;
    }

    public String build() {
        return "%s %s {%s}".formatted(
                operation.getType(),
                operation.getName(),
                operation.getString().formatted(args))
                .replace("\"", "\\\"");
    }

    public String asJson() {
        return "{\"query\":\"%s\",\"operationName\":\"%s\"}".formatted(build(), operation.getName());
    }
}
