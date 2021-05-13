# Cinema Project

This application is available on heroku: 
https://kinoteatr-app.herokuapp.com
</br>
User account: login: user password: user | Admin account: login: admin password: admin

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


#### The repertoire of movies:

![Bez tytułu](https://user-images.githubusercontent.com/46786100/114559778-5e741900-9c6c-11eb-88d1-7e5d841da45f.jpg)

The app should also behave properly on mobile devices

![172990822_260064565770636_2167538867048692561_n](https://user-images.githubusercontent.com/46786100/114551398-a0e52800-9c63-11eb-81bd-307eb7d15f11.jpg)

Administrator has additional values on the menu above. He can add and change repertoire of movies

![Bez tytułu](https://user-images.githubusercontent.com/46786100/114559270-d7bf3c00-9c6b-11eb-90ec-cab4b76ba792.jpg)

Final reservation

![Bez tytułu](https://user-images.githubusercontent.com/46786100/114559690-49978580-9c6c-11eb-804c-f29a3514f906.jpg)


## TO-DO
 In order to create a proper web application, which are created nowadays, there are a few things that need to be changed:
 * Upgrade front-end (Thymeleaf engine) to a newer solution.
 * Move the database outside the application e.g. MySQL

