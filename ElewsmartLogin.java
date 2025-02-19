import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ElewsmartLogin {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private static final String FILE_NAME = "users.txt";

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // Username Field
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(usernameField);

        // Password Field
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(passwordField);

        // Buttons
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(100, 150, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(255, 100, 100));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));

        add(registerButton);
        add(loginButton);

        // Action Listeners
        registerButton.addActionListener(e -> registerUser());
        loginButton.addActionListener(e -> loginUser());

        setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + password);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving user data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loginUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new ElewsmartApp(); // Open the shopping app
                    dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading user data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class ElewsmartApp extends JFrame {
    private DefaultListModel<String> cartListModel;
    private DefaultListModel<String> itemListModel;
    private JLabel lb2;
    private JLabel imageLabel;

    public ElewsmartApp() {
        super("ElewsmartApp");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));

        Container content = getContentPane();
        content.setLayout(new BorderLayout(10, 10));

        // Items Panel
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        itemsPanel.setBackground(new Color(255, 255, 255));

       itemListModel = new DefaultListModel<>();
        itemListModel.addElement("Fried Rice - RM 8.90");
        itemListModel.addElement("Pasta - RM 12.90");
        itemListModel.addElement("Noodles - RM 7.00");
	itemListModel.addElement("Curry Udon - RM 12.90");
	itemListModel.addElement("Sizzling Chicken Chop - RM 15.90");
        itemListModel.addElement("Burger - RM 8.50");
        itemListModel.addElement("Pizza - RM 9.90");
        itemListModel.addElement("Coffee - RM 4.00");
	itemListModel.addElement("Latte - RM 4.30");
	itemListModel.addElement("Americano - RM 3.30");
        itemListModel.addElement("Tea - RM 3.00");
	itemListModel.addElement("Fruit Juice - RM 3.00");
        itemListModel.addElement("Coke - RM 3.50");
	itemListModel.addElement("Water - RM 1.00");


        JList<String> itemList = new JList<>(itemListModel);
        itemList.setFont(new Font("Arial", Font.PLAIN, 14));
        itemList.setSelectionBackground(new Color(100, 150, 255));
        itemList.setSelectionForeground(Color.WHITE);
        itemsPanel.add(new JScrollPane(itemList), BorderLayout.CENTER);

        // Cart Panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        cartPanel.setBackground(new Color(255, 255, 255));

        lb2 = new JLabel("Total: RM 0.00", JLabel.CENTER);
        lb2.setFont(new Font("Arial", Font.BOLD, 16));
        lb2.setForeground(new Color(0, 100, 0));
        cartPanel.add(lb2, BorderLayout.SOUTH);

        cartListModel = new DefaultListModel<>();
        JList<String> cartList = new JList<>(cartListModel);
        cartList.setFont(new Font("Arial", Font.PLAIN, 14));
        cartList.setSelectionBackground(new Color(255, 100, 100));
        cartList.setSelectionForeground(Color.WHITE);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        // Image Display Panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Selected Item Image"));
        imagePanel.setBackground(new Color(255, 255, 255));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton addButton = new JButton("Add to Cart");
        JButton removeButton = new JButton("Remove");
        JButton clearButton = new JButton("Clear");
        JButton finishOrderButton = new JButton("Finish Order");

        // Style Buttons
        addButton.setBackground(new Color(100, 150, 255));
        addButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(255, 100, 100));
        removeButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(255, 150, 100));
        clearButton.setForeground(Color.WHITE);
        finishOrderButton.setBackground(new Color(100, 200, 100));
        finishOrderButton.setForeground(Color.WHITE);

        // Add Action Listeners
        addButton.addActionListener(e -> {
            int[] selectedIndices = itemList.getSelectedIndices();
            for (int index : selectedIndices) {
                cartListModel.addElement(itemListModel.getElementAt(index));
            }
            calculateTotalAmount();
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = cartList.getSelectedIndex();
            if (selectedIndex != -1) {
                cartListModel.remove(selectedIndex);
                calculateTotalAmount();
            }
        });

        clearButton.addActionListener(e -> {
            cartListModel.clear();
            calculateTotalAmount();
        });

        finishOrderButton.addActionListener(e -> {
            double totalAmount = 0.0;
            for (int i = 0; i < cartListModel.size(); i++) {
                String item = cartListModel.getElementAt(i);
                totalAmount += extractPriceFromItem(item);
            }
            JOptionPane.showMessageDialog(this, "Order process successful!", "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
            new OrderConfirmationFrame(totalAmount, cartListModel);
        });

        // Update Image When Item is Selected
        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedItem = itemList.getSelectedValue();
                if (selectedItem != null) {
                    String imagePath = getImagePathForItem(selectedItem);
                    ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
                    if (icon.getImage() == null) {
                        icon = new ImageIcon(imagePath); // Fallback to direct path
                    }
                    imageLabel.setIcon(icon);
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(finishOrderButton);

        // Add Panels to Frame
        content.add(itemsPanel, BorderLayout.WEST);
        content.add(cartPanel, BorderLayout.CENTER);
        content.add(imagePanel, BorderLayout.EAST);
        content.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void calculateTotalAmount() {
        double totalAmount = 0.0;
        for (int i = 0; i < cartListModel.size(); i++) {
            String item = cartListModel.getElementAt(i);
            totalAmount += extractPriceFromItem(item);
        }
        lb2.setText("Total: RM" + String.format("%.2f", totalAmount));
    }

    private double extractPriceFromItem(String item) {
        int startIndex = item.lastIndexOf("RM") + 2;
        return Double.parseDouble(item.substring(startIndex));
    }

    private String getImagePathForItem(String item) {
        if (item.contains("Fried Rice")) return "/fried_rice.jpg";
        if (item.contains("Pasta")) return "/pasta.jpg";
        if (item.contains("Noodles")) return "/noodles.jpg";
        if (item.contains("Burger")) return "/burger.jpg";
        if (item.contains("Pizza")) return "/pizza.jpg";
        if (item.contains("Coffee")) return "/coffee.jpg";
        if (item.contains("Tea")) return "/tea.jpg";
        if (item.contains("Coke")) return "/coke.jpg";
	if (item.contains("Curry Udon")) return "/curry_udon.jpg";
	if (item.contains("Sizzling Chicken Chop")) return "/sizzling_chickenchop.jpg";
	if (item.contains("Fruit Juice")) return "/fruit_juice.jpg";
	if (item.contains("Water")) return "/water.jpg";
	if (item.contains("Latte")) return "/latte.jpg";
	if (item.contains("Americano")) return "/americano.jpg";
        return "/default.png"; // Fallback image
    }
}

class OrderConfirmationFrame extends JFrame {
    public OrderConfirmationFrame(double totalAmount, DefaultListModel<String> cartListModel) {
        setTitle("Order Status");
        setSize(600, 400);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // Title Label
        JLabel label = new JLabel("Your food is on the way!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(0, 100, 0));
        add(label, BorderLayout.NORTH);

        // Order List
        JList<String> orderList = new JList<>(cartListModel);
        orderList.setFont(new Font("Arial", Font.PLAIN, 14));
        orderList.setBackground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(orderList);
        add(scrollPane, BorderLayout.CENTER);

        // Total Cost Label
        JLabel totalLabel = new JLabel("Total Cost: RM" + String.format("%.2f", totalAmount), JLabel.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(new Color(0, 100, 0));
        add(totalLabel, BorderLayout.SOUTH);

        // Load Image
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/food.png")); // Try this first
            if (icon.getIconWidth() == -1) {
                icon = new ImageIcon("food.png"); // Fallback to direct path
            }
            JLabel imageLabel = new JLabel(icon);
            add(imageLabel, BorderLayout.EAST);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        setVisible(true);
    }
}