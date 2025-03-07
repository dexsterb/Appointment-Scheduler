username: admin
password: admin

username: test
password: test
Purpose of application
The objective of this software is to deliver a scheduling desktop application with a user-friendly graphical interface (GUI).

Author: Dexster Bowman
Contact information: dexsterbowman@gmail.com
		     8034603167
application version: 1.0
date: 9/15/2023

IDE and java module Information
IntelliJ IDEA 2023.2 (Community Edition)
Build #IC-232.8660.185, built on July 26, 2023
Runtime version: 17.0.7+7-b1000.6 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 10.0
GC: G1 Young Generation, G1 Old Generation
Memory: 2048M
Cores: 4
Non-Bundled Plugins:
    com.intellij.properties.bundle.editor (232.8660.88)
    DBN (3.3.9882.0)

Kotlin: 232-1.9.0-IJ8660.185

javafx: openjfx-17.0.8
mysql connector: mysql-connector-java-8.0.25

How to run the program
When the program starts the user has to enter one of two unique passwords stored in the database. After entering the user will view a main menu which gives 4 options, view appointments, view customers,
view reports or logout. Entering appointments or customers will give the user the option to add, modify, delete, or view from the selection. If the user selects report they will have three 
filters that will give the user the option to view appointments by contacts, customers or apointment id. If the user wants to filter through multiple tables they should reset to get optimal results
thought it is not necessary. 

Additional report
I chose total customers by division for my additional report. It gives a display of every division in the database and has an additional column that displays the customers totals in
that division. In my firstleveldivisionsDao I created an additional method to query the amount of customers per division by left joining division id on customer id and doind a count on 
the customer id. I stored this in an observable list and populated it in a reports table.

