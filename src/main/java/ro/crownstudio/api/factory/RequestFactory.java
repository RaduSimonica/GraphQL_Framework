package ro.crownstudio.api.factory;

import ro.crownstudio.api.factory.operations.Operation;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.core.ApiClient;
import ro.crownstudio.core.ResponseProcessor;


public class RequestFactory {

    private ApiClient apiClient;
    private ResponseProcessor responseProcessor;
    private Operation operation;
    private Object[] args;
    private boolean assertError;

    private RequestFactory() {
        assertError = false;
    }

    public static RequestFactory builder() {
        return new RequestFactory();
    }

    public RequestFactory apiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
        return this;
    }

    public RequestFactory responseProcessor(ResponseProcessor responseProcessor) {
        this.responseProcessor = responseProcessor;
        return this;
    }

    public RequestFactory operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public RequestFactory withArgs(Object... args) {
        this.args = args;
        return this;
    }

    public RequestFactory assertError() {
        assertError = true;
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

    public GraphQLResponse sendRequest() {
        if (apiClient == null) {
            throw new IllegalArgumentException("Missing ApiClient!");
        }
        return apiClient.sendRequest(asJson());
    }

    public <T> T getResponseObject() {
        if (operation.getReturnType() == null) {
            throw new IllegalArgumentException("Missing 'returns' type");
        }
        if (responseProcessor == null) {
            throw new IllegalArgumentException("Missing responseProcessor");
        }

        GraphQLResponse response = sendRequest();

        if (assertError) {
            return responseProcessor.assertAndReturn(response, operation.getReturnType());
        }
        return responseProcessor.getResponseObject(response, operation.getReturnType());
    }
}
