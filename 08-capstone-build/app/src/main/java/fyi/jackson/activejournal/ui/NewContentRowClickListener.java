package fyi.jackson.activejournal.ui;

public interface NewContentRowClickListener {
    int TYPE_ADD_TEXT = 765;
    int TYPE_ADD_IMAGE = 777;
    int TYPE_EDIT_CONTENT = 772;

    void onClick(int interactionType);
}
