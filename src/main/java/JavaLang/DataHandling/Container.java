package JavaLang.DataHandling;

import lombok.Getter;

import java.util.Map;

public class Container {

    @Getter private final Map<String, String> container;

    public Container(Map<String, String> jsonContainer) {
        this.container = jsonContainer;
    }

}
