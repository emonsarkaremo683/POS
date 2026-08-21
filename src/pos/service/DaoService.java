/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pos.service;

import java.util.List;

/**
 *
 * @author Admin
 */
public interface DaoService<E> {

    void save(E e);

    List<E> findAll();

    void update(E e );

    E findById(int id);

    void delete(int id);

}
