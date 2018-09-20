package fyi.jackson.activejournal.util;

import java.util.List;

import fyi.jackson.activejournal.data.entities.Content;

public interface ContentPositionListener {
    void onPositionChanged(List<Content> newContents);
}
