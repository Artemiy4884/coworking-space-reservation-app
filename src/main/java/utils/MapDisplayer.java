package utils;

import java.util.Map;

public class MapDisplayer {

    public static <K, V> void display(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            System.out.println("--------------------------------------");
        }
    }
}
