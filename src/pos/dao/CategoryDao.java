package pos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pos.model.Category;
import pos.service.DaoService;
import pos.util.DbUtil;

public class CategoryDao implements DaoService<Category> {

    DbUtil db = new DbUtil();
    PreparedStatement ps;
    ResultSet rs;
    String sql;

    @Override
    public void save(Category e) {

        sql = "insert into category(name) values(?)";
        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, e.getName());

            ps.executeUpdate();

            ps.close();
            db.getCon().close();

            JOptionPane.showMessageDialog(null, "category Saved");

            System.out.println("Done");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "category not Save");
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Category> findAll() {
        List<Category> cList = new ArrayList<>();
        sql = "Select id, name from category order by id";
        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category s = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                cList.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cList;
    }

    @Override
    public void update(Category e) {
        sql = "update category set name = ? where id = ?";

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, e.getName());
            ps.setInt(2, e.getId());

            ps.executeUpdate();

            ps.close();
            db.getCon().close();

            JOptionPane.showMessageDialog(null, "category Updated");

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "category not Update");
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Category findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        sql = "delete from category where id = ?";

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
            db.getCon().close();

            JOptionPane.showMessageDialog(null, "category Deleted");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "category not Delete");

            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<String> getAllCategoryName() {
        List<String> list = new ArrayList<>();
        sql = "Select name from category";

        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
            ps.close();
            rs.close();
            db.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getIdByName(String categoryName) {

        sql = "Select id from category where name = ?";

        int id=0;

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, categoryName);

            rs = ps.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }

            ps.close();
            rs.close();
            db.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

}
