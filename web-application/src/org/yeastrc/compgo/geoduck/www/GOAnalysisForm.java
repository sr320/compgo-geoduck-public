package org.yeastrc.compgo.geoduck.www;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GOAnalysisForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1098075872764114617L;

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		return errors;
	}
	
	
	String proteinList1;
	String proteinList2;
	String cutoff = "1E-1";
	String aspect;
	String type = "image";
	String accessionSource = "uniprot";
	
	
	public String getProteinList1() {
		return proteinList1;
	}
	public void setProteinList1(String proteinList1) {
		this.proteinList1 = proteinList1;
	}
	public String getProteinList2() {
		return proteinList2;
	}
	public void setProteinList2(String proteinList2) {
		this.proteinList2 = proteinList2;
	}
	public String getCutoff() {
		return cutoff;
	}
	public void setCutoff(String cutoff) {
		this.cutoff = cutoff;
	}
	public String getAspect() {
		return aspect;
	}
	public void setAspect(String aspect) {
		this.aspect = aspect;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccessionSource() {
		return accessionSource;
	}
	public void setAccessionSource(String accessionSource) {
		this.accessionSource = accessionSource;
	}
	
	

}
