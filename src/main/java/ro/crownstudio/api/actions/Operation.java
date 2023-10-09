package ro.crownstudio.api.actions;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Operation {

    @SerializedName("query")
    private String query;

    @SerializedName("operationName")
    private String operationName;

    public Operation(String query, String operationName) {
        this.query = query;
        this.operationName = operationName;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
