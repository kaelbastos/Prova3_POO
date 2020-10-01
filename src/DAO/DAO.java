package DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DAO <T, K> {
    void create(List<T> list) throws SQLException;
    void create(T t) throws SQLException;
    Map<K, T> importAsMap() throws SQLException;
    List<T> importAsList() throws SQLException;
    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;
}
