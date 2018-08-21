package fyi.jackson.jokes;

import java.util.ArrayList;
import java.util.List;

public class Joker {

    private int jokesIndex = -1;
    private List<Joke> jokesList = new ArrayList<Joke>() {{
        add(new Joke(
                "Scientist: \"My findings are meaningless if taken out of context.\"",
                "Media: Scientist claims \"Findings are meaningless.\""));
        add(new Joke(
                "Set your wifi password to 2444666668888888",
                "So when someone asks tell them it's 12345678"));
        add(new Joke(
                "If i had a dime for every time i didn't understand what's going on.",
                "I'd be like: \"Why y'all keep giving me all these dimes?\""));
        add(new Joke(
                "\"Hey Dad, have you seen my sunglasses?\"",
                "\"No son, have you seen my dad glasses?\""));
    }};

    public Joke getJoke() {
        jokesIndex++;
        jokesIndex %= jokesList.size();
        return jokesList.get(jokesIndex);
    }
}
