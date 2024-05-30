package cn.hit.sw.gui;

import cn.hit.sw.lab1.Generator;
import cn.hit.sw.lab1.impl.GeneratorImpl;
import cn.hit.sw.lab1.impl.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.filechooser.FileSystemView;


public class SimpleGUI extends JFrame {
    private Generator generator = null;

    public SimpleGUI() {
        // 设置窗口标题
        setTitle("有向图小程序");
        // 设置关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口布局为垂直BoxLayout
        getContentPane().setLayout(new FlowLayout());
        // 初始化组件
        initComponents();
        // 设置窗口大小
        setSize(400, 200);
        // 设置窗口可见性
        setVisible(true);
    }

    private void initComponents() {
        // 创建按钮
        JButton buttonInit = new JButton("初始化有向图");
        JButton buttonShow = new JButton("显示有向图");
        JButton buttonQuery = new JButton("查询桥接词");
        JButton buttonGenerate = new JButton("生成新文本");
        JButton buttonShortest = new JButton("计算最短路径");
        JButton buttonRandom = new JButton("随机游走");

        // 添加组件到窗口
        add(buttonInit);
        add(buttonShow);
        add(buttonQuery);
        add(buttonGenerate);
        add(buttonShortest);
        add(buttonRandom);


        // 添加事件监听器
        buttonInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建文件选择器
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // 获取选择的文件路径
                    String selectedPath = fileChooser.getSelectedFile().getPath();
                    try {
                        generator = new GeneratorImpl(util.getGraphFromFile(new File(selectedPath)));
                        JOptionPane.showMessageDialog(null, "有向图初始化成功.");
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "未知错误!");
                    }
                }
            }
        });

        // 添加事件监听器
        buttonShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generator == null) {
                    JOptionPane.showMessageDialog(null, "请先初始化有向图!");
                } else {
                    generator.showDirectedGraph(null);
                }
            }
        });

        // 添加事件监听器
        buttonQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generator == null) {
                    JOptionPane.showMessageDialog(null, "请先初始化有向图!");
                    return;
                }
                // 创建 JPanel 来包含输入字段
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                // 创建两个标签和两个文本输入框
                JLabel label1 = new JLabel("请输入第一个单词:");
                JTextField textField1 = new JTextField(10);
                JLabel label2 = new JLabel("请输入第二个单词:");
                JTextField textField2 = new JTextField(10);

                // 将标签和文本框添加到面板
                panel.add(label1);
                panel.add(textField1);
                panel.add(label2);
                panel.add(textField2);

                // 显示对话框并获取用户响应
                int result = JOptionPane.showConfirmDialog(null, panel, "输入", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // 根据用户响应处理输入
                if (result == JOptionPane.OK_OPTION) {
                    String input1 = textField1.getText();
                    String input2 = textField2.getText();
                    JOptionPane.showMessageDialog(null, generator.queryBridgeWords(input1, input2));
                }
            }
        });

        // 添加事件监听器
        buttonGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generator == null) {
                    JOptionPane.showMessageDialog(null, "请先初始化有向图!");
                    return;
                }
                String input1 = JOptionPane.showInputDialog(null, "请输入文本:");

                if (input1 != null) {
                    // 弹出一个消息对话框显示输入内容
                    JOptionPane.showMessageDialog(null, generator.generateNewText(input1));
                }
            }
        });

        // 添加事件监听器
        buttonShortest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generator == null) {
                    JOptionPane.showMessageDialog(null, "请先初始化有向图!");
                    return;
                }
                // 创建 JPanel 来包含输入字段
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                // 创建两个标签和两个文本输入框
                JLabel label1 = new JLabel("请输入第一个单词:");
                JTextField textField1 = new JTextField(10);
                JLabel label2 = new JLabel("请输入第二个单词:");
                JTextField textField2 = new JTextField(10);

                // 将标签和文本框添加到面板
                panel.add(label1);
                panel.add(textField1);
                panel.add(label2);
                panel.add(textField2);

                // 显示对话框并获取用户响应
                int result = JOptionPane.showConfirmDialog(null, panel, "输入", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // 根据用户响应处理输入
                if (result == JOptionPane.OK_OPTION) {
                    String input1 = textField1.getText();
                    String input2 = textField2.getText();
                    JOptionPane.showMessageDialog(null, generator.calcShortestPath(input1, input2));
                }
                generator.showDirectedGraph(null);
            }
        });

        // 添加事件监听器
        buttonRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generator == null) {
                    JOptionPane.showMessageDialog(null, "请先初始化有向图!");
                    return;
                }
                generator.randomWalk();
            }
        });
    }
}
