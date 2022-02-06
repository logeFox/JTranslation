//    MIT License
//
//    Copyright (c) 2021 Alessandro <https://github.com/logeFox>
//
//    Permission is hereby granted, free of charge, to any person obtaining a copy
//    of this software and associated documentation files (the "Software"), to deal
//    in the Software without restriction, including without limitation the rights
//    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//    copies of the Software, and to permit persons to whom the Software is
//    furnished to do so, subject to the following conditions:
//
//    The above copyright notice and this permission notice shall be included in all
//    copies or substantial portions of the Software.
//
//    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//    SOFTWARE.

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

// TODO --- Upgrade JsonHandler

public class JsonHandler {
    private final String resourcePath;

    public JsonHandler(String root, String locale) {
        this.resourcePath = new StringBuilder()
            .append("/").append(root)
            .append("/").append(locale.toString())
            .append(".json")
            .toString();
    }

    protected JsonHandler(String path) {
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
