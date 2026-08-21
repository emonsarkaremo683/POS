# JEE Store POS

A desktop **Point of Sale (POS)** application built with **Java** and **Swing** (NetBeans GUI Builder), backed by a **MySQL** database. It manages products, categories, suppliers, and sales (invoices with line items, discounts, and totals).

> Application name shown in the UI: **"JEE Store"**.

---

## Features

- **User Login** — Simple username/password gate before accessing the dashboard.
- **Dashboard** — Central navigation screen with buttons for Product, Category, Sales, and Supplier modules.
- **Product Management (CRUD)**
  - Add, view, update, and delete products.
  - Each product has a name, price, quantity/stock, and is linked to a **category** and a **supplier**.
  - Product table displays supplier and category names (joined from related tables).
- **Category Management (CRUD)**
  - Add, view, update, and delete product categories.
  - Categories are referenced by products via dropdown selectors.
- **Supplier Management (CRUD)**
  - Add, view, update, and delete suppliers.
  - Stores supplier name, cell, contact person name/cell, and address.
- **Sales / Invoicing**
  - Create a sale with a customer cell number and auto-prefixed invoice number (`INV-...`).
  - Build a multi-line invoice by adding products with quantity, unit price, and discount rate.
  - Automatic calculation of line totals, discount amount, and actual (post-discount) price.
  - Running **grand total** across all invoice line items.
  - On save, the sale header (`sales`) and each line item (`sales_items`) are persisted to the database.
- **Database-Backed Persistence** — All data is stored in MySQL via JDBC (`mysql-connector-j`).

---

## Tech Stack

| Layer        | Technology                              |
|--------------|-----------------------------------------|
| Language     | Java (JDK 8+)                           |
| UI           | Java Swing (NetBeans `JFrame`/`.form`)  |
| Build        | Apache Ant (`build.xml`, NetBeans)      |
| Database     | MySQL                                   |
| DB Access    | JDBC (`com.mysql.cj.jdbc.Driver`)       |
| Drivers      | `lib/mysql-connector-j-9.6.0.jar`, `lib/sql.jar` |

---

## Project Structure

```
POS/
├── build.xml               # Ant build script (NetBeans)
├── manifest.mf             # JAR manifest
├── lib/                    # MySQL JDBC driver jars
├── src/pos/
│   ├── POS.java            # Application entry point (launches Login)
│   ├── model/              # Entity classes
│   │   ├── Product.java
│   │   ├── Category.java
│   │   ├── Supplier.java
│   │   ├── Sales.java
│   │   ├── SalesItem.java
│   │   └── User.java
│   ├── dao/                # Data Access Objects (JDBC)
│   │   ├── ProductDao.java
│   │   ├── CategoryDao.java
│   │   ├── SupplierDao.java
│   │   ├── SalesDao.java
│   │   ├── SalesItemDAO.java
│   │   └── UserDao.java
│   ├── service/
│   │   └── DaoService.java # Generic DAO interface (save/find/update/delete)
│   ├── util/
│   │   ├── DbUtil.java     # JDBC connection helper
│   │   └── SalesUtil.java  # Sales pricing math (total, discount, actual)
│   └── view/               # Swing GUI forms
│       ├── Login.java / .form
│       ├── DashBoard.java / .form
│       ├── ProductView.java / .form
│       ├── CategoryView.java / .form
│       ├── SupplierView.java / .form
│       └── SalesView.java / .form
└── test/                   # (empty)
```

---

## Database Setup

The app connects to a MySQL database named **`jeestore`** on `localhost:3306`.

Default connection settings are hardcoded in `src/pos/util/DbUtil.java`:

```java
url      = "jdbc:mysql://localhost:3306/jeestore";
user     = "root";
password = "1234";
driver   = "com.mysql.cj.jdbc.Driver";
```

1. Install and start MySQL.
2. Create the database:
   ```sql
   CREATE DATABASE jeestore;
   ```
3. Create the required tables:

```sql
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(100),
    password VARCHAR(100),
    role VARCHAR(50)
);

CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE supplier (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    cell VARCHAR(50),
    contactPersonName VARCHAR(100),
    contactPersonCell VARCHAR(50),
    address VARCHAR(255)
);

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    price DOUBLE,
    quantity DOUBLE,
    supplierId INT,
    categoryId INT,
    FOREIGN KEY (supplierId) REFERENCES supplier(id),
    FOREIGN KEY (categoryId) REFERENCES category(id)
);

CREATE TABLE sales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_no VARCHAR(100),
    sales_date DATE,
    customer_cell VARCHAR(50),
    total_amount DOUBLE
);

CREATE TABLE sales_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sales_id INT,
    product_id INT,
    quantity DOUBLE,
    unit_price DOUBLE,
    total_price DOUBLE,
    discount_rate DOUBLE,
    discount DOUBLE,
    actual_price DOUBLE,
    FOREIGN KEY (sales_id) REFERENCES sales(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);
```

---

## How to Run

### Prerequisites
- JDK 8 or newer
- MySQL Server running with the `jeestore` database and tables created
- MySQL JDBC driver (already in `lib/`)

### Using NetBeans
1. Open the project in NetBeans.
2. Ensure the MySQL connection details in `DbUtil.java` match your setup.
3. Right-click the project → **Run** (or press F6).

### Using Ant (command line)
From the project root:

```bash
ant clean jar
java -cp "dist/POS.jar;lib/*" pos.POS
```

> The `lib/*` classpath is required so the JDBC driver loads at runtime.

---

## How to Use

1. **Login** — Launch the app. Sign in with the hardcoded credentials:
   - Username: `java`
   - Password: `1234`
2. **Dashboard** — Click a module button:
   - **Product** — manage products (link each to a category + supplier).
   - **Category** — manage product categories.
   - **Supplier** — manage suppliers.
   - **Sales** — create invoices.
3. **Sales workflow**
   - Enter customer cell and an invoice number (saved as `INV-<number>`).
   - Select a product, enter unit price and quantity → total price auto-calculates.
   - Enter a discount rate (%) → discount amount and actual price auto-calculate.
   - Click **Add Item** to add the line to the invoice table.
   - Repeat for more items; the **Total Amount** updates automatically.
   - Click **Sales** to save the invoice and its line items to the database.

> Tip: Create at least one **Category** and one **Supplier** before adding **Products**, since products require both.

---

## Notes & Limitations

- Login credentials (`java` / `1234`) and DB credentials are **hardcoded** in `DbUtil.java` and `Login.java` — not secure for production.
- There is no user registration or role-based access (the `User` model has a fixed `Admin` role and `UserDao` only implements `save`).
- Several DAO methods (`findById`, `UserDao.findAll/update/delete`) are stubbed with `UnsupportedOperationException`.
- No automated tests are included (`test/` is empty).
- Each DAO opens and closes its own DB connection per operation.

---

## Author

Original project generated in NetBeans (`@author Admin`).
