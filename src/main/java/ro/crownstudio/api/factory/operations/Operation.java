package ro.crownstudio.api.factory.operations;

import java.lang.reflect.Type;

public interface Operation {

    String getType();
    String getName();
    String getString();
    Type getReturnType();
}
