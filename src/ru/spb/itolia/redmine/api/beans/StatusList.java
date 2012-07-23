package ru.spb.itolia.redmine.api.beans;

import java.util.ArrayList;
import java.util.Iterator;

public class StatusList<Object> extends ArrayList<IssueStatus> {
	
	private static final long serialVersionUID = 1L;

	public IssueStatus getStatusById(String id) {
		IssueStatus st = null;
		Iterator<IssueStatus> iter = this.iterator();
		while (iter.hasNext()) {
			st = iter.next();
			if (st.getId() == id) {
				return st;
			}
			return null;

		}
		return null;
	}
}
