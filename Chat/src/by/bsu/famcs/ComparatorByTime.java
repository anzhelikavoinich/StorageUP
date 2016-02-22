package by.bsu.famcs;

import java.util.Comparator;

public class ComparatorByTime implements Comparator<Message> {
    @Override
    public int compare(Message mes1, Message mes2) {
        Long l1 = mes1.getDate();
        Long l2 = mes2.getDate();
        return (int)(l1-l2);

    }
}
