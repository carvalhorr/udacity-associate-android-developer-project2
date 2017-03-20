# Movie Database

This is a complete app that displays popular and best rated movies. The data is obtained from 
The Movie Db api. Intructions on how to setup the a api key is given below.

It was developed as part of the coursework for the Udacity Associate Android Developer 
Fast Track nanodegree.

## Why choosing this project

I've chosen this project because it contains the most recent Android code that I have produced 
and it employs different resources of the android platform.

It uses different resources of android development:
* Activities
* Fragments
* Loader manager
* AsyncTasks
* Content Provider
* SQLite Database
* Broadcast Receivers
* Support for multiple screen sizes
* Recycler View
* Retrieval of data from the internet (using third party libraries - Retrofit)
* Loading images from the internet (using Picasso library)
* Testing Android

## The movie db api key
In order to use the app it is necessary to obtain an api key from [the movie db API](https://www.themoviedb.org/documentation/api). 

After getting an api key set it up in the development environment so that Gradle can load it into the project:
- create a file named api.properties under /app directory
- include a value for the movie database api key in the format: movie_db_api_key="your api key goes here"
- replace the text "your api key goes here" with your api key

## Other code
If required I can provide more Java and Android code I have produced.

### Java
* __Open Street Maps historic data manipulation__ - I'm currently working on this project for 
my master's second year dissertation at Myanooth University. It is a command line Java 
software that imports open street maps historic data into postgresql database and 
extract different data in CSV, JSON and GEOJSON formats

* __Software Artefacts Impact Analysis__ - a tool I created for the my first year masters 
dissertation at University of St Andrews last year. It consists of a Restful websservice 
server that store and serve data about software artefacts and their relationships in 
order to perform impact analysis for planning changes or detecting artefacts that need to 
be changed as consequence of other changes. The client side is a visualization tool written 
in HTML, jQuery and D3.js

### Android apps published on the Play Store
* [__Personal Finance Budgetting__](https://play.google.com/store/apps/details?id=carvalhorr.money) - App for personal finances control and budgetting. 
It automatically reads records spendings by reading SMS messages that banks in Brazil send 
every  time a debit or credit card is used. It also learns how the user classified the 
spendings based on venues and time that the the money was spent.


* [__Brazilian Verbs Conjugation__](https://play.google.com/store/apps/details?id=carvalhorr.verbs.br.free) - 12000 brazilian verbs conjugated in 19 different tenses 
cached in the device using disk space efficiently.

* [__Bus Timetables for Belo Horizonte__](https://play.google.com/store/apps/details?id=carvalhorr.onibus.bh) - the first app I created for Android. It displayes 
bus information for the public transportation system in Belo Horizonte city. It uses a 
rest webservice deployed to Google App Engine and GCM for updating data about the buses and 
allways have the most up to date bus info data cached on the device for users who don't have 
internet connection all the time.
