# Teams Clone

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

## `Features`
### `Basic Features`
1. User will be able to signup and login to the application.
2. User will be able to search for other users using their name, email or username and view their profile.
3. Profile page for each user (profile status, username, company, contacts etc.).
4. Users can set their status( Online, Away, In a Call, Offline, Busy).
5. One can schedule meetings and url for the meeting will be generated.
6. The user can view all the upcoming meetings.
7. User will be able to conduct a video chat with another user (video + sound).


### `Advanced Features`
1. User is able to share his/her screen to other user.
2. The user can share his/her audio, complete screen, a window or a browser tab.
3. Users are able to chat with each other by sending textmessages (like conversation window next to the video).
4. The user will be notified about scheduled meetings, chat messages, and upcoming meetings.

### `Future Scope`


## `Tech-Stack`

#### `Client Side`
- Programming Language: **`Java`**
- User Interface: **`JavaFX`** **`CSS`** **`XML`**

#### `Server Side`
- Programming Language **`Java`**
- Database: **`SQLite3`** **`JDBC`**

#### `WebRTC API`
- Programming Language: **`JavaScript`**
- User Interface: **`CSS`** **`HTML`**

#### `Networking`
- Main Application: **`Java Sockets Programming`**
- WebRTC: **`Web Sockets`**

#### `Other Tools`
- IDE: **`IntelliJ Idea`**
- VCS: **`Git`**


### `Database-Structure`

| Table |Fields|
|:-------:|:--------------------:|
|users|user_id, password, email, firstname, lastname, organization|
|user_profile|user_id, phone, address, country, about|
|meetings|title, host_id, date, time, details, meeting_link|
|messages|sender_id, receiver_id, message, date, time, is_read|
|notifications|sender_id, receiver_id, notifications, date, time, is_read|
|online_users|user_id, user_ip|
|statuses|user_id, status|
