package tv.rzn.rzntv.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Автор: EVSafronov
 * Дата: 31.12.14.
 */
public class PreviewItem implements Serializable, Parcelable {
    @SerializedName("id")
    public long id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("timestamp")
    public Date timeStamp;
    @SerializedName("datestart")
    public Date startDate;
    @SerializedName("dateend")
    public Date endDate;
    @SerializedName("imageurl")
    public String imageUrl;
    @SerializedName("contenturl")
    public String contentUrl;
    @SerializedName("video")
    public String video;
    @SerializedName("siteurl")
    public String siteUrl;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeSerializable(this);
    }

    public static final Parcelable.Creator<PreviewItem> CREATOR = new Parcelable.Creator<PreviewItem>() {
        public PreviewItem createFromParcel(Parcel in) {
            return (PreviewItem) in.readSerializable();
        }

        public PreviewItem[] newArray(int size) {
            return new PreviewItem[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreviewItem that = (PreviewItem) o;

        if (id != that.id) return false;
        if (contentUrl != null ? !contentUrl.equals(that.contentUrl) : that.contentUrl != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        if (siteUrl != null ? !siteUrl.equals(that.siteUrl) : that.siteUrl != null) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null)
            return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (contentUrl != null ? contentUrl.hashCode() : 0);
        result = 31 * result + (siteUrl != null ? siteUrl.hashCode() : 0);
        return result;
    }
}
