package frame;

import helpers.ComboBoxItem;
import helpers.Koneksi;

import javax.swing.*;
import java.sql.*;

public class KecamatanInputFrame extends JFrame{
    private JTextField idTextField;
    private JTextField namaTextField;
    private JComboBox kabupatenComboBox;
    private JButton batalButton;
    private JButton simpanButton;
    private JPanel mainPanel;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public KecamatanInputFrame() {
        batalButton.addActionListener(e -> {
            dispose();
            kustomisasiKomponen();
        });
        simpanButton.addActionListener(e-> {
            String nama = namaTextField.getText();
            if (nama.equals("")){
                JOptionPane.showMessageDialog(null, "Isi Nama Kecamatan", "Validasi data Kosong", JOptionPane.WARNING_MESSAGE);
                namaTextField.requestFocus();
                return;
            }
            ComboBoxItem item = (ComboBoxItem) kabupatenComboBox.getSelectedItem();
            int kabupatenId = item.getValue();
            if (kabupatenId == 0 ){
                JOptionPane.showMessageDialog(null, "pilih kabupaten", "validasi combobox", JOptionPane.WARNING_MESSAGE);
                kabupatenComboBox.requestFocus();
                return;
            }
            Connection c = Koneksi.getConnection();
            PreparedStatement ps;
            try {
                if(id == 0){
                    String insertSQL = "INSERT INTO kecamatan (id, nama, kabupaten_id) VALUES (NULL, ?, ?)";
                    ps = c.prepareStatement(insertSQL);
                    ps.setString(1,nama);
                    ps.setInt(2,kabupatenId);
                    ps.executeUpdate();
                    dispose();
                }else {
                    String updateSQL = "UPDATE kecamatan SET nama = ?, kabupaten_id = ? WHERE id = ?";
                    ps = c.prepareStatement(updateSQL);
                    ps.setString(1, nama);
                    ps.setInt(2, kabupatenId);
                    ps.setInt(3,id);
                    ps.executeUpdate();
                    dispose();
                }
            }catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        init();
    }
        public void init(){
            setContentPane(mainPanel);
            setTitle("Input Kecamatan");
            pack();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
    }
    public void isiKomponen() {
        Connection c = Koneksi.getConnection();
        String findSQL = "SELECT * FROM kecamatan WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idTextField.setText(String.valueOf(rs.getInt("id")));
                namaTextField.setText(rs.getString("nama"));
                int kabupatenId = rs.getInt("kabupaten_id");
                for (int i = 0; i < kabupatenComboBox.getItemCount(); i++){
                    kabupatenComboBox.setSelectedIndex(i);
                    ComboBoxItem item =(ComboBoxItem) kabupatenComboBox.getSelectedItem();
                    if (kabupatenId == item.getValue()) {
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void kustomisasiKomponen(){
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT * FROM kabupaten ORDER BY nama";
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            kabupatenComboBox.addItem(new ComboBoxItem(0,"Pilih Kabupaten"));
            while (rs.next()){
                kabupatenComboBox.addItem(new ComboBoxItem(
                        rs.getInt("id"),
                        rs.getString("nama")));
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    }

