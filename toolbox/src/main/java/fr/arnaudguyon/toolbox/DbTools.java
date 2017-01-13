package fr.arnaudguyon.toolbox;

import android.database.Cursor;

/**
 * Created by aguyon on 13.01.17.
 */

public class DbTools {
    public static String getString(final Cursor cursor, final String columnName) {
        final int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return null;
        }
        return cursor.getString(columnIndex);
    }
    public static Integer getInt(final Cursor cursor, final String columnName) {
        final int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return null;
        }
        if (cursor.isNull(columnIndex)) {
            return null;
        }
        return cursor.getInt(columnIndex);
    }
    public static int getIntValue(final Cursor cursor, final String columnName, final int defaultValue) {
        Integer value = getInt(cursor, columnName);
        if (value != null) {
            return value.intValue();
        } else {
            return defaultValue;
        }
    }
    public static Long getLong(final Cursor cursor, final String columnName) {
        final int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return null;
        }
        if (cursor.isNull(columnIndex)) {
            return null;
        }
        return cursor.getLong(columnIndex);
    }
    public static long getLongValue(final Cursor cursor, final String columnName, long defaultValue) {
        Long value = getLong(cursor, columnName);
        if (value != null) {
            return value.longValue();
        } else {
            return defaultValue;
        }
    }
    public static Float getFloat(final Cursor cursor, final String columnName) {
        final int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return null;
        }
        if (cursor.isNull(columnIndex)) {
            return null;
        }
        return cursor.getFloat(columnIndex);
    }
    public static float getFloatValue(final Cursor cursor, final String columnName, float defaultValue) {
        Float value = getFloat(cursor, columnName);
        if (value != null) {
            return value.floatValue();
        } else {
            return defaultValue;
        }
    }
    public static Boolean getBoolean(final Cursor cursor, final String columnName) {
        final int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return null;
        }
        if (cursor.isNull(columnIndex)) {
            return null;
        }
        Integer value = cursor.getInt(columnIndex);
        return (value != null) ? (value == 1) : null;
    }
    public static boolean getBooleanValue(final Cursor cursor, final String columnName, final boolean defaultValue) {
        Boolean value = getBoolean(cursor, columnName);
        if (value != null) {
            return value.booleanValue();
        } else {
            return defaultValue;
        }
    }

    public static boolean stringHasChanged(final Cursor cursor, final String columnName, final String newString) {
        final int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return true;
        }
        final String oldString = cursor.getString(columnIndex);
        if (((oldString == null) && (newString != null)) || ((oldString != null) && (newString == null))) {
            return true;
        }
        if (oldString == null) {
            return (newString != null);
        }
        return (!oldString.equals(newString));
    }
    public static boolean longHasChanged(final Cursor cursor, final String columnName, final long newLong) {
        final int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return true;
        }
        if (cursor.isNull(columnIndex)) {
            return true;
        }
        final long oldLong = cursor.getLong(columnIndex);
        return (oldLong != newLong);
    }
    public static boolean intHasChanged(final Cursor cursor, final String columnName, final int newInt) {
        final int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex < 0) {
            return true;
        }
        if (cursor.isNull(columnIndex)) {
            return true;
        }
        final int oldInt = cursor.getInt(columnIndex);
        return (oldInt != newInt);
    }

}
