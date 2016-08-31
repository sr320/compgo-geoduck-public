
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html>

	<head>
		<title>GO Analysis Report</title>

		<script src="js/jquery-1.8.0.min.js"></script>
		<script src="js/collapsable_handler.js"></script>

	</head>

	<body>


		<nested:iterate id="reportNode" name="reportData" scope="request">
		
			<div style="margin-top:20px;">
				<span style="font-size:14pt;"><bean:write name="reportNode" property="node.acc" />: <bean:write name="reportNode" property="node.name" /></span>
					
					<logic:notEmpty name="reportNode" property="hpv1">
						<logic:notEmpty name="reportNode" property="proteins1">
							<div style="font-size:12pt;margin-left:20px;" class="collapsable-container">
								Protein Set 1: p-value: <bean:write name="reportNode" property="hpv1.pvalueString" /> ratio: <bean:write name="reportNode" property="hpv1.i" /> / <bean:write name="reportNode" property="hpv1.inputTotal" />
								<span style="color:#940000;" class="clickable collapsable-header">[Show/Hide Proteins]</span>
								<div style="margin-left:20px;display:none;" class="collapsable">
								
									<table style="border-width:0px;">
										<nested:iterate id="protein" name="reportNode" property="proteins1">
											<tr>
												<td><bean:write name="protein" property="id"/></td>
												<td><bean:write name="protein" property="name"/></td>
											</tr>
										</nested:iterate>
									</table>
									
								
								</div>
								
							</div>
						</logic:notEmpty>
					</logic:notEmpty>

					<logic:notEmpty name="reportNode" property="hpv2">
						<logic:notEmpty name="reportNode" property="proteins2">
							<div style="font-size:12pt;margin-left:20px;" class="collapsable-container">
								Protein Set 2: p-value: <bean:write name="reportNode" property="hpv2.pvalueString" /> ratio: <bean:write name="reportNode" property="hpv2.i" /> / <bean:write name="reportNode" property="hpv2.inputTotal" />
								<span style="color:#940000;" class="clickable collapsable-header">[Show/Hide Proteins]</span>
								<div style="margin-left:20px;display:none;" class="collapsable">
								
									<table style="border-width:0px;">
										<nested:iterate id="protein" name="reportNode" property="proteins2">
											<tr>
												<td><bean:write name="protein" property="id"/></td>
												<td><bean:write name="protein" property="name"/></td>
											</tr>
										</nested:iterate>
									</table>
									
								
								</div>
								
							</div>
						</logic:notEmpty>
					</logic:notEmpty>

			</div>
		
		
		</nested:iterate>

		

	</body>

</html>
