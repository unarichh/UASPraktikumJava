package frame;

import javax.swing.*;

public class KabupatenViewFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel cariPanel;
    private JTextField textField1;
    private JButton cariButton;
    private JTable table1;
    private JButton tambahButton;
    private JButton ubahButton;
    private JButton hapusButton;
    private JButton batalButton;
    private JButton cetakButton;
    private JButton tutupButton;

    public KabupatenViewFrame(){
        init();
    }
    public void init(){
        setContentPane(mainPanel);
        setTitle("Data Kabupaten");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}

