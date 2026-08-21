/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import pos.model.SalesItem;
import pos.util.DbUtil;

/**
 *
 * @author Admin
 */
public class SalesItemDAO {

    DbUtil db = new DbUtil();
    PreparedStatement ps;
    ResultSet rs;
    String sql;

    public void saveSalesItem(SalesItem item) {

        try {

            Connection con = db.getCon();

            sql = "INSERT INTO sales_items(sales_id,product_id,quantity,unit_price,total_price,discount_rate,discount,actual_price) VALUES(?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1, item.getSalesId());
            ps.setInt(2, item.getProductId());
            ps.setDouble(3, item.getQuantity());
            ps.setDouble(4, item.getUnitPrice());
            ps.setDouble(5, item.getTotalPrice());
            ps.setDouble(6, item.getDiscountRate());
            ps.setDouble(7, item.getDiscount());
            ps.setDouble(8, item.getActualPrice());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
