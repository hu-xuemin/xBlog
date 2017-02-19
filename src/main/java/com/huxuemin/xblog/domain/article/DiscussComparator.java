package com.huxuemin.xblog.domain.article;

import java.util.Comparator;

public class DiscussComparator implements Comparator<Discuss> {

	@Override
	public int compare(Discuss discuss1, Discuss discuss2) {
		// TODO Auto-generated method stub
		if (discuss2 == null) {
			return 1;
		}
		if (discuss1 == null) {
			return -1;
		}
		Discuss d1 = (Discuss) discuss1;
		Discuss d2 = (Discuss) discuss2;
		if (d1.getDiscussId() > d2.getDiscussId()) {
			return 1;
		} else if (d1.getDiscussId() == d2.getDiscussId()) {
			return 0;
		} else {
			return -1;
		}
	}

}
