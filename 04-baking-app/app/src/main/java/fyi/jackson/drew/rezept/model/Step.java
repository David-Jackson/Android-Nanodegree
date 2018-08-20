package fyi.jackson.drew.rezept.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    @SerializedName("videoURL")
    private
    String videoUrl;
    @SerializedName("thumbnailURL")
    private
    String thumbnailUrl;

    public Step() {}

    private Step(Parcel in) {
        setId(in.readInt());
        setShortDescription(in.readString());
        setDescription(in.readString());
        setVideoUrl(in.readString());
        setThumbnailUrl(in.readString());
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    private void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        if (videoUrl.equals("")) return thumbnailUrl;
        return videoUrl;
    }

    private void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    private void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }

    public String toShortString() {
        return (id == 0 ? "" : id + ".\t") + shortDescription;
    }
}
