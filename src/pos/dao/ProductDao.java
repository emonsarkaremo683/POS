package pos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pos.model.Product;
import pos.service.DaoService;
import pos.util.DbUtil;

/**
 *
 * @author Admin
 */
public class ProductDao implements DaoService<Product> {

    DbUtil db = new DbUtil();
    PreparedStatement ps;
    ResultSet rs;
    String sql;

    @Override
    public void save(Product e) {
        sql = "insert into product(name, price,quantity,supplierId, categoryId) values(?,?,?,?,?)";
        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, e.getName());
            ps.setDouble(2, e.getPrice());
            ps.setDouble(3, e.getQuantity());
            ps.setInt(4, e.getSupplierId());
            ps.setInt(5, e.getCategoryId());

            ps.executeUpdate();

            ps.close();
            db.getCon().close();

            JOptionPane.showMessageDialog(null, "Product Saved");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Product Not Save");

            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        sql = "Select p.id, p.name, p.price, p.quantity, c.name, s.name from product p "
                + "join supplier s on s.id= p.supplierId "
                + "join category c on c.id= p.categoryId ";

        try {
            ps = db.getCon().prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDouble("quantity"),
                        rs.getString("s.name"),
                        rs.getString("c.name")
                );

                System.out.println(p);

                list.add(p);
            }

            ps.close();
            rs.close();
            db.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    @Override
    public void update(Product e) {
        sql = "Update product set name = ?, price=?, quantity =?, supplierId =?, categoryId=? where id =?";
        try {
            ps = db.getCon().prepareStatement(sql);

            ps.setString(1, e.getName());
            ps.setDouble(2, e.getPrice());
            ps.setDouble(3, e.getQuantity());
            ps.setInt(4, e.getSupplierId());
            ps.setInt(5, e.getCategoryId());
            ps.setInt(6, e.getId());

            ps.executeUpdate();

            ps.close();
            db.getCon().close();
            JOptionPane.showMessageDialog(null, "Product Updated");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Product not Updated");

            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Product findById(int id) {
        Product p = null;
        sql = " select * from product where id =?";
        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {

                p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDouble("quantity"),
                        rs.getInt("supplierId"),
                        rs.getInt("categoryId")
                );

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public void delete(int id) {

        sql = "delete from product where id = ?";
        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
            db.getCon().close();
            JOptionPane.showMessageDialog(null, "Product deleted");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Product not deleted");

            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<String> getAllProductName() {

        List<String> productNames = new ArrayList<>();
        sql = "select name from product";
        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                String name = rs.getString("name");
                productNames.add(name);

            }

            rs.close();
            ps.close();
            db.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productNames;
    }

    public Integer findIdByName(String name) {
        Integer id = null;
        sql = "SELECT id FROM product WHERE name = ?";
        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, name);

            rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }

            rs.close();
            ps.close();
            db.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id; // returns null if not found
    }

}
