package fyi.jackson.activejournal.ui;

import java.util.List;

import fyi.jackson.activejournal.data.entities.Content;

public interface ContentChangeListener {
    void onChange(List<Content> updatedContents);
    void onChange(Content updatedContent);

    void onInsert(Content newContent);
}
