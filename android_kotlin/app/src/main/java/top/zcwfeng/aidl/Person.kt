package top.zcwfeng.aidl

import android.os.Parcel
import android.os.Parcelable


class Person(private var name: String?, private var grade: Int) : Parcelable {


    constructor(parcel: Parcel) :
            this(name = parcel.readString(), grade = parcel.readInt())


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(grade)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Person(name=$name, grade=$grade)"
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }


}