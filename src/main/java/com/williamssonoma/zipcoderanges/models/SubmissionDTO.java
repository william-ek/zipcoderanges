package com.williamssonoma.zipcoderanges.models;

import org.springframework.stereotype.Component;

@Component
public class SubmissionDTO {
	
	private Integer lowZipCode;
    
	private Integer highZipCode;

	public Integer getLowZipCode() {
		return lowZipCode;
	}

	public void setLowZipCode(Integer lowZipCode) {
		this.lowZipCode = lowZipCode;
	}

	public Integer getHighZipCode() {
		return highZipCode;
	}

	public void setHighZipCode(Integer highZipCode) {
		this.highZipCode = highZipCode;
	}
	
	public String toJson() {
		
		return "{ " + "\"lowZipCode\"" + ": " + lowZipCode + ", " + "\"highZipCode\"" + ": " + highZipCode + " }";
	}


}
