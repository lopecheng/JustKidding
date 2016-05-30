package com.justkid.bain.justkidding.util;

import android.os.Parcel;

import java.util.HashMap;
import java.util.Map;


public class ParcelableUtil {
    private ParcelableUtil() { }

    public static void writeStringMap(Map<String, String> map, Parcel parcel) {
        if (map != null && map.size() > 0) {
            parcel.writeInt(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                parcel.writeString(entry.getKey());
                parcel.writeString(entry.getValue());
            }
        } else {
            parcel.writeInt(0);
        }
    }

    public static Map<String, String> readStringMap(Parcel parcel) {
        Map<String, String> map = null;
        int size = parcel.readInt();
        if (size > 0) {
            map = new HashMap<String, String>(size);
            for (int i = 0; i < size; i++) {
                String key = parcel.readString();
                String value = parcel.readString();
                map.put(key, value);
            }
        }
        return map;
    }
}

