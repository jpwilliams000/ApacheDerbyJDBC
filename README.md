# ApacheDerbyJDBC
Basic Java SE standalone application that demonstrates an embedded Apache Derby JavaDB Connectivity with simple SQL query statements.

Reads initial data from a text file, creates a new database named ArenaDB, adds the data row by row, then demonstrates the connection with simple SQL example queries. 

For simplicity purposes, the Connection, Statement and Result set are not closed until the exiting of the program. However, it would be considered better practice to free up the resources at the commencement of each method that utilizes them. With Java 7, this becomes simplified with the use of 'try-with-resources' https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
