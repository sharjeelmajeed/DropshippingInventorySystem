# Dropshipping Inventory System

## 📁 How to Run

1. Import the database:
   - Open MySQL Workbench
   - Go to Server > Data Import
   - Choose "Import from Self-Contained File"
   - Select `dropshipping_inventory.sql`
   - Import into a new schema: `dropshipping_inventory`

2. Open the Java project:
   - Make sure MySQL JDBC driver (.jar) is in the lib folder
   - Compile and run `DropshippingInventoryGUI.java`

## ⚠️ Notes

- Make sure MySQL is running and you have access to the database.
- Update the DB credentials in the Java code if needed (e.g., `username`, `password`).
