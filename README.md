# About
This project was developed as part of the Udacity Associate Android Developer Fast Track nanodegree. The app displays popular and best rated movies obtained from [The Movie DB] (https://www.themoviedb.org) through its RESTful API. Intructions on how to setup the a api key can be found below

After the project was submitted and graded by Udacity it is used as a learning project where new techniques are continually included. The extra techniques and libraries include:
- [Dagger2](https://google.github.io/dagger/)
- [Retrofit](http://square.github.io/retrofit/)
- [UI testing with Espresso](https://developer.android.com/training/testing/ui-testing/espresso-testing.html)
- [Picasso](http://square.github.io/picasso/)

Other libraries and techniques will be incorporated such as Kotlin and RxJava. 

## The movie db api key
In order to use the app it is necessary to obtain an api key from [the movie db API](https://www.themoviedb.org/documentation/api).

After getting an api key set it up in the development environment so that Gradle can load it into the project:
- create a file named api.properties under /app directory
- include a value for the movie database api key in the format: movie_db_api_key="your api key goes here"
- replace the text "your api key goes here" with your api key
