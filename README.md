Source Code for GO Analysis in Geoduck Gonad Background
==========================================================

*Note* A running version of this web application is available at 
http://yeastrc.org/compgo_geoduck/pages/goAnalysisForm.jsp  These
files are provided so that underlying source code doing the analysis
may be reviewed.

About the source code
-----------------------
The Java source files for the web application are available in the web-application directory.

The primary file for performing the analysis is in the org/yeastrc/compgo/geoduck/analysis/AnalyzedGOTreeMaker.java file.

A number of Java libraries required to run the web application, but not directly related
to the analysis, are not included in this repository for licensing reasons. These libraries are:

  * Several Apache commons libraries: beanutils, collections, dbcp, digester, lang, logging, pool, and validator.
  * Java Advanced Imaging libraries.
  * MySQL Connector
  * yFiles library for generating the graph image (commercial).
