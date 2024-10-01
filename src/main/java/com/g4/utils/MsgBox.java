package com.g4.utils;

import java.awt.Component;
import javax.swing.JOptionPane;


public class MsgBox {

    public static void alert(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message);
    }

    public static boolean confirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message,
                "Quản lý bán giày thể thao nam G4",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    public static String prompt(Component parent, String message) {
        return JOptionPane.showInputDialog(parent, message,
                "Quản lý bán giày thể thao nam G4", JOptionPane.INFORMATION_MESSAGE);
    }

}
