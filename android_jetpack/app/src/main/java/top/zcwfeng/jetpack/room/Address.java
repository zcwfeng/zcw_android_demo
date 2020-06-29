package top.zcwfeng.jetpack.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Address
{
    @PrimaryKey(autoGenerate = true)
    public int addressId;
    @ColumnInfo(name = "name")
    public String name;

    public Address(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", name='" + name + '\'' +
                '}';
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
