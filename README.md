# TapIn
TapInBoiz 
Thomas Delaney C15300756
Mohamad Zabad ADD YOUR STUDENT NUMBERS HERE 
Raul Alvarez ADD YOUR STUDENT NUMBERS HERE 

#YouTube Video

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/1V9bT77H-N8/0.jpg)](https://www.youtube.com/watch?v=1V9bT77H-N8)

# What is TapIn?
TapIn is a multi-functional utility mobile Android app for Employers and Employees of a Company. A Company would come to us to start up a service for them, we would add them to our database and give them Employer Logins. An Employer can add an Employee to the system by entering in the respective information, giving them a login and then finally loading their ID onto a personalised NFC Card. The employer can see who has clocked in for the current day and add schedules and new employees to the system. An Employee can also login now with their details and see their schedules for 4 weeks from the current week. There is a Hub part of the App (that would really be on a piece of Hardware, we put the software on the app to show functionality however) where the Employee can clock in and out of work. The employee places their NFC Card on the phone and it will display the current time and if they are or are not working today. If so they can clock in for work (The Employee will recieve a notification of this). When the employee clocks out they can see how much money they have made that day. This is the 

# Main Menu Features
* Main Menu
  * 3 login/user options as well as a nice personally designed logo for the app
* Employer Mode
  * Main Area for Employer where they can see who is checked in for current day, Add new Employees or add Schedules for current Employees
* Employee Mode
  * Main Are for Employee where they can select a week to view their Schedule for that week (allows up to 4 weeks including current week)
* Hub Mode
  * Allows Employee to Clock in/out using NFC Card

# The Database
The database is hosted on a free hosting site called 000webhost.com, the connection is made through a php file on the server which has the creditentials to access the database.

There are 4 tables, COMPANY, EMPLOYER, EMPLOYEE and TIMETABLES
* SOMEONE WRITE ABOUT THE TABLES LIKE WHAT THEY INCLUDE *

#Main Menu Summary
3 Buttons and an Image, the first Button brings the user to the Employer Main Home Page, it will checked SharedPreferences to see if the Employer is already logged in, if so then the page loads, else then the Employer Login page is shown, which prompts the Employer to login, when they do the Home Page is loaded. This workins in hand for the second button for Employee Mode; the Hub however does not save the Employer's details into SharedPreferences.

#How BackgroundTask gets information from the Database
There is a class called BackgroundTask which extends AsyncTask which is used to access the internet. The class opens a named PHP file and POST specific info to that file, the PHP file will execute on the server and whatever is echoed by the file is converted to a String in the Android App, it is then passed back to the class through an AsyncResponse method which then overrides a ProcessFinish method which a String is passed (the echo from the php file) to the method. This is how all information from the database is retrieved.

#Employer Mode
Employer mode has 3 core functions, Check Staff, Add Employee and Add Schedules.
These are accessed through buttons which bring the user to the respective activity. 
  * Check Staff 
    This part of Employer mode displays all Employees working that day and if they have clocked in or not clocked in for work. A PHP file is called by this class where all the named employees working for that day are returned. The CheckedIn field from the database is used to see if the employee has clocked in or not. A ListView and ListViewAdapter (extends BaseAdapter) are used to create a scrollable nicely laid out list to see who has clocked in or not, if the user taps on these little adapters they can see what time that employee clocked in at and when they have to clock in at OR what time they should clock in at.
    
  * Add Employee
    The Employer is first asked to turn on NFC if they have not on their phone, this is because when the Employer is finished entering info about the new employee, they must load that new Employee's ID to the NFC so they can clock in/out on the Hub. This is done by getting the users phone's NFC adapter and putting into a NfcAdapter object, if the adapter is not null and is Enabled then the Enter Employee form is loaded, else a screen is displayed notifying then Employer to turn on NFC on their phone. If it is then a form to enter new Employee info is displayed, there is fields for most attributes in the EMPLOYEE table in the database. There is also error checking to make sure all fields are entered correctly (@ in email address, wage format in XX.XX). They are the asked to give the new Employee a Username and Password so they can login to their TapIn account; the password has to be entered twice for error checking sake. Finally a gif is displayed and a message for the user to place the NFC card on the phone and hold it until propted. How it works is when the card is placed on the phone, the app is notified (through OnNewIntent, which has a check to see if an NFC tag has been tagged on the phone) and the Employee is added to the database, the ID is then retrieved as a String and converted into an NFC record then creates an NFC message from that record (through a number of methods), the tag is formatted to the device and the message is written to the card/tag. An AlertDialog pops up when the process is complete notifying the user of this, then when the user clicks okay they go back to the homescreen.
    
   * Add Schedules
     This displays a CalendarView to the Employer, where he can select any day from the current month + 2 months ahead and also that isn't today or previous days of that month. When the user taps on the day it the asks them are they sure, if so then another ListView, similar to Check Staff except it displays the the Employee name, their job title and if they have a schedule for that day or not. If not, a button will appear on the very left side of the ListViewAdapter which will allow the Employer to add a schedule for that day for that employee, if they do then the times they are working for that day is displayed and a button called which allows the employer to edit or delete that schedule. The times are chosed by TimePickers, which allows the user to chose an hour and a minute in the 24 hour format, the end/clock out time has to be at least 1 hour more than the start/clock in time. 

#Key Notes
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
