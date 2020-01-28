package com.adaptive.common.dao.tableOfValues;

import java.sql.SQLException;
import java.util.List;

import com.adaptive.common.dao.GenericDAO;

public interface TableOfValuesDAO extends GenericDAO<Object, Long>{
	public List<Object[]> getListTableOfValues(String sql) throws SQLException ;

}
