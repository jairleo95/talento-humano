/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.util;

/**
 *
 * @author santjair
 */
public class Sql {

    private static final String ROW_NUMBER_COLUMN = " row_number() over (order by %s desc) row_number ";
    private static final String ROWNUM_CLAUSE = " AND rownum < ((%d * %d) + 1) ";

    public static String queryWithPagination(String query, int pageNumber, int pageSize, String orderby) {
        int safePage = Math.max(pageNumber, 1);
        int safeSize = Math.max(pageSize, 1);
        String safeOrderBy = (orderby == null) ? "" : orderby.replaceAll("[^a-zA-Z0-9_ ]", "");
        String rowNumber = String.format(ROW_NUMBER_COLUMN, safeOrderBy);
        String rownum = String.format(ROWNUM_CLAUSE, safePage, safeSize);
        return String.format(" SELECT * FROM(  " + query + "  ) WHERE row_number >= ((%d-1) * %d) + 1", safePage, safeSize, rowNumber, rownum);
    }
}
