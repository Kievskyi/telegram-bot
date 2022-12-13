package com.example.demo5bot.enums;

import com.vdurmont.emoji.EmojiParser;

public enum Emoji {
    CROSS(":x:"),
    PLUS(":new:"),
    DONE(":heavy_check_mark:"),
    DOWN_ARROW(":arrow_heading_down: "),
    ROCKET(":rocket:"),
    CAT_PRINTS(":paw_prints:"),
    CLIPBOARD(":clipboard:"),
    BRAIN(":brain:"),
    BACK(":end:"),
    YELLOW_CIRCLE(":large_orange_diamond:"),
    RED_CIRCLE(":large_blue_diamond:"),
    CLAP(":clap:"),
    SARCASTIC_CAT(":smirk_cat:"),
    LOVE_CAT(":heart_eyes_cat:"),
    KISSING_CAT(":kissing_cat:"),
    LAUGH_CAT(":joy_cat:"),
    SCREAM_CAT(":scream_cat:"),
    CRYING_CAT(":crying_cat_face:");

    String unicode;

    Emoji(String unicode) {
        this.unicode = unicode;
    }

    public String getEmoji() {
        return EmojiParser.parseToUnicode(unicode);
    }
}