package JavaLang.DataHandling;

import JavaLang.Exceptions.JsonNotFoundException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Scanner;

public class JsonHandler {
    private final String resourcePath;

    public JsonHandler(String root, Languages locale) {
        this.resourcePath = new StringBuilder()
            .append("/").append(root)
            .append("/").append(locale.toString())
            .append(".json")
            .toString();
    }

    public JsonHandler(String path) {
        this.resourcePath = new StringBuilder()
            .append("/")
            .append(path).toString();
    }

    @SneakyThrows
    public Container getContainerFromJson() {
        String json = getJson();

        Type containerType = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> objectJson = new Gson().fromJson(json, containerType);

        return new Container(objectJson);
    }

    @NonNull
    private String getJson() throws JsonNotFoundException {
        InputStream json = getClass().getResourceAsStream(resourcePath);
        checkJsonExistence(json);

        return readJsonFromInstance(json);
    }

    @NonNull
    private static String readJsonFromInstance(InputStream json) {
        Scanner scanner = new Scanner(json);
        StringBuilder output = new StringBuilder();

        while (scanner.hasNext()) output.append(scanner.nextLine());

        return output.toString();
    }

    private void checkJsonExistence(InputStream json) throws JsonNotFoundException {
        if (json == null) throw new JsonNotFoundException(
            new StringBuilder()
                    .append("No locale Json file was found at the path: \"")
                    .append(resourcePath)
                    .append("\" in resources folder!\nCheck the root and locale name matching")
                    .toString()
        );
    }
}
