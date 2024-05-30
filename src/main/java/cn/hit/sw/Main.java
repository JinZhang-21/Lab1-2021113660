package cn.hit.sw;

import cn.hit.sw.gui.SimpleGUI;
import cn.hit.sw.lab1.Generator;
import cn.hit.sw.lab1.impl.GeneratorImpl;
import cn.hit.sw.lab1.impl.util;
import org.graphstream.graph.Graph;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // 在事件分派线程中运行GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleGUI();
            }
        });

    }
}