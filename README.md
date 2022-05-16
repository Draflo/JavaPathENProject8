# TourGuide V1.0 Release 05/22

Tourguide is an application that uses Spring Boot and is part of TripMaster applications.
The main goal is to provide its users a way to look and visit nearby attractions, having attractive prices on hotel and tikets.

This new version of the app allows it to be ready to welcome a huge amount of user without loosing its performance ! 

# How to launch the App

First of all you'll have to build each one of the MS. For this go to each Folder and enter the command "docker build -t 'imageName'" with respectively gpsutil, rewardscentral, trippricer and tourguide as 'imageName'
When the 4 containers are up and running enter the command "docker-compose up -d" in the tourguide folder. 

You can now launch the tests or go to localhost:8080 and check the functionality provided by the application ! 
