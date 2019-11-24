package com.williamssonoma.zipcoderanges.models;

import javax.validation.constraints.NotNull;

import com.williamssonoma.zipcoderanges.validation.ZipCode;

public class SubmissionDTO {
	
	@NotNull(message="{zipcode.lowziprequired}")
	@ZipCode(message="{zipcode.lowzipinvalid}")
	private Integer lowZipCode;
    
	@NotNull(message="{zipcode.highziprequired}")
	@ZipCode(message="{zipcode.highzipinvalid}")
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
