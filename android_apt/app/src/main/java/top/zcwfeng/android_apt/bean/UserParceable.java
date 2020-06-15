package top.zcwfeng.android_apt.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserParceable implements Parcelable {
    String name;


    public UserParceable(String name) {
        this.name = name;
    }

    protected UserParceable(Parcel in) {
        name = in.readString();
    }

    public static final Creator<UserParceable> CREATOR = new Creator<UserParceable>() {
        @Override
        public UserParceable createFromParcel(Parcel in) {
            return new UserParceable(in);
        }

        @Override
        public UserParceable[] newArray(int size) {
            return new UserParceable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return "UserParceable{" +
                "name='" + name + '\'' +
                '}';
    }
}
