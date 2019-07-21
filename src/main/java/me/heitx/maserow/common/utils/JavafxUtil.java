package me.heitx.maserow.common.utils;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Collection;
import java.util.Iterator;

public class JavafxUtil {
    public static void assignValuesToTextfields(Collection<? extends Control> tfs, Collection values) {
        if(tfs.size() != values.size()) return;

        Iterator<? extends Control> i1 = tfs.iterator();
        Iterator i2 = values.iterator();

        while(i1.hasNext() && i2.hasNext()) {
            Control next = i1.next();

            String text = String.valueOf(i2.next());

            if(next instanceof TextField) {
                ((TextField) next).setText(text);
            } else if(next instanceof Label) {
                ((Label) next).setText(text);
            }
        }
    }
}
