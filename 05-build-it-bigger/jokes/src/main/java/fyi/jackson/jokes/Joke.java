package fyi.jackson.jokes;

import java.io.Serializable;

public class Joke implements Serializable {
    private String setup;
    private String punchline;

    public Joke(String setup, String punchline) {
        setSetup(setup);
        setPunchline(punchline);
    }

    @Override
    public String toString() {
        return setup + "\n" + punchline;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }
}
