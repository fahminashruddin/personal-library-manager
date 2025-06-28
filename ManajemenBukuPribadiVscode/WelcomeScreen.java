import java.awt.*;
import java.io.InputStream;
import java.util.Random;
import javax.swing.*;

public class WelcomeScreen extends JFrame {
    private static final String[] QUOTES = {
        "Buku adalah jendela dunia!",
        "Membaca satu buku membuka seribu pintu pengetahuan.",
        "Dalam setiap halaman buku, ada petualangan baru menanti.",
        "Membaca adalah napas kehidupan pembelajaran.",
        "Buku adalah teman setia yang tak pernah mengkhianati."
    };
    
    private Font customFontTitle;
    private Font customFontText;

    public WelcomeScreen() {
        setTitle("Selamat Datang");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load custom fonts
        loadCustomFonts();

        // Panel utama dengan background image
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw background image
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/buku_background.jpeg"));
                Image image = imageIcon.getImage();
                
                // Draw image with semi-transparent overlay
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                
                // Add semi-transparent overlay
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 180)); // Semi-transparent black
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Welcome text dengan custom font
        JLabel welcomeLabel = new JLabel("Manajemen Buku Pribadi");
        welcomeLabel.setFont(customFontTitle.deriveFont(38f));
        welcomeLabel.setForeground(new Color(255, 218, 185));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appLabel = new JLabel("Selamat Datang!");
        appLabel.setFont(customFontTitle.deriveFont(32f));
        appLabel.setForeground(Color.WHITE);
        appLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Random quote dengan custom font
        String randomQuote = QUOTES[new Random().nextInt(QUOTES.length)];
        JLabel quoteLabel = new JLabel("<html><div style='text-align: center;'>" + randomQuote + "</div></html>");
        quoteLabel.setFont(customFontText.deriveFont(Font.ITALIC, 20f));
        quoteLabel.setForeground(new Color(255, 240, 245));
        quoteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quoteLabel.setPreferredSize(new Dimension(400, 50));
        quoteLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Masuk button dengan styling yang lebih menarik
        JButton enterButton = new JButton("Masuk");
        enterButton.setFont(customFontText.deriveFont(Font.BOLD, 18f));
        enterButton.setBackground(new Color(255, 182, 193, 200));
        enterButton.setForeground(Color.WHITE);
        enterButton.setFocusPainted(false);
        enterButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 182, 193)),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setMaximumSize(new Dimension(180, 50));

        // Hover effect untuk button
        enterButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                enterButton.setBackground(new Color(255, 105, 180, 200));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                enterButton.setBackground(new Color(255, 182, 193, 200));
            }
        });

        enterButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                ManajemenBukuGUI mainApp = new ManajemenBukuGUI();
                mainApp.setVisible(true);
            });
        });

        // Add components with spacing
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(appLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(quoteLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(enterButton);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);
    }

    private void loadCustomFonts() {
        try {
            // Load custom fonts
            InputStream isTitleFont = getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf");
            InputStream isTextFont = getClass().getResourceAsStream("/fonts/Raleway-Regular.ttf");
            
            customFontTitle = Font.createFont(Font.TRUETYPE_FONT, isTitleFont);
            customFontText = Font.createFont(Font.TRUETYPE_FONT, isTextFont);
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFontTitle);
            ge.registerFont(customFontText);
        } catch (Exception e) {
            // Fallback fonts if custom fonts fail to load
            customFontTitle = new Font("Segoe UI", Font.BOLD, 32);
            customFontText = new Font("Segoe UI", Font.PLAIN, 16);
            e.printStackTrace();
        }
    }
}