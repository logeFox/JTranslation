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

import JavaLang.Exceptions.EmojiNotFoundException;

public class Emojis {

    private static Emojis INSTANCE;
    private final Container emojiContainer = new JsonHandler("emojis.json").getContainerFromJson();

    protected Emojis(){}

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

    public static Emojis getInstance(){
        if(INSTANCE == null) INSTANCE = new Emojis();
        return INSTANCE;
    }
}
