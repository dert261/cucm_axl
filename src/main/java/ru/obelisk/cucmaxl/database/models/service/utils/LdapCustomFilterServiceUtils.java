package ru.obelisk.cucmaxl.database.models.service.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.obelisk.cucmaxl.web.ui.datatables.ColumnDef;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.datatables.utils.StringUtils;

public class LdapCustomFilterServiceUtils {
	/**
	*
	* @param select
	* @param criterias
	* @return
	*/
	public static StringBuilder getFilterQuery(DatatablesCriterias criterias){
		StringBuilder queryBuilder = new StringBuilder();
		List<String> paramList = new ArrayList<String>();
		/**
		* Step 1.1: global filtering
		*/
		if (StringUtils.isNotBlank(criterias.getSearch()) && criterias.hasOneFilterableColumn()) {
			queryBuilder.append(" WHERE ");
			queryBuilder.append(" ( ");
			for (ColumnDef columnDef : criterias.getColumnDefs()) {
				if (!columnDef.getName().endsWith("Localized") && columnDef.isFilterable() && StringUtils.isBlank(columnDef.getSearch())) {
					paramList.add(" LOWER(ldapCustomFilter." + columnDef.getName()
							+ ") LIKE '%?%'".replace("?", criterias.getSearch().toLowerCase()));
				}
			}
			Iterator<String> itr = paramList.iterator();
			while (itr.hasNext()) {
				queryBuilder.append(itr.next());
				if (itr.hasNext()) {
					queryBuilder.append(" OR ");
				}
			}
			queryBuilder.append(" ) ");
		}
		
		/**
		* Step 1.2: individual column filtering
		*/
		if (criterias.hasOneFilterableColumn() && criterias.hasOneFilteredColumn()) {
			paramList = new ArrayList<String>();
			if(!queryBuilder.toString().contains("WHERE")){
				queryBuilder.append(" WHERE ");
			}
			else{
				queryBuilder.append(" AND ");
			}
			for (ColumnDef columnDef : criterias.getColumnDefs()) {
				if (columnDef.isFilterable()){
					if (StringUtils.isNotBlank(columnDef.getSearchFrom())) {
						if (columnDef.getName().equalsIgnoreCase("birthDate")) {
							paramList.add("ldapCustomFilter." + columnDef.getName() + " >= '" + columnDef.getSearchFrom() + "'");
						}
						else {
							paramList.add("ldapCustomFilter." + columnDef.getName() + " >= " + columnDef.getSearchFrom());
						}
					}
					if (StringUtils.isNotBlank(columnDef.getSearchTo())) {
						if (columnDef.getName().equalsIgnoreCase("birthDate")) {
							paramList.add("ldapCustomFilter." + columnDef.getName() + " < '" + columnDef.getSearchTo() + "'");
						}
						else {
							paramList.add("ldapCustomFilter." + columnDef.getName() + " < " + columnDef.getSearchTo());
						}
					}
					if(StringUtils.isNotBlank(columnDef.getSearch())) {
						paramList.add(" LOWER(ldapCustomFilter." + columnDef.getName()
								+ ") LIKE '%?%'".replace("?", columnDef.getSearch().toLowerCase()));
					}
				}
			}
			Iterator<String> itr = paramList.iterator();
			while (itr.hasNext()) {
				queryBuilder.append(itr.next());
				if (itr.hasNext()) {
					queryBuilder.append(" AND ");
				}
			}
		}
		
		
		/**
		* Step 1.3: Extra search individual column filtering
		*/
		if (criterias.hasOneFilterableColumn() && criterias.hasOneExtraFilteredColumn()) {
			paramList = new ArrayList<String>();
			if(!queryBuilder.toString().contains("WHERE")){
				queryBuilder.append(" WHERE ");
			}
			else{
				queryBuilder.append(" AND ");
			}
			for (ColumnDef columnDef : criterias.getColumnDefs()) {
				if (columnDef.isExtraFiltered()){
					
					if (!columnDef.getExtraSearch().isEmpty()) {
						
						if (columnDef.getName().equalsIgnoreCase("id")) {
							StringBuilder params = new StringBuilder();
							params.append("ldapCustomFilter." + columnDef.getName() + " in (");
														
							Iterator<String> extraIterator =  columnDef.getExtraSearch().iterator();
							while(extraIterator.hasNext()){
								params.append("'"+extraIterator.next()+"'");
								if(extraIterator.hasNext())
									params.append(",");
							}
							params.append(")");
							paramList.add(params.toString());
						}
					}
				}
			}
			Iterator<String> itr = paramList.iterator();
			while (itr.hasNext()) {
				queryBuilder.append(itr.next());
				if (itr.hasNext()) {
					queryBuilder.append(" AND ");
				}
			}
		}
		return queryBuilder;
	}
}
