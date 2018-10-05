package fyi.jackson.activejournal.util;

public class Validator {
    public static boolean checkActivityName(String name) {
        return !name.isEmpty();
    }
}
