package app.utils;

import app.ui.ConsoleUI;

import java.util.List;
import java.util.Map;

public class CollectionsDisplayer {

    private final ConsoleUI consoleUI;

    public CollectionsDisplayer(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    public <K, V> void displayMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            consoleUI.println(entry.getKey() + ": " + entry.getValue());
            consoleUI.println("--------------------------------------");
        }
    }

    public <T> void displayList(List<T> list) {
        for (T element : list) {
            consoleUI.println(element.toString());
            consoleUI.println("--------------------------------------");
        }
    }
}
