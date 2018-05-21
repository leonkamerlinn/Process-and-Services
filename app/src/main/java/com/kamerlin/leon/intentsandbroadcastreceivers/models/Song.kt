package com.kamerlin.leon.intentsandbroadcastreceivers.models

import android.os.Parcel
import android.os.Parcelable
import com.kamerlin.leon.intentsandbroadcastreceivers.toBoolean

/**
 * Created by benjakuben on 5/13/16.
 */
class Song(
        val id: Long,
        val title: String,
        val duration: Int,
        val artist: String,
        val label: String,
        val yearReleased: Int,
        val albumId: Long,
        val isFavorite: Boolean
) : Parcelable {





    override fun toString(): String {
        return "ID: " + id + "\n" +
                "Title: " + title + "\n" +
                "Duration: " + duration + "\n" +
                "Artist: " + artist + "\n" +
                "Label: " + label + "\n" +
                "Year Released: " + yearReleased + "\n" +
                "Album ID: " + albumId + "\n" +
                "Favorite?: " + isFavorite
    }

    override fun describeContents(): Int {
        return 0 // ignore
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(title)
        dest.writeInt(duration)
        dest.writeString(artist)
        dest.writeString(label)
        dest.writeInt(yearReleased)
        dest.writeLong(albumId)
        dest.writeInt(if (isFavorite) 1 else 0)
    }




    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song?> {
            return arrayOfNulls(size)
        }
    }

    constructor(p: Parcel) : this(
            p.readLong(),
            p.readString(),
            p.readInt(),
            p.readString(),
            p.readString(),
            p.readInt(),
            p.readLong(),
            p.readInt().toBoolean()
    )


}
