package fyi.jackson.activejournal.util;

import fyi.jackson.activejournal.data.entities.Content;

// Validator is a helper class to validate user input
// All methods in this class return booleans:
// All methods return true when input is valid, false when invalid
public class Validator {
    public static boolean checkActivityName(String name) {
        return !name.isEmpty();
    }

    public static boolean checkContent(Content content) {
        return content.getType() == Content.TYPE_IMAGE || !content.getValue().isEmpty();
    }
}
