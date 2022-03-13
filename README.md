# C195_Project
Gabriel Izzo
gizzo@wgu.edu
app version 1.0
03/06/2022

Appointment+ README

The title and purpose of the application:
The title of the application is Appointment+, a simple appointment scheduling java application that connects to a MySQL Database.

The purpose of the application is for users to be able to perform the following:
•Schedule, modify, and delete appointments stored in the MySQL database.
•Add, modify, and delete customers and customer associated appointments stored in the database.
•Search appointments, customers and reports conveniently using a text search tool.
•View and print data reports directly from the application in report views.
•Display the login screen and alerts in French if the user's system language is set to French.

IDE including version number, full JDK of version used, and JavaFX version compatible with JDK version:
•IDE used to develop the application: IntelliJ 2021.3 Community Edition
•JDK version used: Java SE 17.0.1
•JavaFX version used: JavaFX-SDK-17.0.1

Directions for how to run the program:
To run the program, please ensure JDK 17.0.1 is installed and configured for the project in the IDE as well as JavaFX-SDK-17.0.1. Also, ensure MySQL Workbench database is set up using the C195_db_ddl.txt and C195_db_dml.txt files and MySQL Connector/J 8.0.28 driver will need to be downloaded and installed for database connectivity.
The application can be run directly from IntelliJ's Java Runtime Environment by performing the following: 
1)Opening the project in IntelliJ, accessing File>Settings and setting the path variable to JavaFX, by clicking on Path Variables, "+" symbol, add PATH_TO_FX and choose the directory that holds the javafx-sdk-17.0.1\lib.
2)Next select File>Project Structure>Project Settings>Libraries>"+"> and add the javafx-sdk-17.0.1\lib directory.
3)Select File>Project Structure>Project Settings>Modules>Dependencies and select the "+" above export on the right side window and select Library and add the javafx-sdk-17.0.1\lib directory. Select the "+" again, choose JARs or Directories
and locate the mysql-connector-java-8.0.28.jar file and add it to the dependencies list.
4)To run the program, a runtime configuration will need to be created and setup correctly. To do this, go to the IntelliJ Main Screen and to the right of the hammer symbol click on the section to add a runtime configuration.
5)Select Add/Edit Configurations>"+">Application> and name the configuration and select Modify Options>Add VM Options> paste in the following in the VM Options field: --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
6)In the Main Class field type main.Main and select Apply>OK. 
7)Now from the IntelliJ IDE everything should be set up to run JavaFX and the MySQL database, and you can start the application by clicking the green Run/Play icon or from the debugger.

To login, please use either test/test or admin/admin default usernames and passwords to log in to the Main Menu.
A welcome greeting will display depending on which user account logs in.
From the Main Menu, the user can access options for adding, modifying, and deleting appointments, access the customers' menu, or view reports from the menu on the left-hand side of the screen.
In the Customers Menu, the user can add, modify or delete customers(along with their associated appointments upon deletion).
To view the login screen in French, try changing your system language to French and restart the application to see the login screen changed to display in French.

All login attempts, whether successful or not, are recorded to login_activity.txt and all GUI reports are searchable and printable directly from the user interface.
When the user wishes to exit the application, they can either simply close the application window or use the Log Out/Exit button from the Main Menu.

A description of the additional report of your choice:
In addition to being able to view appointments by month and type, there is also two additional reports.
The first report provides the ability to view, search and print the application user's existing appointment schedule.
There is also a second report that provides the ability to view, search and print the business contact's appointment schedule.

The MySQL Connector driver version number, including the update number:
-Connector/J 8.0.28
-Used with MySQL Workbench 8.0.28 Community Edition

Please email the @author gizzo@wgu.edu if you encounter any issues with the application or discover bugs that require a patch. Enjoy Appointment+!


