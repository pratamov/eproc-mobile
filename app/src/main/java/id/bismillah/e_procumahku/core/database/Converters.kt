package id.bismillah.e_procumahku.core.database

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Subkhan Sarif on 24/03/2018.
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}