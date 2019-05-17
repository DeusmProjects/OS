import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    Disk disk = Disk.getInstance();

    public Main() {

        var jPanel = new JPanel();
        setContentPane(jPanel);

        jPanel.setLayout(null);

        var listModel = new DefaultListModel();

        int rows = (disk.getCountBlocks()*15 / 1000) + 1; // 1000 - это максимальная ширина таблицы будет, дальше он уже вторую строку начнет
        int columns = disk.getCountBlocks() / rows;

        var tablePanel = new Panel(rows, columns);
        tablePanel.setBounds(10, 10, columns*15, rows*15);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
//        tablePanel.setBackground(new Color(0, 195, 190));
        jPanel.add(tablePanel);

        var panel = new JPanel();
        panel.setBounds(0, 260, 1300, 590);
        jPanel.add(panel);
        panel.setLayout(null);


        var labelName = new JLabel("Имя файла: ");
        labelName.setBounds(450, 0, 200, 23);
        panel.add(labelName);

        var textFieldName = new JTextField();
        textFieldName.setBounds(550, 0, 200, 23);
        panel.add(textFieldName);

        var labelSize = new JLabel("Размер файла: ");
        labelSize.setBounds(450, 40, 200, 23);
        panel.add(labelSize);

        var textFieldSize = new JTextField();
        textFieldSize.setBounds(550, 40, 200, 23);
        panel.add(textFieldSize);

        var labelDiskSize = new JLabel("Размер диска: " + disk.getDiskSize());
        labelDiskSize.setBounds(850, 40, 200, 23);
        panel.add(labelDiskSize);

        var labelBlockSize = new JLabel("Размер блока: " + disk.getBlockSize());
        labelBlockSize.setBounds(850, 70, 200, 23);
        panel.add(labelBlockSize);

//        var labelFreeBlocks = new JLabel("Свободно блоков: " + disk.getFreeBlocks());
//        labelFreeBlocks.setBounds(850, 100, 200, 23);
//        panel.add(labelFreeBlocks);

        var list = new JList(listModel);
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String fileName = (String) list.getSelectedValue();
                if (fileName == null) {
                    return;
                }
                disk.selectFile(fileName);
                tablePanel.repaint();
            }
        });
        list.setBounds(10, 0, 400, 150);
        panel.add(list);

        var button = new JButton("Создать");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int sizeFile = Integer.parseInt(textFieldSize.getText());

                    String fileName = textFieldName.getText();

                    if (fileName.isEmpty()) {
                        throw new Exception("Заполните имя файла!");
                    }

                    disk.addFile(new File(fileName, sizeFile));
                    listModel.addElement(textFieldName.getText());

                    textFieldName.setText("");
                    textFieldSize.setText("");
                    tablePanel.repaint();
                    //panel.repaint();
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    System.out.println(ex.getMessage());
                }
            }
        });
        button.setBounds(550, 80, 200, 23);
        panel.add(button);

        var button_1 = new JButton("Удалить");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String fileName = (String) list.getSelectedValue();
                    listModel.removeElement(list.getSelectedValue());
                    disk.deleteFile(fileName);
                    tablePanel.repaint();
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    System.out.println(ex.getMessage());
                }
            }
        });
        button_1.setBounds(550, 120, 200, 23);
        panel.add(button_1);



        setSize(1000, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main gr = new Main();
        gr.setVisible(true);
    }
}
