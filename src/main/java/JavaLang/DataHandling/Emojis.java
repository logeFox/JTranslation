package JavaLang.DataHandling;

import JavaLang.Exceptions.EmojiNotFoundException;

public class Emojis {

    // TODO --- implements constructor, checker, utils
    private final String emojiPath = "emojis.json";
    private final Container emojiContainer;

    public Emojis() {
        this.emojiContainer = new JsonHandler(emojiPath).getContainerFromJson();
    }

    public String getEmoji(String emoji, String key, String locale) throws EmojiNotFoundException {
        if (!emojiContainer.getContainer().containsKey(emoji))
            //TODO --- Replace Link with WIP Docs
            throw new EmojiNotFoundException(
                new StringBuilder()
                    .append("Emoji \"").append(emoji).append("\" not found in \"")
                    .append(key)
                    .append("\" key in \"")
                    .append(locale)
                    .append("\" locale")
                    .append("\nThe available emoji can be found on the website: https://emojipedia.org/")
                    .append("\nFor more information on emoji use: LINK")
                    .toString()
            );

        return emojiContainer.getContainer().get(emoji);
    }
}
