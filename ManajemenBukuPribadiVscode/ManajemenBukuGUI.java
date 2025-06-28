import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ManajemenBukuGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Buku> daftarBuku = new ArrayList<>();
    private JTextField txtSearch;
    // Variable untuk tracking sort order
    private boolean ascendingTitle = true;
    private boolean ascendingDate = true;

    public ManajemenBukuGUI() {
        setTitle("Manajemen Buku Pribadi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Utama dengan Gradien
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(186, 85, 211), 0, getHeight(), new Color(255, 105, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        // Panel Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(255, 182, 193));
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setToolTipText("Masukkan kata kunci judul, penulis, atau genre...");

        JButton btnSearch = new JButton("Cari");
        JButton btnReset = new JButton("Reset");
        customizeButton(btnSearch);
        customizeButton(btnReset);

        searchPanel.add(new JLabel("Cari Buku:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);
        panel.add(searchPanel, BorderLayout.NORTH);

        // Panel Sorting
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortPanel.setBackground(new Color(255, 182, 193));
        
        JButton btnSortTitle = new JButton("Sort by Title");
        JButton btnSortDate = new JButton("Sort by Date");
        
        customizeButton(btnSortTitle);
        customizeButton(btnSortDate);
        
        sortPanel.add(btnSortTitle);
        sortPanel.add(btnSortDate);

        // Menggabungkan Search panel dan Sort panel
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(searchPanel);
        topPanel.add(sortPanel);
        panel.add(topPanel, BorderLayout.NORTH);

        // Action listener untuk button sorting
        btnSortTitle.addActionListener(e -> sortByTitle());
        btnSortDate.addActionListener(e -> sortByDate());

        // Tabel
        String[] columnNames = {"Judul", "Penulis", "Genre", "Status Baca", "Tanggal Terakhir Baca", "Catatan"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);

        // Header Tabel
        table.getTableHeader().setBackground(new Color(186, 85, 211));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Atur lebar kolom tabel
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Judul
        columnModel.getColumn(1).setPreferredWidth(120); // Penulis
        columnModel.getColumn(2).setPreferredWidth(100); // Genre
        columnModel.getColumn(3).setPreferredWidth(100); // Status Baca
        columnModel.getColumn(4).setPreferredWidth(160); // Tanggal Terakhir Baca
        columnModel.getColumn(5).setPreferredWidth(110); // Catatan

        // Highlight Baris yang Dipilih
        table.setSelectionBackground(new Color(255, 182, 193));
        table.setSelectionForeground(Color.BLACK);

        // Baris Bergaris Zebra
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!isSelected) {
            c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(255, 240, 245));
            }
        return c;
            }
        });

        // JScrollPane untuk tabel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); // Transparansi JScrollPane
        scrollPane.getViewport().setOpaque(false); // Transparansi viewport JScrollPane
        table.setOpaque(false); // Membuat tabel transparan
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(255, 182, 193));

        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");

        customizeButton(btnTambah);
        customizeButton(btnEdit);
        customizeButton(btnHapus);

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Aksi Tombol
        btnTambah.addActionListener(e -> formBuku(null));
        btnEdit.addActionListener(e -> editBuku());
        btnHapus.addActionListener(e -> hapusBuku());
        btnSearch.addActionListener(e -> searchBuku());
        btnReset.addActionListener(e -> resetSearch());

        setVisible(true);
        }

        // Method kustomisasi Tombol
        private void customizeButton(JButton button) {
        button.setBackground(new Color(186, 85, 211));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(120, 35));
        button.setToolTipText(button.getText());
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 105, 180));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(186, 85, 211));
            }
        });
    }

    // Method untuk sorting berdasarkan judul
    private void sortByTitle() {
        Comparator<Buku> comparator = (b1, b2) -> {
            String title1 = b1.getJudul().toLowerCase();
            String title2 = b2.getJudul().toLowerCase();
            return ascendingTitle ? title1.compareTo(title2) : title2.compareTo(title1);
        };
        
        daftarBuku.sort(comparator);
        ascendingTitle = !ascendingTitle; // Toggle order
        updateTable();
    }

    // Method untuk sorting berdasarkan tanggal
    private void sortByDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        Comparator<Buku> comparator = (b1, b2) -> {
            try {
                String date1Str = b1.getTanggalSelesai();
                String date2Str = b2.getTanggalSelesai();
                
                // Handle empty dates
                if (date1Str.isEmpty() && date2Str.isEmpty()) return 0;
                if (date1Str.isEmpty()) return ascendingDate ? 1 : -1;
                if (date2Str.isEmpty()) return ascendingDate ? -1 : 1;
                
                Date date1 = sdf.parse(date1Str);
                Date date2 = sdf.parse(date2Str);
                
                return ascendingDate ? date1.compareTo(date2) : date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        };
        
        daftarBuku.sort(comparator);
        ascendingDate = !ascendingDate; // Toggle order
        updateTable();
    }

    // Form Tambah Buku
    private void formBuku(Buku buku) {
        // Panel dengan gradient background
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(186, 85, 211), 0, getHeight(), new Color(255, 105, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Style Komponen Inputan
        JTextField txtJudul = createStyledTextField(20);
        JTextField txtPenulis = createStyledTextField(20);
        JTextField txtGenre = createStyledTextField(20);
    
        String[] opsiStatus = {"Selesai", "Belum selesai"};
        JComboBox<String> comboStatus = new JComboBox<>(opsiStatus);
        styleComboBox(comboStatus);

        // Date chooser untuk input tanggal
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        styleDateChooser(dateChooser);
    
        JTextField txtCatatan = createStyledTextField(20);
    
        // Membuat Style Label
        JLabel[] labels = {
            createStyledLabel("Judul:"),
            createStyledLabel("Penulis:"),
            createStyledLabel("Genre:"),
            createStyledLabel("Status Baca:"),
            createStyledLabel("Terakhir Baca:"),
            createStyledLabel("Catatan:")
        };
    
        // Menambahkan komponen pada tabel
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(labels[i], gbc);
    
            gbc.gridx = 1;
            gbc.weightx = 0.7;
            if (i == 4) { // Untuk row tanggal
                formPanel.add(dateChooser, gbc);
            } else if (i == 3) { // Untuk row status baca
                formPanel.add(comboStatus, gbc);
            } else {
                formPanel.add(i == 0 ? txtJudul : 
                             i == 1 ? txtPenulis : 
                             i == 2 ? txtGenre : 
                             txtCatatan, gbc);
            }
        }

        // Kustom tombol panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
    
        JButton okButton = createStyledButton("OK");
        JButton cancelButton = createStyledButton("Cancel");
    
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
    
        // Main container panel
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.add(formPanel, BorderLayout.CENTER);
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Create and configure dialog
        JDialog dialog = new JDialog(this, "Form Buku", true);
        dialog.setContentPane(containerPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
    
      // Button actions
        okButton.addActionListener(e -> {
         // Validasi judul
        if (txtJudul.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(dialog,
            "Judul buku tidak boleh kosong!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        txtJudul.requestFocus();
        return;
        }
    
        // Validasi penulis
        if (txtPenulis.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(dialog,
            "Nama penulis tidak boleh kosong!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        txtPenulis.requestFocus();
        return;
        }
    
    // Validasi genre
    if (txtGenre.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(dialog,
            "Genre buku tidak boleh kosong!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        txtGenre.requestFocus();
        return;
    }

    // Validasi tanggal untuk status "Selesai"
    if (comboStatus.getSelectedItem().equals("Selesai")) {
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(dialog,
                "Tanggal harus diisi untuk buku yang sudah selesai dibaca!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            dateChooser.requestFocus();
            return;
        }
        
        // Validasi tanggal tidak boleh di masa depan
        if (dateChooser.getDate().after(new Date())) {
            JOptionPane.showMessageDialog(dialog,
                "Tanggal tidak boleh di masa depan!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            dateChooser.requestFocus();
            return;
        }
    }

    // Validasi panjang input
    if (txtJudul.getText().length() > 100) {
        JOptionPane.showMessageDialog(dialog,
            "Judul buku tidak boleh lebih dari 100 karakter!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        txtJudul.requestFocus();
        return;
    }

    if (txtPenulis.getText().length() > 50) {
        JOptionPane.showMessageDialog(dialog,
            "Nama penulis tidak boleh lebih dari 50 karakter!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        txtPenulis.requestFocus();
        return;
    }

    if (txtGenre.getText().length() > 30) {
        JOptionPane.showMessageDialog(dialog,
            "Genre tidak boleh lebih dari 30 karakter!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        txtGenre.requestFocus();
        return;
    }

    if (txtCatatan.getText().length() > 200) {
        JOptionPane.showMessageDialog(dialog,
            "Catatan tidak boleh lebih dari 200 karakter!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        txtCatatan.requestFocus();
        return;
    }

        // Jika semua validasi berhasil, simpan data
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String tanggal = dateChooser.getDate() != null ? 
        sdf.format(dateChooser.getDate()) : "";
                    
        Buku newBuku = new Buku(
        txtJudul.getText().trim(),
        txtPenulis.getText().trim(),
        txtGenre.getText().trim(),
        (String) comboStatus.getSelectedItem(),
        tanggal,
        txtCatatan.getText().trim()
        );

         daftarBuku.add(newBuku);
        updateTable();
        JOptionPane.showMessageDialog(dialog,
        "Data buku berhasil disimpan!",
        "Sukses",
        JOptionPane.INFORMATION_MESSAGE);
        dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
    
    // Method untuk styling date chooser
    private void styleDateChooser(JDateChooser dateChooser) {
    dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    dateChooser.setBackground(new Color(255, 240, 245));
    dateChooser.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(186, 85, 211)),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    
    // Style the text field inside date chooser
    JTextField dateTextField = ((JTextField)dateChooser.getDateEditor().getUiComponent());
    dateTextField.setBackground(new Color(255, 240, 245));
    dateTextField.setBorder(BorderFactory.createEmptyBorder());
    }

     // Metode styling untuk JComboBox
     private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(new Color(255, 240, 245));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(186, 85, 211)));
    }
    
    // Helper methods for styled components
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(new Color(255, 240, 245));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(186, 85, 211)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return field;
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(186, 85, 211));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 182, 193)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 105, 180));
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(186, 85, 211));
            }
        });
        
        return button;
    }
    
    // Edit Buku
    private void editBuku() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        Buku buku = daftarBuku.get(selectedRow);
    
        // Form edit dengan pre-filled data
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(186, 85, 211), 0, getHeight(), new Color(255, 105, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField txtJudul = createStyledTextField(20);
        txtJudul.setText(buku.getJudul());
        JTextField txtPenulis = createStyledTextField(20);
        txtPenulis.setText(buku.getPenulis());
        JTextField txtGenre = createStyledTextField(20);
        txtGenre.setText(buku.getGenre());
    
        String[] opsiStatus = {"Selesai", "Belum selesai"};
        JComboBox<String> comboStatus = new JComboBox<>(opsiStatus);
        comboStatus.setSelectedItem(buku.getStatusBaca());
        styleComboBox(comboStatus);
    
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        styleDateChooser(dateChooser);
        if (!buku.getTanggalSelesai().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                dateChooser.setDate(sdf.parse(buku.getTanggalSelesai()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        JTextField txtCatatan = createStyledTextField(20);
        txtCatatan.setText(buku.getCatatanPribadi());
    
        JLabel[] labels = {
            createStyledLabel("Judul:"),
            createStyledLabel("Penulis:"),
            createStyledLabel("Genre:"),
            createStyledLabel("Status Baca:"),
            createStyledLabel("Terakhir Baca:"),
            createStyledLabel("Catatan:")
        };
    
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(labels[i], gbc);
    
            gbc.gridx = 1;
            gbc.weightx = 0.7;
            if (i == 4) {
                formPanel.add(dateChooser, gbc);
            } else if (i == 3) {
                formPanel.add(comboStatus, gbc);
            } else {
                formPanel.add(i == 0 ? txtJudul :
                             i == 1 ? txtPenulis :
                             i == 2 ? txtGenre :
                             txtCatatan, gbc);
            }
        }
    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
    
        JButton okButton = createStyledButton("OK");
        JButton cancelButton = createStyledButton("Cancel");
    
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
    
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.add(formPanel, BorderLayout.CENTER);
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JDialog dialog = new JDialog(this, "Edit Buku", true);
        dialog.setContentPane(containerPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
    
        okButton.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String tanggal = dateChooser.getDate() != null ?
                            sdf.format(dateChooser.getDate()) : "";
    
            buku.setJudul(txtJudul.getText());
            buku.setPenulis(txtPenulis.getText());
            buku.setGenre(txtGenre.getText());
            buku.setStatusBaca((String) comboStatus.getSelectedItem());
            buku.setTanggalSelesai(tanggal);
            buku.setCatatanPribadi(txtCatatan.getText());
    
            updateTable();
            dialog.dispose();
        });
    
        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
    
    // Hapus Buku
    private void hapusBuku() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        // Tampilkan dialog konfirmasi
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus buku ini?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
    
        // Hapus data jika pengguna memilih "Yes"
        if (confirm == JOptionPane.YES_OPTION) {
            daftarBuku.remove(selectedRow);
            updateTable();
            JOptionPane.showMessageDialog(this, "Buku berhasil dihapus.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Search Buku
    private void searchBuku() {
        String keyword = txtSearch.getText().toLowerCase();
        if (keyword.isEmpty()) {
            updateTable();
            return;
        }

        List<Buku> filteredBuku = new ArrayList<>();
        for (Buku buku : daftarBuku) {
            if (buku.getJudul().toLowerCase().contains(keyword) ||
                buku.getPenulis().toLowerCase().contains(keyword) ||
                buku.getGenre().toLowerCase().contains(keyword)) {
                filteredBuku.add(buku);
            }
        }

        updateTable(filteredBuku);
    }

    private void resetSearch() {
        txtSearch.setText("");
        updateTable();
    }

    private void updateTable() {
        updateTable(daftarBuku);
    }

    private void updateTable(List<Buku> bukuList) {
        tableModel.setRowCount(0);
        for (Buku buku : bukuList) {
            tableModel.addRow(new Object[]{
                buku.getJudul(), buku.getPenulis(), buku.getGenre(), buku.getStatusBaca(), buku.getTanggalSelesai(), buku.getCatatanPribadi()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Hanya menampilkan WelcomeScreen saat aplikasi dimulai
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true);
        });
    }
}


