package JavaLang.ValidityController;

import lombok.Getter;

public enum Regex {

    PATH_DISASSEMBLER("[.+\\\\]*(\\w+)\\\\(\\w+)(?=(?:\\.[\\w]*)?$)"),
    EMOJI_FOUNDER("(:[A-Z _]+:)"),
    LABEL_FOUNDER("\\{}");

    @Getter private final String regex;

    Regex(String regex) {
        this.regex = regex;
    }
}
