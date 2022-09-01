package JTranslation.ValidityController;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSupervisor {
    private Matcher matcher;
    private Regex regex;

    public RegexSupervisor(Regex regex, String text) {
        this.matcher = compileRegex(regex, text);
    }

    public RegexSupervisor(Regex regex) {
        this.regex = regex;
    }

    public void executeRegexOnString(String text) {
        this.matcher = compileRegex(regex, text);
    }
    
    public ArrayList<String> matchingList() {
        ArrayList<String> matchResult = new ArrayList<>();
        while(matcher.find()) {
            matchResult.add(matcher.group(0));
        }

        if (matchResult.isEmpty()) return null;

        return matchResult;
    }

    @NonNull
    private Matcher compileRegex(Regex regex, String text) {
        return Pattern
            .compile(regex.getRegex(), Pattern.MULTILINE)
            .matcher(text);
    }
}
