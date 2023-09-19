# About
This android app was fully realized as a project for the end of year exam, it allows you to manage your workout plan (unfortunately it was designed in single frequency) and was developed in Android Studio (Java), PHP and SQL (MariaDB).

## Disclaimer
The project was developed when I was almost 18 years old, obviously I was learning and the code will probably be messy, not clear and with non-English comments or constructs.

## Phases of creation
### 1. Brainstorming and ER Model
I used XMind for brainstorming and for building concept maps subsequently implemented in the Database.
<p align="center">
  <img width="400" height="200" src="https://github.com/svtmontagna/My-Gym---Workout-Plan/blob/main/images/concept.png?raw=true">
</p>

### 2. Mockups, UI and UX
I used Balsamiq Mockups to create optimal application mockups from a UI and UX perspective.
<p align="center">
  <img width="500" height="300" src="https://github.com/svtmontagna/My-Gym---Workout-Plan/blob/main/images/mockups.png?raw=true">
</p>

### 3. Backend
I created the database and the various tables based on the conceptual map created with XMind and subsequently developed the API in PHP to insert and withdraw data from the database and pass it to the application.
<p align="center">
  <img width="700" height="400" src="https://github.com/svtmontagna/My-Gym---Workout-Plan/blob/main/images/er-model.png?raw=true">
</p>
As can be seen from the ER model, a user can create a tab that can contain the type of exercise (which in turn will be linked to the muscle entity to understand which muscle group it is aimed at), number of sets and repetitions.

### 4. Frontend
I created the app on Android studio (Java) based on the mockups created with Balsamiq Mockups, manipulating the data through the previously created APIs.
>tabstarting2.xml
<p align="center">
  <img width="350" height="600" src="https://github.com/svtmontagna/My-Gym---Workout-Plan/blob/main/images/app.png?raw=true">
</p>


