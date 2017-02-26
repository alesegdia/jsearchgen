package com.alesegdia.jsearchgen.experiment;

public class HTMLTableMaker {

	String content = "";
	
	public void initHead() {
		content += "<table class='fragment'>" +
				   "<thead><tr>\n";
	}
	
	public void headTitle(String s) {
		content += "<td align='center' valign='center'><h4>" + s + "</h4></td>\n";
	}
	
	public void closeHead() {
		content += "</tr></thead>";
	}
	
	public void initBody() {
		content += "<tbody>";
	}
	
	public void initRow() {
		content += "<tr>\n";
	}
	
	public void addCol(String s) {
		content += "<td height='10' align='center' valign='center'>" + s + "</td>\n";
	}
	
	public void endRow() {
		content += "</tr>";
	}
	
	public void endBody() {
		content += "</tbody>";
		content += "</table>";
	}

	public String dump() {
		return content;
	}
	
}
