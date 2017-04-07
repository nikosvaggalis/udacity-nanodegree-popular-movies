# Android Developer Nanodegree 2017 - Popular Movies Database Stages 1 & 2


## Pumped up with Lambda expressions, custom Generic Loaders and Generic interfaces.

### Requirements

Requires  *Java 8*, *Jack toolchain* and *minSdkVersion 24*.It uses [The Movie Database](https://www.themoviedb.org/documentation/api) API to retrieve movies,so
you must provide your own *API key* in order to build the app.

# Project Overview
To become an Android developer, you must know how to bring particular mobile experiences to life. Specifically, you need to know how to build clean and compelling user interfaces (UIs), fetch data from network services, and optimize the experience for various mobile devices. You will hone these fundamental skills in this project.

Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, you’ll build an app to allow users to discover the most popular movies playing. We will split the development of this app in two stages. 


## In Stage1 your app will:

* Fetch data from the Internet with theMovieDB API.
* Use adapters and custom list layouts to populate list views.
* Incorporate libraries to simplify the amount of code you need to write
* Present the user with a grid arrangement of movie posters upon launch.
* Allow your user to change sort order via a setting:
The sort order can be by most popular or by highest-rated
* Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
  original title, movie poster image thumbnail, a plot synopsis (called overview in the api), user rating (called vote_average in the api),
release date

## In Stage2 your app will:
In this second and final stage, you will build a fully featured application that looks and feels natural on the latest Android operating system (Nougat, as of November 2016), adding functionality to the app you built in Stage 1:

* Allow users to view and play trailers ( either in the youtube app or a web browser).
* Allow users to read reviews of a selected movie.
* Allow users to mark a movie as a favorite in the details view by tapping a button(star).
* Create a database and content provider to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
* Modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.


## Libraries
* [RecyclerView-FlexibleDivider](https://github.com/yqritc/RecyclerView-FlexibleDivider)
* [Picasso](http://square.github.io/picasso/)



## License

    Copyright 2017 Nikos Vaggalis

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
