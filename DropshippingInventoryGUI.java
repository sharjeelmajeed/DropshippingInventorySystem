import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class DropshippingInventoryGUI extends JFrame {
    private DropshippingInventorySystem system;
    private JTabbedPane tabbedPane;
    private JTable suppliersTable, productsTable, inventoryTable, ordersTable;

    public DropshippingInventoryGUI() {
        system = new DropshippingInventorySystem();
        createUI();
        setTitle("Dropshipping Inventory System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUI() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        tabbedPane.addTab("📞 Suppliers", createSuppliersPanel());
        tabbedPane.addTab("📦 Products", createProductsPanel());
        tabbedPane.addTab("📊 Inventory", createInventoryPanel());
        tabbedPane.addTab("🚚 Orders", createOrdersPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createSuppliersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 240, 240));
        
        suppliersTable = new JTable();
        suppliersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suppliersTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        suppliersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        suppliersTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(suppliersTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Supplier List"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton addBtn = createStyledButton("Add Supplier", new Color(46, 204, 113));
        JButton editBtn = createStyledButton("Edit Supplier", new Color(52, 152, 219));
        JButton deleteBtn = createStyledButton("Delete Supplier", new Color(231, 76, 60));
        JButton refreshBtn = createStyledButton("Refresh Data", new Color(149, 165, 166));
        
        addBtn.addActionListener(e -> showAddEditSupplierDialog(null));
        editBtn.addActionListener(e -> editSelectedSupplier());
        deleteBtn.addActionListener(e -> deleteSelectedSupplier());
        refreshBtn.addActionListener(e -> refreshSuppliers());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        refreshSuppliers();
        return panel;
    }

    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 240, 240));
        
        productsTable = new JTable();
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        productsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        productsTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Product Catalog"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton addBtn = createStyledButton("Add Product", new Color(46, 204, 113));
        JButton editBtn = createStyledButton("Edit Product", new Color(52, 152, 219));
        JButton deleteBtn = createStyledButton("Delete Product", new Color(231, 76, 60));
        JButton refreshBtn = createStyledButton("Refresh Data", new Color(149, 165, 166));
        
        addBtn.addActionListener(e -> showAddEditProductDialog(null));
        editBtn.addActionListener(e -> editSelectedProduct());
        deleteBtn.addActionListener(e -> deleteSelectedProduct());
        refreshBtn.addActionListener(e -> refreshProducts());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        refreshProducts();
        return panel;
    }

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 240, 240));
        
        inventoryTable = new JTable();
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inventoryTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        inventoryTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Inventory"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton updateBtn = createStyledButton("Update Stock", new Color(52, 152, 219));
        JButton refreshBtn = createStyledButton("Refresh Data", new Color(149, 165, 166));
        
        updateBtn.addActionListener(e -> showUpdateInventoryDialog());
        refreshBtn.addActionListener(e -> refreshInventory());
        
        buttonPanel.add(updateBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        refreshInventory();
        return panel;
    }

    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 240, 240));
        
        ordersTable = new JTable();
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ordersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        ordersTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Order History"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton placeBtn = createStyledButton("Place New Order", new Color(46, 204, 113));
        JButton refreshBtn = createStyledButton("Refresh Data", new Color(149, 165, 166));
        
        placeBtn.addActionListener(e -> showPlaceOrderDialog());
        refreshBtn.addActionListener(e -> refreshOrders());
        
        buttonPanel.add(placeBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        refreshOrders();
        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
    JButton button = new JButton(text) {
        // Add hover effect
        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isRollover()) {
                setBackground(bgColor.darker());
            } else {
                setBackground(bgColor);
            }
            super.paintComponent(g);
        }
    };
    
    button.setBackground(bgColor);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
    // Force custom styling
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setOpaque(true);
    
    // Add padding and border
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(bgColor.darker(), 2),
        BorderFactory.createEmptyBorder(10, 25, 10, 25)
    ));
    
    button.setMinimumSize(new Dimension(180, 50));
    button.setPreferredSize(new Dimension(200, 50));
    
    // Add hover effect listener
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(bgColor.darker());
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(bgColor);
        }
    });
    
    return button;
}

    private void refreshSuppliers() {
        try {
            ResultSet rs = system.getSuppliers();
            suppliersTable.setModel(buildTableModel(rs));
        } catch (SQLException ex) {
            showError("Error loading suppliers: " + ex.getMessage());
        }
    }

    private void refreshProducts() {
        try {
            ResultSet rs = system.getProducts();
            productsTable.setModel(buildTableModel(rs));
        } catch (SQLException ex) {
            showError("Error loading products: " + ex.getMessage());
        }
    }

    private void refreshInventory() {
        try {
            ResultSet rs = system.getInventory();
            inventoryTable.setModel(buildTableModel(rs));
        } catch (SQLException ex) {
            showError("Error loading inventory: " + ex.getMessage());
        }
    }

    private void refreshOrders() {
        try {
            ResultSet rs = system.getOrders();
            ordersTable.setModel(buildTableModel(rs));
        } catch (SQLException ex) {
            showError("Error loading orders: " + ex.getMessage());
        }
    }

    private void showAddEditSupplierDialog(Supplier supplier) {
        JTextField nameField = new JTextField(25);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField contactField = new JTextField(25);
        contactField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        if (supplier != null) {
            nameField.setText(supplier.name);
            contactField.setText(supplier.contactInfo);
        }

        JPanel panel = createFormPanel(
            new String[]{"Name:", "Contact Info:"},
            new JComponent[]{nameField, contactField}
        );

        String title = supplier == null ? "Add New Supplier" : "Edit Supplier";
        int result = JOptionPane.showConfirmDialog(this, panel, title, 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            if (supplier == null) {
                system.addSupplier(nameField.getText().trim(), contactField.getText().trim());
            } else {
                system.updateSupplier(supplier.id, nameField.getText().trim(), contactField.getText().trim());
            }
            refreshSuppliers();
        }
    }

    private void showAddEditProductDialog(Product product) {
        JTextField nameField = new JTextField(25);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField skuField = new JTextField(25);
        skuField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextArea descArea = new JTextArea(3, 25);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descArea.setLineWrap(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        JTextField priceField = new JTextField(10);
        priceField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        Vector<Supplier> suppliers = system.getSupplierList();
        JComboBox<Supplier> supplierCombo = new JComboBox<>(suppliers);
        supplierCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        supplierCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Supplier) {
                    setText(((Supplier) value).name);
                }
                return this;
            }
        });
        
        if (product != null) {
            nameField.setText(product.name);
            skuField.setText(product.sku);
            descArea.setText(product.description);
            priceField.setText(String.valueOf(product.price));
            for (int i = 0; i < suppliers.size(); i++) {
                if (suppliers.get(i).id == product.supplierId) {
                    supplierCombo.setSelectedIndex(i);
                    break;
                }
            }
        }

        JPanel panel = createFormPanel(
            new String[]{"Product Name:", "SKU:", "Description:", "Supplier:", "Price:"},
            new JComponent[]{nameField, skuField, descScroll, supplierCombo, priceField}
        );

        String title = product == null ? "Add New Product" : "Edit Product";
        int result = JOptionPane.showConfirmDialog(this, panel, title, 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String sku = skuField.getText().trim();
            String description = descArea.getText().trim();
            Supplier selectedSupplier = (Supplier) supplierCombo.getSelectedItem();
            double price = Double.parseDouble(priceField.getText().trim());
            
            if (product == null) {
                system.addProduct(name, sku, description, selectedSupplier.id, price);
            } else {
                system.updateProduct(product.id, name, sku, description, selectedSupplier.id, price);
            }
            refreshProducts();
            refreshInventory();
        }
    }

    // FIXED: Proper inventory update handling
    private void showUpdateInventoryDialog() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Please select a product first");
            return;
        }
        
        // Handle different numeric types
        Object idObj = inventoryTable.getValueAt(selectedRow, 0);
        Object nameObj = inventoryTable.getValueAt(selectedRow, 1);
        Object stockObj = inventoryTable.getValueAt(selectedRow, 2);
        
        int productId;
        String productName;
        int currentStock;
        
        try {
            // Convert to proper types
            if (idObj instanceof Number) {
                productId = ((Number) idObj).intValue();
            } else {
                productId = Integer.parseInt(idObj.toString());
            }
            
            if (nameObj instanceof String) {
                productName = (String) nameObj;
            } else {
                productName = nameObj.toString();
            }
            
            if (stockObj instanceof Number) {
                currentStock = ((Number) stockObj).intValue();
            } else {
                currentStock = Integer.parseInt(stockObj.toString());
            }
        } catch (Exception e) {
            showError("Error reading product data: " + e.getMessage());
            return;
        }

        JSpinner stockSpinner = new JSpinner(new SpinnerNumberModel(currentStock, 0, 10000, 1));
        JComponent editor = stockSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        
        JPanel panel = createFormPanel(
            new String[]{"Product:", "Current Stock:", "New Stock:"},
            new JComponent[]{new JLabel(productName), new JLabel(String.valueOf(currentStock)), stockSpinner}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Inventory", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            int newStock = (Integer) stockSpinner.getValue();
            system.setInventory(productId, newStock);
            refreshInventory();
            JOptionPane.showMessageDialog(this, "Stock updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showPlaceOrderDialog() {
        Vector<Product> products = system.getProductList();
        if (products.isEmpty()) {
            showError("No products available to order");
            return;
        }
        
        JComboBox<Product> productCombo = new JComboBox<>(products);
        productCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        productCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Product) {
                    setText(((Product) value).name);
                }
                return this;
            }
        });
        
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JComponent editor = quantitySpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        
        JPanel panel = createFormPanel(
            new String[]{"Product:", "Quantity:"},
            new JComponent[]{productCombo, quantitySpinner}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Place New Order", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            Product selectedProduct = (Product) productCombo.getSelectedItem();
            int quantity = (Integer) quantitySpinner.getValue();
            
            try {
                system.placeOrder(selectedProduct.id, quantity);
                refreshOrders();
                refreshInventory();
                JOptionPane.showMessageDialog(this, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                showError("Error placing order: " + ex.getMessage());
            }
        }
    }

    private JPanel createFormPanel(String[] labels, JComponent[] fields) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 15, 10, 15);
        
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(label, gbc);
            
            gbc.gridx = 1;
            panel.add(fields[i], gbc);
        }
        
        return panel;
    }

    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            data.add(row);
        }
        
        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Automatically detect column types for proper rendering
                if (getRowCount() > 0) {
                    Object value = getValueAt(0, columnIndex);
                    if (value != null) {
                        return value.getClass();
                    }
                }
                return Object.class;
            }
        };
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void editSelectedSupplier() {
        int selectedRow = suppliersTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Please select a supplier first");
            return;
        }
        
        int id = (int) suppliersTable.getValueAt(selectedRow, 0);
        String name = (String) suppliersTable.getValueAt(selectedRow, 1);
        String contact = (String) suppliersTable.getValueAt(selectedRow, 2);
        
        showAddEditSupplierDialog(new Supplier(id, name, contact));
    }
    
    private void deleteSelectedSupplier() {
        int selectedRow = suppliersTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Please select a supplier first");
            return;
        }
        
        int id = (int) suppliersTable.getValueAt(selectedRow, 0);
        String name = (String) suppliersTable.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Delete supplier '" + name + "'? This will remove all associated products.", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            system.deleteSupplier(id);
            refreshSuppliers();
            refreshProducts();
            refreshInventory();
        }
    }
    
    private void editSelectedProduct() {
    int selectedRow = productsTable.getSelectedRow();
    if (selectedRow < 0) {
        showError("Please select a product first");
        return;
    }
    
    int id = (int) productsTable.getValueAt(selectedRow, 0);
    String name = (String) productsTable.getValueAt(selectedRow, 1);
    String sku = (String) productsTable.getValueAt(selectedRow, 2);
    String desc = (String) productsTable.getValueAt(selectedRow, 3);
    String supplierName = (String) productsTable.getValueAt(selectedRow, 4);
    Object priceObj = productsTable.getValueAt(selectedRow, 5);
    
    double price = 0.0;
    if (priceObj instanceof Number) {
        price = ((Number) priceObj).doubleValue();
    } else {
        try {
            price = Double.parseDouble(priceObj.toString());
        } catch (Exception e) {
            showError("Invalid price value: " + priceObj);
            return;
        }
    }
    
    int supplierId = system.getSupplierIdByName(supplierName);
    
    showAddEditProductDialog(new Product(id, name, sku, desc, supplierId, price));
}
    
    private void deleteSelectedProduct() {
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Please select a product first");
            return;
        }
        
        int id = (int) productsTable.getValueAt(selectedRow, 0);
        String name = (String) productsTable.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Delete product '" + name + "'? This will remove all inventory and order history.", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            system.deleteProduct(id);
            refreshProducts();
            refreshInventory();
            refreshOrders();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
                UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
                UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
                UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));
                UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));
                UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 14));
                UIManager.put("TitledBorder.font", new Font("Segoe UI", Font.BOLD, 14));
                UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
            } catch (Exception e) {
                e.printStackTrace();
            }
            new DropshippingInventoryGUI();
        });
    }
}

class DropshippingInventorySystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dropshipping_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "ideapadflex5";

    private Connection conn;

    public DropshippingInventorySystem() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Connected to MySQL successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

    // Supplier management
    public void addSupplier(String name, String contactInfo) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO Suppliers (name, contact_info) VALUES (?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, contactInfo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding supplier: " + e.getMessage());
        }
    }
    
    public void updateSupplier(int id, String name, String contactInfo) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Suppliers SET name = ?, contact_info = ? WHERE id = ?")) {
            pstmt.setString(1, name);
            pstmt.setString(2, contactInfo);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating supplier: " + e.getMessage());
        }
    }
    
    public void deleteSupplier(int id) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM Suppliers WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting supplier: " + e.getMessage());
        }
    }
    
    public Vector<Supplier> getSupplierList() {
        Vector<Supplier> suppliers = new Vector<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, contact_info FROM Suppliers")) {
            while (rs.next()) {
                suppliers.add(new Supplier(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("contact_info")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting suppliers: " + e.getMessage());
        }
        return suppliers;
    }
    
    public int getSupplierIdByName(String name) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT id FROM Suppliers WHERE name = ?")) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting supplier ID: " + e.getMessage());
        }
    }

    // Product management
    public void addProduct(String name, String sku, String description, int supplierId, double price) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO Products (name, sku, description, supplier_id, price) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, sku);
            pstmt.setString(3, description);
            pstmt.setInt(4, supplierId);
            pstmt.setDouble(5, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding product: " + e.getMessage());
        }
    }
    
    public void updateProduct(int id, String name, String sku, String description, int supplierId, double price) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Products SET name = ?, sku = ?, description = ?, supplier_id = ?, price = ? WHERE id = ?")) {
            pstmt.setString(1, name);
            pstmt.setString(2, sku);
            pstmt.setString(3, description);
            pstmt.setInt(4, supplierId);
            pstmt.setDouble(5, price);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product: " + e.getMessage());
        }
    }
    
    public void deleteProduct(int id) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM Products WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product: " + e.getMessage());
        }
    }
    
    public Vector<Product> getProductList() {
        Vector<Product> products = new Vector<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, sku, description, supplier_id, price FROM Products")) {
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("sku"),
                    rs.getString("description"),
                    rs.getInt("supplier_id"),
                    rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting products: " + e.getMessage());
        }
        return products;
    }

    // Inventory management - FIXED
    public void setInventory(int productId, int stock) {
        try {
            // Check if record exists
            boolean exists = false;
            PreparedStatement checkStmt = conn.prepareStatement("SELECT 1 FROM Inventory WHERE product_id = ?");
            checkStmt.setInt(1, productId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                exists = true;
            }
            checkStmt.close();
            
            if (exists) {
                // Update existing record
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE Inventory SET stock = ? WHERE product_id = ?");
                updateStmt.setInt(1, stock);
                updateStmt.setInt(2, productId);
                updateStmt.executeUpdate();
                updateStmt.close();
            } else {
                // Insert new record
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO Inventory (product_id, stock) VALUES (?, ?)");
                insertStmt.setInt(1, productId);
                insertStmt.setInt(2, stock);
                insertStmt.executeUpdate();
                insertStmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating inventory: " + e.getMessage());
        }
    }
    
    public int getCurrentStock(int productId) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT stock FROM Inventory WHERE product_id = ?")) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock");
            }
            return 0;
        }
    }

    // Order management
    public void placeOrder(int productId, int quantity) {
        try {
            int stock = getCurrentStock(productId);
            if (stock < quantity) {
                throw new RuntimeException("Insufficient stock. Available: " + stock);
            }
            
            PreparedStatement updateStock = conn.prepareStatement(
                    "UPDATE Inventory SET stock = stock - ? WHERE product_id = ?");
            updateStock.setInt(1, quantity);
            updateStock.setInt(2, productId);
            updateStock.executeUpdate();
            updateStock.close();
            
            PreparedStatement insertOrder = conn.prepareStatement(
                    "INSERT INTO Orders (product_id, quantity, order_date, status) VALUES (?, ?, NOW(), ?)");
            insertOrder.setInt(1, productId);
            insertOrder.setInt(2, quantity);
            insertOrder.setString(3, "Placed");
            insertOrder.executeUpdate();
            insertOrder.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error placing order: " + e.getMessage());
        }
    }

    // Data retrieval methods for GUI
    public ResultSet getSuppliers() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT id AS 'ID', name AS 'Name', contact_info AS 'Contact Info' FROM Suppliers");
    }

    public ResultSet getProducts() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT p.id AS 'ID', p.name AS 'Name', p.sku AS 'SKU', " +
                "p.description AS 'Description', s.name AS 'Supplier', p.price AS 'Price' " +
                "FROM Products p LEFT JOIN Suppliers s ON p.supplier_id = s.id");
    }

    public ResultSet getInventory() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT p.id AS 'Product ID', p.name AS 'Product Name', " +
                "COALESCE(i.stock, 0) AS 'Current Stock' " +
                "FROM Products p LEFT JOIN Inventory i ON p.id = i.product_id");
    }

    public ResultSet getOrders() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT o.id AS 'Order ID', p.name AS 'Product', o.quantity AS 'Quantity', " +
                "DATE_FORMAT(o.order_date, '%Y-%m-%d %H:%i') AS 'Order Date', o.status AS 'Status' " +
                "FROM Orders o LEFT JOIN Products p ON o.product_id = p.id " +
                "ORDER BY o.order_date DESC");
    }
    
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing DB connection: " + e.getMessage());
        }
    }
}

class Supplier {
    int id;
    String name;
    String contactInfo;
    
    public Supplier(int id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }
    
    @Override
    public String toString() {
        return name;
    }
}

class Product {
    int id;
    String name;
    String sku;
    String description;
    int supplierId;
    double price;
    
    public Product(int id, String name, String sku, String description, int supplierId, double price) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.description = description;
        this.supplierId = supplierId;
        this.price = price;
    }
    
    @Override
    public String toString() {
        return name;
    }
}