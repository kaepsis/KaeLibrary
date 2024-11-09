package dev.kaepsis.library;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String color(final String message) {
        final char colorChar = ChatColor.COLOR_CHAR;

        final Matcher hexMatcher = HEX_PATTERN.matcher(message);
        final StringBuilder buffer = new StringBuilder(message.length() + 4 * 8);

        while (hexMatcher.find()) {
            final String group = hexMatcher.group(1);

            hexMatcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        final String partiallyTranslated = hexMatcher.appendTail(buffer).toString();

        return ChatColor.translateAlternateColorCodes('&', partiallyTranslated);
    }

    public static void send(CommandSender receiver, String message, String... placeholders) {
        String modifiedMessage = message;
        for (int i = 0; i < placeholders.length; i += 2) {
            String placeholder = placeholders[i];
            String replacement = placeholders[i + 1];
            modifiedMessage = modifiedMessage.replace(placeholder, replacement);
        }
        send(receiver, modifiedMessage);
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

}
