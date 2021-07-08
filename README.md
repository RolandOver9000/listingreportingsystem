# Listing Reporting System

 This is a program that making listing validations and reports based on the data of a third party API which is the Mockaroo API.
 The program is still under development, so you will find the latest version on the **development** branch.
 
 **It requires an FTP server where you can upload your reports. It will try to create a directory on the root of your FTP server with the name of *reports***<br><br>
 It will generate the validation logs and the reports into its listingreportingsystem/src/main/logs and listingreportingsystem/src/main/reports directory.
 
 You also will need some environment variables for the setup:
  - MOCKAROO_API_KEY -- the API key of the third party API
  - FTP_SERVER_HOST -- the IP address of the FTP server
  - FTP_SERVER_PORT -- the port of the FTP server
  - FTP_SERVER_USERNAME -- the username of your FTP client
  - FTP_SERVER_PASSWORD -- the password of your FTP client
  - PERSISTENCE_UNIT_NAME -- the name of your persistence unit which will handle the database based on your persistence.xml file

You can use my persistence.xml if you have a PostgreSQL database with the setup data I have in the *listingreportingsystem/src/main/resources/META-INF/persistence.xml*.

Tests are not fully implemented yet. I use H2 in-memory databse for testing and I still do not handle memory reset after test cases. You can run test cases separately at the moment. :disappointed: <br><br>

There were some parts that was not clear in the description of the assignment:
- Should I validate *upload time* -- I validate it
- I do not know what counts as *best listing* -- I calculcated it with the (*quantity * listing price*) formula
- I do not know the scale and rounding of the calculations -- I use *scale: 2* and *Round down*<br><br>

I also need some improvements on the other parts too like:
- there is 2 plus columns in the listing table (year and month) because I had a problem with the *MONTH* and *YEAR* SQL functions, I will remove those columns later
- common sql query usage
- validation refactoring
- making a config file instead of environment variables
- proper error handling
- some other code refactoring 
- more tests <br><br>

Used technologies:
- Apache Maven 3.6
- H2
- Java 1.8
- JPA + Hibernate
- PostgreSQL
- JUnit5
- Mockito
