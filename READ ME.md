Goal: 
- Creating a step by step guide for android development for people with programming language background (Java)
- Showing how an idea is broken down into steps, and then utilizing existing guides to build it into a functioning app.

# Breakdown:
  
### Pre-Requisite: Learning how to utilize basic features of android studio
-installation process for android studio.
1. Install latest android studio
2. When you run android studio, it will ask for sdk location. Ignore it and close out of window.
3. Go to Tools - SDK Manager 
4. On SDK manager, select edit (next to Android SDK Location)
5. Make sure Android SDK, API ## is selected and click next

** With this you should now be able to select an android OS to download and install **
*** NOTE: If you have an android device. Use that, rather than VM android on computer ***
Resources:
* https://codelabs.developers.google.com/codelabs/android-training-hello-world/index.html?index=..%2F..%2Fandroid-training#0
a. Test whether Android Studio is functional and VM/android device is functional via the "Hello World" first app.
  - New Project - Empty Activity - Name: Hello World - Minimum SDK: API 15 - Finish
  - Select android device or VM device and run app.


## Practice 1: Understanding Android studio
* Quick review over basic syntax and making sure that android studio is fully functional
* https://www.youtube.com/watch?v=SXLmr4Qp4OM
* Skip as needed. First 3 hours is roughly syntax of kotlin which is relatively similar to Java

* Follow the video till 4:36:40.
* At this point you will run into an error as the instructor does not mention a plugin setup that is needed.
* From your android view (Folders), expand Gradle Scripts, and select the 2nd build.gradle file
* In the plugins, you will add this (do not delete anything else)     
id 'kotlin-android-extensions'
* You can follow through the remaining of the video to get a rough understanding of how to create an app that will utilize a Array List to store data temporarily to randomly select an item from the list.
* I added in an additional feature to delete, just to test out if I knew enough to add on to the beginners project. (so far so good)

* You may have tested the app further an noticed why i stated "temporarily" earlier. Each time you reopen the app. The stored values in the Array is erased.
* This is why we will now try Version2, which will utilize SQLite instead. This will solve the disappearing data issue.

-during the project if you get an error: "This version of the Android Support plugin for IntelliJ IDEA (or Android Studio) cannot open this project, please retry with version 4.1 or newer."
- just update Android Studio from the help tool bar


## Practice 2: Using SQLITE
# Pre-requisite 1. What is SQL? A quick look.
STUFF about simple structure. What is primary key. What is foreign key. 
How a simple CREATE TABLE  works
How to INSERT values into a table
How to search and retrieve data from a table using simple query.


# Creating the database on android
* https://www.youtube.com/watch?v=VnabHcf0e3w
* This is a short youtube video that demonstrates how a simple database can be created using SQLITE.

