# Teams Clone

<img src="/assets/teamslogo.png" height="100px" width="100px">

### `Section Links`
[Introduction](#Introduction)  
[Features](#Features)  
[Tech Stack](#Tech-Stack)  
[Database Structure](#Database-Structure)


## `Introduction`

## `Features`

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
- Main Application: `Java Sockets Programming`
- WebRTC: `Web Sockets`

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
