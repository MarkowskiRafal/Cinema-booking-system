# Cinema Project

This application is available on heroku: 
https://kinoteatr-app.herokuapp.com
</br>
<b>User account-</b> login: <b>user</b> password: <b>user</b> | <b>Admin account-</b> login: <b>admin</b> password: <b>admin</b>

## Contents
* [About](#About)
* [Technologies](#technologies)
* [Functions](#Functions)
* [TO-DO](#TO-DO)


## About
This project aimed to broaden the knowledge of programming and the use of my knowledge in practice. The main goal was to create a modern application that is functional and
fault-tolerant. This application is used to book seats in a cinema. Users can browse the repertoire, select a day and time, and then reserve seats. The seats are divided into
already reserved and unreserved. All data is stored in the database. Users can register and log into the system. Administrator is able to do CRUD operations on movies and
screening times. The app was tested on mobile devices.

## Technologies
* Java
* Spring Boot
* Spring Security
* Hibernate
* H2
* Thymeleaf
* Bootstrap
#### Used tools:
* Maven
* Lombok


## Functions
- Everyone can browse the application without logging into the system.
- Additional menu with more options for admin who can add, edit and delete movies.
- Admin can add, edit and delete the day and time when a particular movie will be playing in the cinema.
- Non logged in users can register and login to the application.
- Logged in users can reserve seats.
- User registration validation: each field must be entered correctly.
- User must confirm his registration to the e-mail adress he provided.
- Seats are divided into reserved(green) and unreserved(red).

 
### The repertoire of movies any user can browse:

![Bez tytułu](https://user-images.githubusercontent.com/46786100/114559778-5e741900-9c6c-11eb-88d1-7e5d841da45f.jpg)

 
### The next step is to select a time:

![image](https://user-images.githubusercontent.com/46786100/118123187-30613080-b3f4-11eb-9e17-6b9501da1884.png)
 
 
### Final reservation:

![Bez tytułu](https://user-images.githubusercontent.com/46786100/114559690-49978580-9c6c-11eb-804c-f29a3514f906.jpg)


### Admin can do CRUD operations on movies:

![image](https://user-images.githubusercontent.com/46786100/118122724-897c9480-b3f3-11eb-80e2-68b3c32df913.png)

### And repertoires:

![image](https://user-images.githubusercontent.com/46786100/118122876-bfba1400-b3f3-11eb-8d93-87a1894c7d38.png)


### Menu for user:

![image](https://user-images.githubusercontent.com/46786100/118122954-dbbdb580-b3f3-11eb-8384-030a388820d2.png)

### Menu for admin:

![image](https://user-images.githubusercontent.com/46786100/118122986-e2e4c380-b3f3-11eb-96a3-305d48c28eb2.png)

### Responsiveness on mobile devices:

![172990822_260064565770636_2167538867048692561_n](https://user-images.githubusercontent.com/46786100/114551398-a0e52800-9c63-11eb-81bd-307eb7d15f11.jpg)

User can swipe through the application.


## TO-DO
 In order to create a proper web application, which are created nowadays, there are a few things that need to be changed:
 * Upgrade front-end (Thymeleaf engine) to a newer solution.
 * Move the database outside the application e.g. MySQL

