package ks.kanhustoryview;

import android.os.Parcel;
import android.os.Parcelable;

public class KanhuStoryModel implements Parcelable {
    public String imageUri;
    public String name;
    public String time;

    public KanhuStoryModel(String imageUri, String name, String time) {
        this.imageUri = imageUri;
        this.name = name;
        this.time = time;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Creator<KanhuStoryModel> getCREATOR() {
        return CREATOR;
    }

    protected KanhuStoryModel(Parcel in) {
        imageUri = in.readString();
        name = in.readString();
        time = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUri);
        dest.writeString(name);
        dest.writeString(time);
    }

    @SuppressWarnings("unused")
    public static final Creator<KanhuStoryModel> CREATOR = new Creator<KanhuStoryModel>() {
        @Override
        public KanhuStoryModel createFromParcel(Parcel in) {
            return new KanhuStoryModel(in);
        }

        @Override
        public KanhuStoryModel[] newArray(int size) {
            return new KanhuStoryModel[size];
        }
    };
}

