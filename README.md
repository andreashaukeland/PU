# Leitemarka Orienteringsklubb
An application for orienteering trainers and athletes to view and monitor progress of runners and the weekly activities.

[![pipeline status](https://gitlab.stud.iie.ntnu.no/tdt4140-2018/75/badges/master/pipeline.svg)](https://gitlab.stud.iie.ntnu.no/tdt4140-2018/75/commits/master)
[![coverage report](https://gitlab.stud.iie.ntnu.no/tdt4140-2018/75/badges/master/coverage.svg)](https://gitlab.stud.iie.ntnu.no/tdt4140-2018/75/commits/master)

# Motivation and status
The app is designed to make life easier for runners and athletes that want to take their training to the next level. Similar applications like Strava fills a similar need, but 
this application is better fitted for orieentering athletes.
At the current stage the application supports viewing the runners activites in a map and a view of their performance over time. Future work will focus on better comparing 
runners route to the expected or optimal path of the training.

![Github](https://image.ibb.co/k8eJQH/rsz_1kart.png "Map") ![Github](https://image.ibb.co/kZaskH/rsz_1progress.png  "Progress")
 

## Framework
- The application is written in Java and JavaFX
- The team uses Eclipse as IDE, however other IDEs that support Java coud also be used (e.g. IntelliJ)
- GitLab is used for continous integration
- Maven is used for building and testing
- The application is a 3-layer client application
- The application uses the MVC architecture
- We use MySql as a database and it is hosted at https://mysqladmin.stud.ntnu.no/index.php?token=0aa074722016d612958bf626fbba1421

## Conventions:
- Commitmessages should be linked to issues by tagging Issue #<N> at every commit
- All issues should be connected to a userstory and sprint
- All code and comments should be written in english
- The code follows the general Oracle conventions for code structure

## Getting Started
### Prerequisites
- We have used JFoenix for the GUI of the project and to be able to use JFoenix you will need to use Java 8 as a running environment, as it is not compatible with Java 9.
  Furthermore if you want to edit the fxml-files in Scenebuilder you will need to add JFoenix and FontAwesome as a dependency there
- You need an editor that supports Java (we reccommend Eclipse)
- If you want to run the tests from terminal you need to download Maven (https://maven.apache.org/download.cgi)

### Installation
Clone the project from GitLab and set it up as a maven project (git@gitlab.stud.iie.ntnu.no:tdt4140-2018/75.git).

### Testing and running the program
- The launcher of the application is called "ApplicationLauncher.java". It is found under app.ui/src/main/java. Run the script as a java-application.
- To run the tests for the application right-click at the root-folder, click run as and then maven test.


# Authors
- Andreas Haukeland
- Martin Syversen
- Herman Rommetveit
- Henrik Backer
- Torgeir Sandnes Laurvik
- Peder Grundvold
- Martin Skurtveit

