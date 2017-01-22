package tv.rzn.rzntv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Автор: EVSafronov
 * Дата: 31.12.14.
 */
public class NavigationItem implements Serializable, Parcelable {
    public String title;
    public long order;
    public ContentType contentType;
    public String requestPath;
    public Integer imageRes;

    public NavigationItem(String title, long order, ContentType contentType, String requestPath, Integer imageRes) {
        this.title = title;
        this.order = order;
        this.contentType = contentType;
        this.requestPath = requestPath;
        this.imageRes = imageRes;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeSerializable(this);
    }

    public static final Parcelable.Creator<NavigationItem> CREATOR = new Parcelable.Creator<NavigationItem>() {
        public NavigationItem createFromParcel(Parcel in) {
            return (NavigationItem) in.readSerializable();
        }

        public NavigationItem[] newArray(int size) {
            return new NavigationItem[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NavigationItem that = (NavigationItem) o;

        if (order != that.order) return false;
        if (contentType != that.contentType) return false;
        if (imageRes != null ? !imageRes.equals(that.imageRes) : that.imageRes != null)
            return false;
        if (requestPath != null ? !requestPath.equals(that.requestPath) : that.requestPath != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (int) (order ^ (order >>> 32));
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (requestPath != null ? requestPath.hashCode() : 0);
        result = 31 * result + (imageRes != null ? imageRes.hashCode() : 0);
        return result;
    }
}
