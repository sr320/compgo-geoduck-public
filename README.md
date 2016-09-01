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

About the database
-----------------------
The Java code requires a MySQL database with two tables: ``go_annotation`` and ``go_protein_counts``. The ``go_annotation``
table holds the GO annotations associated with protein accession strings, and go_protein_counts are the cached counts for
the number of GO terms associated with protein accession strings. The table definitions are:

```sql
CREATE TABLE `go_protein_counts` (
  `go_acc` varchar(255) NOT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`go_acc`)
) 
```

```sql
CREATE TABLE `go_annotation` (
  `db` varchar(255) NOT NULL,
  `db_object_id` varchar(255) NOT NULL,
  `db_object_symbol` varchar(255) NOT NULL,
  `qualifier` varchar(255) DEFAULT NULL,
  `go_id` varchar(255) NOT NULL,
  `db_reference` varchar(2000) NOT NULL,
  `evidence_code` varchar(255) NOT NULL,
  `with_or_from` varchar(255) DEFAULT NULL,
  `aspect` varchar(255) NOT NULL,
  `db_object_name` varchar(2000) DEFAULT NULL,
  `db_object_synonym` varchar(2000) DEFAULT NULL,
  `db_object_type` varchar(255) NOT NULL,
  `taxon` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `assigned_by` varchar(255) NOT NULL,
  KEY `db` (`db`,`db_object_id`),
  KEY `db_object_id` (`db_object_id`),
  KEY `go_id` (`go_id`),
  KEY `qualifier` (`qualifier`),
  KEY `aspect` (`aspect`)
)
```

