package JavaLang;


import JavaLang.DataHandling.Container;
import JavaLang.DataHandling.Emojis;
import JavaLang.DataHandling.JsonHandler;
import JavaLang.DataHandling.Languages;
import JavaLang.Exceptions.ArgumentsOutOfRangeException;
import JavaLang.Exceptions.BrokenRouteException;
import JavaLang.Exceptions.KeyNotFoundException;
import JavaLang.Exceptions.LocaleNotFoundException;
import JavaLang.ValidityController.Regex;
import JavaLang.ValidityController.RegexSupervisor;
import lombok.NonNull;

import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Map;

public class JavaLang {
    //New version of JavaLanguage i18n manager

    private Container textContainer;
    private static String root = "translations";
    @Setter private Languages locale;

    @SneakyThrows
    public JavaLang(String path) {
        ArrayList<String> processedRoute = new RegexSupervisor(Regex.PATH_DISASSEMBLER, path).matchingList();

        checkRouteValidity(processedRoute, path);

        this.root = processedRoute.get(0);
        this.locale = Languages.valueOf(processedRoute.get(1));

        load();
    }

    public JavaLang(String root, Languages locale) {
        this.root = root;
        this.locale = locale;

        load();
    }

    // Load Reload methods
    public void reload() {
        load();
    }

    public void reload(Languages locale) {
        this.locale = locale;

        load();
    }

    @SneakyThrows
    public void reload(String locale) {
        checkLocaleValidity(locale);
        this.locale = Languages.valueOf(locale);

        load();
    }

    private void load() {
        this.textContainer = new JsonHandler(root, locale).getContainerFromJson();
        replaceEmojisAllItems();
    }

    // TODO --- STATIC ACCESS

    @SneakyThrows
    public String getLang(String key, Object... args) {
        checkKeyExistence(key);

        return replaceTextLabels(textContainer.getContainer().get(key), key, args);
    }

    // Replacers
    @SneakyThrows
    private void replaceEmojisAllItems() {
        RegexSupervisor regexSupervisor = new RegexSupervisor(Regex.EMOJI_FOUNDER);
        Map<String, String> container = textContainer.getContainer();
        Emojis emojis = new Emojis();

        for (String key : container.keySet()) {
            container.replace(key, replaceEmoji(regexSupervisor, emojis, container.get(key), key));
        }
    }

    @SneakyThrows
    @NonNull
    private String replaceEmoji(RegexSupervisor supervisor, Emojis emojis, String text, String key) {
        supervisor.executeRegexOnString(text);
        ArrayList<String> matchesFound = supervisor.matchingList();

        if (matchesFound == null) return text;

        String editedText = text;
        for (String value : matchesFound) {
            editedText = editedText.replaceFirst(value,
                new StringBuilder()
                    .append("\\\\")
                    .append(emojis.getEmoji(
                        value.replace(":", ""),
                            key,
                            locale.toString()
                    ))
                    .toString()
            );
        }

        return editedText;
    }

    @SneakyThrows
    @NonNull
    private String replaceTextLabels(String text, String key,  @NonNull Object... args) {
        checkLabelRelationship(text, key, args.length);

        for (Object obj : args) {
            text = text.replaceFirst(Regex.LABEL_FOUNDER.getRegex(), obj.toString());
        }
        return text;
    }

    // Checkers
    private void checkRouteValidity(ArrayList<String> processedRoute, String path) throws LocaleNotFoundException, BrokenRouteException {
        if (processedRoute == null) throw new BrokenRouteException(
            new StringBuilder()
                .append("Unable to extrapolate root folder and language from path: ")
                .append(path)
                .append("\nRecommended route: \\root\\locale.json")
                .toString()
        );

        checkLocaleValidity(processedRoute.get(1));
    }

    private void checkLocaleValidity(String locale) throws LocaleNotFoundException {
        if (!Languages.contains(locale)) throw new LocaleNotFoundException(
            new StringBuilder()
                .append("local code invalid or not found:")
                .append(locale)
                .append("\nValid codes are available at LINK")
                .toString()
        );
    }

    private void checkKeyExistence(String key) throws KeyNotFoundException {
        if (!textContainer.getContainer().containsKey(key)) throw new KeyNotFoundException(
            new StringBuilder()
                .append("No key corresponding to \"")
                .append(key)
                .append("\" found on the loaded locale \"")
                .append(locale.toString())
                .append("\"")
                .toString()
        );
    }

    private void checkLabelRelationship(String text, String key, int argumentsAmount) throws ArgumentsOutOfRangeException {
        ArrayList<String> matches = new RegexSupervisor(Regex.LABEL_FOUNDER, text).matchingList();

        if (matches == null) return;

        if (matches.size() != argumentsAmount) throw new ArgumentsOutOfRangeException(
            new StringBuilder()
                .append("Amount of mismatched arguments in \"")
                .append(key).append("\" at the locale \"")
                .append(locale.toString()).append("\"\nArguments in the label: ")
                .append(matches.size()).append("\nArguments passed: ").append(argumentsAmount)
                .toString()
        );
    }
}
