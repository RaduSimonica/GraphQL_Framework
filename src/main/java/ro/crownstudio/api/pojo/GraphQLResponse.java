package ro.crownstudio.api.pojo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class GraphQLResponse {

    @SerializedName("data")
    private LinkedTreeMap<?, ?> data;

    @SerializedName("errors")
    private List<Error> error;

    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphQLResponse response = (GraphQLResponse) o;
        return Objects.equals(data, response.data) && Objects.equals(error, response.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, error);
    }
}
