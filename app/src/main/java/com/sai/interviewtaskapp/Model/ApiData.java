package com.sai.interviewtaskapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sai.interviewtaskapp.ListScreenActivity;

public class ApiData implements Parcelable
{
    private String id;

    private String title;

    private String description;

    private String thumb;

    private String url;


    public ApiData(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        thumb = in.readString();
        url = in.readString();
    }

    public static final Creator<ApiData> CREATOR = new Creator<ApiData>() {
        @Override
        public ApiData createFromParcel(Parcel in) {
            return new ApiData(in);
        }

        @Override
        public ApiData[] newArray(int size) {
            return new ApiData[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Creator<ApiData> getCREATOR() {
        return CREATOR;
    }

    public ApiData() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(thumb);
        dest.writeString(url);
    }
}
