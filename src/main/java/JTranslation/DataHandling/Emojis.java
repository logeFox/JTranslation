package JTranslation.DataHandling;

import JTranslation.Exceptions.EmojiNotFoundException;

public class Emojis {

    private static Emojis INSTANCE;
    private final Container emojiContainer = new JsonHandler("emojis.json").getContainerFromJson();

    protected Emojis(){}

    public String getEmoji(String emoji, String key, String locale) throws EmojiNotFoundException {
        if (!emojiContainer.getContainer().containsKey(emoji))
            throw new EmojiNotFoundException(
                new StringBuilder()
                    .append("Emoji \"").append(emoji).append("\" not found in \"")
                    .append(key)
                    .append("\" key in \"")
                    .append(locale)
                    .append("\" locale")
                    .append("\nThe available emoji can be found on the website: https://emojipedia.org/")
                    .append("\nFor more information on emoji use: https://github.com/logeFox/JTranslation#emoji-usage")
                    .toString()
            );

        return emojiContainer.getContainer().get(emoji);
    }

    public static Emojis getInstance(){
        if(INSTANCE == null) INSTANCE = new Emojis();
        return INSTANCE;
    }
}
