package com.pf.common.vo.pc;

import java.util.Comparator;

public class LevelCompartor implements Comparator<AppUserContactVo>{
   private Integer level;
   
   
	public Integer getLevel() {
	return level;
}


public void setLevel(Integer level) {
	this.level = level;
} 

	@Override
	public int compare(AppUserContactVo o1, AppUserContactVo o2) {
		return o1.getLevel().compareTo(o2.getLevel()); 
	}
}
