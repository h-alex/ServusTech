package com.servustech.alex.servustech.model.category;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableCategory implements Parcelable {
    private String id;
    private String name;
    private String iconURL;
    private int numberOfSubcategories;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconURL() {
        return iconURL;
    }

    public int getNumberOfSubcategories() {
        return this.numberOfSubcategories;
    }

    public ParcelableCategory(Category category) {
        this.id = category.getId();
        this.name = category.getName();

        if (category.getIcon() != null) {
            this.iconURL = category.getIcon().getImageURL();
        } else {
            this.iconURL = "";
        }
        this.numberOfSubcategories = category.getNumberOfSubcategories();
    }

    private ParcelableCategory(Parcel parcel) {
        String[] data = new String[3];
        parcel.readStringArray(data);

        id = data[0];
        name = data[1];
        iconURL = data[2];
        numberOfSubcategories = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.id, this.name, this.iconURL});
        parcel.writeInt(this.numberOfSubcategories);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public ParcelableCategory createFromParcel(Parcel parcel) {
            return new ParcelableCategory(parcel);
        }

        @Override
        public ParcelableCategory[] newArray(int i) {
            return new ParcelableCategory[i];
        }
    };
}
