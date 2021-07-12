# JMeet 

<img src="/assets/teamslogo.png" height="100px" width="100px">

## `Section Links`
- [Introduction](#Introduction)  
- [Features](#Features)  
- [Tech Stack](#Tech-Stack)  
- [Database Structure](#Database-Structure)  
- [Installation](#Installation)  
- [Snapshots](#Snapshots)  
- [Contributor](#Contributor)  


## `Introduction`
JMeet is a meeting application designed under Microsoft Engage Mentorship Program 2021. It is a Java based application built along with the help of WebRTC API. It is inspired by the Microsoft Teams Software. Following is the description of the application, hope you will like it! 😊

## `Features`
### `Basic Features`
1. User will be able to signup and login to the application.
2. User will be able to search for other users using their name, email or username and view their profile.
3. Profile page for each user (profile status, username, company, contacts etc.).
4. Users can set their status (Online, Away, In a Call, Offline, Busy).
5. One can schedule meetings and URL for the meeting will be generated.
6. The user can view all the upcoming meetings.
7. User will be able to conduct a video chat with another user (video + sound).


### `Advanced Features`
1. Users are able to share their screen to other user.
2. The users can share their audio, complete screen, a window or a browser tab.
3. Users are able to chat with each other by sending textmessages (like conversation window next to the video).
4. The user will be notified about scheduled meetings, chat messages, and upcoming meetings.


## `Tech-Stack`

#### `Client Side`
- Programming Language: **`Java`**
- User Interface: **`JavaFX`** **`CSS`** **`XML`**

#### `Server Side`
- Programming Language: **`Java`**
- Database: **`SQLite`** **`JDBC`**

#### `WebRTC API`
- Programming Language: **`JavaScript`**
- User Interface: **`CSS`** **`HTML`**

#### `Networking`
- Main Application: **`Java Socket Programming`**
- WebRTC: **`Web Sockets`**

#### `Other Tools`
- IDE: **`IntelliJ Idea`**
- VCS: **`Git`**


## `Database-Structure`

| Table |Fields|
|:-------:|:--------------------:|
|users|user_id, password, email, firstname, lastname, organization|
|user_profile|user_id, phone, address, country, about|
|meetings|title, host_id, date, time, details, meeting_link|
|messages|sender_id, receiver_id, message, date, time, is_read|
|notifications|sender_id, receiver_id, notifications, date, time, is_read|
|online_users|user_id, user_ip|
|statuses|user_id, status|

## `Installation`
1. Install the JDK version greater than 8. 
2. Download JavaFX [(link)](https://gluonhq.com/download/javafx-11-0-2-sdk-windows/) and JDBC SQLite [(link)](https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.1/sqlite-jdbc-3.36.0.1.jar).
3. Open as IntelliJ Idea project and change the [given settings](https://openjfx.io/openjfx-docs/).
4. Add the above libraries to your project structure.
5. Setup Apache Tomcat Server and paste the .war file into the webapps.
6. Change the URL in DBConnection.java according to your database URL.
7. Now run the project in IntelliJ Idea. And you are good to go. 😊 

## `Snapshots`

## `Contributor`
[Yashika Jain](https://github.com/Yashikaj14)
