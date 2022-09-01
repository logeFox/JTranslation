package JTranslation;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.Arrays;

import JTranslation.DataHandling.Languages;


public class JTranslationBuilder {
    private String root = "translations";
    private final String[] locales;

    @SneakyThrows
    public JTranslationBuilder(@NonNull String... locales) {
        for (String locale : locales) {
            JTranslation.checkLocaleValidity(locale);
        }

        this.locales = locales;
    }

    public JTranslationBuilder(@NonNull Languages... locales) {
        this.locales = Arrays.stream(locales).map(Languages::name).toArray(String[]::new);
    }

    public JTranslationBuilder setRoot(String root) {
        this.root = root;
        return this;
    }

    @SneakyThrows
    public JTranslation build() {
        return new JTranslation(root, locales);
    }
}
