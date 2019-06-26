package com.bolu.base.common;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@SuppressWarnings("deprecation")
public class PaginationHelper<E> {

	public CurrentPage<E> fetchPage(final JdbcTemplate jt,   final String sqlCountRows, final String sqlFetchRows,
                                    final Object args[], final int pageNo, final int pageSize,
                                    final RowMapper<E> rowMapper) {
		
		// determine how many rows are available
		final int rowCount = jt.queryForObject(sqlCountRows, args,Integer.class);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		// create the page object
		final CurrentPage<E> page = new CurrentPage<E>();
		page.setRowCount(rowCount);
		page.setPageNumber(pageNo);
		page.setPagesAvailable(pageCount);
		
//		// fetch a single page of results
//		final int startRow = (pageNo - 1) * pageSize;
//		jt.query(sqlFetchRows, args, new ResultSetExtractor() {
//			public Object extractData(ResultSet rs) throws SQLException,
//					DataAccessException {
//				final List pageItems = page.getPageItems();
//				int currentRow = 0;
//				while (rs.next() && currentRow < startRow + pageSize) {
//					if (currentRow >= startRow) {
//						pageItems.add(rowMapper.mapRow(rs, currentRow));
//					}
//					currentRow++;
//				}
//				return page;
//			}
//		});
		
		final int startRow = (pageNo - 1) * pageSize;
		final String reFetchRows = sqlFetchRows + " LIMIT "+startRow+","+pageSize;
		final List pageItems = jt.query(reFetchRows, args, rowMapper);
		page.setPageItems(pageItems);
		
		return page;
	}
}
