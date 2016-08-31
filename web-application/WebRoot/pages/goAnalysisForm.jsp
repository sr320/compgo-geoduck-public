
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html>

	<head>
		<title>GO Analysis Tool</title>
	</head>

	<body>

		<p>Fill in the form below to perform graphical GO analysis on input proteins.</p>
		
		<p>Any questions, bug reports, or feature request should be sent to Michael Riffle at <a href="mailto:mriffle@uw.edu">mriffle@uw.edu</a></p>

		<html:form action="/goAnalysis">
		
		<div>
			Protein list 1:<br>
			<html:textarea property="proteinList1" rows="5" cols="80"></html:textarea>
		</div>
		
		<div>
			Protein list 2 (optional):<br>
			<html:textarea property="proteinList2" rows="5" cols="80"></html:textarea>
		</div>

		<div>
			GO Aspect:
			<html:select property="aspect">
				<html:option value="C">Cellular Component</html:option>
				<html:option value="P">Biological Process</html:option>
				<html:option value="F">Molecular Function</html:option>
			</html:select>
		</div>

		<div>
			P-value cutoff (optional): <html:text property="cutoff" size="10"></html:text>
		</div>
		
		<div>
			Analysis type: <html:radio property="type" value="image"></html:radio> Image <html:radio property="type" value="report"></html:radio> Report
		</div>
		
		<div>
			<html:submit></html:submit>
		</div>
		
		</html:form>
		

	</body>

</html>
