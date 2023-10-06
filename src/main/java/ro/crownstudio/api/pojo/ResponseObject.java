package ro.crownstudio.api.pojo;

import com.google.gson.Gson;

public class ResponseObject {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
