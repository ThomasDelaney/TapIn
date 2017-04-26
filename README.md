# TapIn
TapInBoiz 

Thomas Delaney C15300756

Mohamad Zabad C15745405 

Raul Alvarez C14722441

#YouTube Video

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/EsPfizWKrfA/0.jpg)](https://www.youtube.com/watch?v=EsPfizWKrfA)

# What is TapIn?
TapIn is a multi-functional utility mobile Android app for Employers and Employees of a Company. A Company would come to us to start up a service for them, we would add them to our database and give them Employer Logins. An Employer can add an Employee to the system by entering in the respective information, giving them a login and then finally loading their ID onto a personalised NFC Card. The employer can see who has clocked in for the current day and add schedules and new employees to the system. An Employee can also login now with their details and see their schedules for 4 weeks from the current week. There is a Hub part of the App (that would really be on a piece of Hardware, we put the software on the app to show functionality however) where the Employee can clock in and out of work. The employee places their NFC Card on the phone and it will display the current time and if they are or are not working today. If so they can clock in for work (The Employee will recieve a notification of this). When the employee clocks out they can see how much money they have made that day. 

# Main Menu Features
* Main Menu
  * 3 login/user options as well as a nice personally designed logo for the app
* Employer Mode
  * Main Area for Employer where they can see who is checked in for current day, Add new Employees or add Schedules for current Employees
* Employee Mode
  * Main Area for Employee where they get some of their details displayed and have the option to select a week from the spinner  to view their Schedule for that week (allows up to 4 weeks including current week)
* Hub Mode
  * Allows Employee to Clock in/out using NFC Card

# The Database
The database is hosted on a free hosting site called 000webhost.com, the connection is made through a php file on the server which has the creditentials to access the database.

There are 4 tables, COMPANY, EMPLOYER, EMPLOYEE and TIMETABLES

* COMPANY

It holds information about the company. Upon request for access to our software, the company will provide a name, location and the length of the break time that they offer. This information will be held by this table, as well as, an auto-incrementing company id which will be used as a foreign key for all the other tables. 

* EMPLOYER

Upon completing the requirements for the COMPANY table, the said company will send information about how many employers will use our product, followed by us created the login keys for each employer. This table will hold the login details for each employer and Tap In Hub.
Last but not least, one of the most important fields is the token which is used when an employer gets a notification when the employee clocks in. This holds a string which is made of 150 characters and is used to identify the employer's devicee when he logs in.

* EMPLOYEE

This table contains details about the employees which are linked with the companies through the company id. Every field from this table is set by the employer whenever he sets up an nfc tag for the new employee. The employee, with the help of the employer, has the freedom to choose the login details he wants to use. Therefore, the employee will get a login that he will remember easily and will not require our help in case he forgets the details.

* TIMETABLES

In this table each scheduled day will have the details set. It will be populated by the employer through the app whenver he sets up a schedule for an employee. The said employer has the freedom to add, edit and delete a record so no intervention from us would be needed. Each record is linked to a company and to an employee through the unique Ids.
Apart from the schedule set and display, this table is also used for the clock in and clock out function because it holds the scheduled time, the actual time when the employee clocked in and if he already clocked in or not he cannot clock in/out more than once.

# Main Menu Summary
3 Buttons and an Image, the first Button brings the user to the Employer Main Home Page, it will checked SharedPreferences to see if the Employer is already logged in, if so then the page loads, else then the Employer Login page is shown, which prompts the Employer to login, when they do the Home Page is loaded. This workins in hand for the second button for Employee Mode; the Hub however does not save the Employer's details into SharedPreferences.

# How BackgroundTask gets information from the Database
There is a class called BackgroundTask which extends AsyncTask which is used to access the internet. The class opens a named PHP file and POST specific info to that file, the PHP file will execute on the server and whatever is echoed by the file is converted to a String in the Android App, it is then passed back to the class through an AsyncResponse method which then overrides a ProcessFinish method which a String is passed (the echo from the php file) to the method. This is how all information from the database is retrieved.

# Employer Mode
Employer mode has 3 core functions, Check Staff, Add Employee and Add Schedules.
These are accessed through buttons which bring the user to the respective activity. 
  * Check Staff 
  
    This part of Employer mode displays all Employees working that day and if they have clocked in or not clocked in for work. A PHP file is called by this class where all the named employees working for that day are returned. The CheckedIn field from the database is used to see if the employee has clocked in or not. A ListView and ListViewAdapter (extends BaseAdapter) are used to create a scrollable nicely laid out list to see who has clocked in or not, if the user taps on these little adapters they can see what time that employee clocked in at and when they have to clock in at OR what time they should clock in at.
    
  * Add Employee
  
    The Employer is first asked to turn on NFC if they have not on their phone, this is because when the Employer is finished entering info about the new employee, they must load that new Employee's ID to the NFC so they can clock in/out on the Hub. This is done by getting the users phone's NFC adapter and putting into a NfcAdapter object, if the adapter is not null and is Enabled then the Enter Employee form is loaded, else a screen is displayed notifying then Employer to turn on NFC on their phone. If it is then a form to enter new Employee info is displayed, there is fields for most attributes in the EMPLOYEE table in the database. There is also error checking to make sure all fields are entered correctly (@ in email address, wage format in XX.XX). They are the asked to give the new Employee a Username and Password so they can login to their TapIn account; the password has to be entered twice for error checking sake. Finally a gif is displayed and a message for the user to place the NFC card on the phone and hold it until propted. How it works is when the card is placed on the phone, the app is notified (through OnNewIntent, which has a check to see if an NFC tag has been tagged on the phone) and the Employee is added to the database, the ID is then retrieved as a String and converted into an NFC record then creates an NFC message from that record (through a number of methods), the tag is formatted to the device and the message is written to the card/tag. An AlertDialog pops up when the process is complete notifying the user of this, then when the user clicks okay they go back to the homescreen.
    
   * Add Schedules
   
     This displays a CalendarView to the Employer, where he can select any day from the current month + 2 months ahead and also that isn't today or previous days of that month. When the user taps on the day it the asks them are they sure, if so then another ListView, similar to Check Staff except it displays the the Employee name, their job title and if they have a schedule for that day or not. If not, a button will appear on the very left side of the ListViewAdapter which will allow the Employer to add a schedule for that day for that employee, if they do then the times they are working for that day is displayed and a button called which allows the employer to edit or delete that schedule. The times are chosed by TimePickers, which allows the user to chose an hour and a minute in the 24 hour format, the end/clock out time has to be at least 1 hour more than the start/clock in time. 

# Employee Mode
Being meant to be accessed only by the employees, this mode rather restrictive, because its purpose is to display the schedule on the following weeks.

   * Choose Week
   
   The fucntion of this page is to get the schedule displayed for the employee. The user is prompted by his name, the position that he holds in the company and the name of the company that he works for. All this data is retrived through the use of a database which is linked to the Java code by Php files.
   
   The main element of this page is a spinner which holds the dates of the next four weeks starting with the current one. The content that goes into the spinner gets checked through a substantial amount of error checking. The user can choose which week to be displayed on the following screen. The next two buttons are the "Done" button and "Log out" button. Upon pressing the "Done" button the Timetable View screen gets launched
   
   Just like in the Employer mode, there is a Session Manager that will remember the details for the log in. Therefore, the employee part has a "Log Out" button that simply clears the session and takes you back the the main screen.
   
   
   * The Timetable view 
   
   Upon the user choosing the week, the app will retrive from the database all the days scheduled to work for that employee and display it in a List View. Aditionally, the app will also display the not scheduled day with appropiate color marking. On the the text view that is on the top side of the screen the total amount of hours to be worked on that week will be displayed.

# Hub Mode
In order to use the Hub, the employer must login first. There are two main activities in this mode:
   * Card Scanning
   
   This avtivity reads the id stored in the employee's NFC card. It checks if the employee works for the same company as the employer, and if it succeeds the employee can now clock in or clock out.
   
   * Clock in / Clock out
   
   Once the employee scans the NFC card, a TextView will be displayed showing the current time, another TextView showing if they are working or not that day and the options to clock in and clock out.
   
   When the employee taps the clock in Button, the app checks if he is eledgible to clock in. A php file returns the starting time of the employee. If the current time is either 15 minutes behind or 15 minutes after the start time, the employee can clock in, storing the current time into the database and changing the CHECKEDIN to true. Otherwise, a toast will pop up saying "You cannot clock in now". If the employee tries to clock in again an error message will be displayed.
   
   Clocking out works in a very similar way, the employee can only clock out if the current time is in range of the finishing time (15 minutes before and after) and the status of CHECKEDIN is equal to true. If the conditions are met, the current time is saved into the database, the CHECKEDIN is changed to false, and the payment for the day, whick is calcuted using the total hours worked for the day multiplied by the employee's wage.
   
# Key Notes
* Use of Android Studio's vast libraries.

* Usage of Online Database (000webhost.com for hosting) which involved created many PHP files and SQL queries.

* Use of Google's Firebase Cloud Messaging service to notify Employer when Employee has Clocked In.

* Multiple extending of classes and implementation of interfaces such as AsyncTask.

* Use of NFC technology which involved learning the NFC libraries for Android.

* Worked with different Data Structures such as Android ListView, ArrayLists and Hashmaps.

* Use of SharedPreferences which keeps a user logged in to their respective Mode until they logout, even if the app is closed/stopped.

* App can naturally scale well to different screens by design (RelativeLayout).

* Very thought through App Integrity - A lot of error checking and handling - The user is always notified or given some UI to show what is currently happening in the app.

* Real Time - app updates in real time depending of the current date and what the users and doing with the app, this all happens due to changes in the database made by the users on the app.

* Complicated Algorithms used to check times for clock in/out, populate ListViews accordingly, calculate start of week from current week then 3 weeks onward.
