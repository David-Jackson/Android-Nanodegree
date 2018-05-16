# Android Nanodegree

Here a repository of all work done on projects for Udacity's Android Nanodegree program.

## Project #3: Popular Movies Part 2
*In this project, you will add to the app you built in Stage 1 by building on the detail view for each movie, allowing users to 'favorite' movies.*

### NOTE: 
To run this app, you must also have an [API Key](https://www.themoviedb.org/documentation/api) as a string with the name of `api_key` in a resource file.

### Features
- **Animated Vector Drawables**: The favorite floating action button features an animation when clicked (on devices with API 21+)
- Offline Use: API calls are cached in a SQLite database, and served when offline. This along with Picasso's Image caching enables this app to be usable when offline.
- Reviews and Videos: The detail screen shows user reviews and videos related to the movie that can be viewed in the browser or YouTube.

![See Popular Movies Part 2 in action](https://github.com/David-Jackson/Android-Nanodegree/blob/master/03-popular-movies-part-2/images/device-2018-05-16-112129.gif)

## Project #2: Popular Movies Part 1
*In this project, you will build an app to help users discover popular and recent movies. You will build a clean UI, sync to a server, and present information to the user.*

### NOTE: 
To run this app, you must also have an [API Key](https://www.themoviedb.org/documentation/api) as a string with the name of `api_key` in a resource file.

### Features
- **Shared Element Transitions**: Smooth, meaningful transitions make interacting with this app much more enjoyable.
- **Fragments**! By implementing fragments for the [list](https://github.com/David-Jackson/Android-Nanodegree/blob/master/02-popular-movies/app/src/main/java/fyi/jackson/drew/popularmovies/fragment/MovieListFragment.java) and [detail](https://github.com/David-Jackson/Android-Nanodegree/blob/master/02-popular-movies/app/src/main/java/fyi/jackson/drew/popularmovies/fragment/MovieDetailFragment.java) views, we are able to achieve smooth transitions without the overhead of shared element transitions with activities.
- Material Design: This app capitalizes on its use of fragments with a shared [CollapsingToolbarLayout](https://github.com/David-Jackson/Android-Nanodegree/raw/master/02-popular-movies/images/device-2018-05-08-170943.png), leading to extra continuity between screens.
- Laying groundwork for Part 2: Layout considerations have been given for how to implement the favoriting feature required in Part 2.

[See Popular Movies Part 1 in action](https://github.com/David-Jackson/Android-Nanodegree/blob/master/02-popular-movies/images/device-2018-05-08-163344.gif)

## Project #1: Sandwich Club
*Design the layout for the detail activity so the different elements display in a sensible way. Implement the JSON parsing in JsonUtils so it produces a Sandwich Object that can be used to populate the UI that you designed.*

### Features
- Material Design UI: from the [title being placed on top of the media area](https://github.com/David-Jackson/Android-Nanodegree/raw/master/01-sandwich-club/images/device-2018-04-25-145254.png) to the use of [icons instead of text labels](https://github.com/David-Jackson/Android-Nanodegree/raw/master/01-sandwich-club/images/device-2018-04-25-145331.png), there are several Material Design principles being utilized in this app.
- Dual Detail Views: Detail Activity and Detail Bottom Sheet.
- These dual detail views use the [same layout file](https://github.com/David-Jackson/Android-Nanodegree/blob/master/01-sandwich-club/app/src/main/res/layout/activity_detail.xml) and [same UI code](https://github.com/David-Jackson/Android-Nanodegree/blob/master/01-sandwich-club/app/src/main/java/com/udacity/sandwichclub/handlers/DetailViewHandler.java).
  - Only difference being that the Bottom Sheet view uses an [addition class](https://github.com/David-Jackson/Android-Nanodegree/blob/master/01-sandwich-club/app/src/main/java/com/udacity/sandwichclub/handlers/BottomSheetHandler.java) that extends the DetailViewHandler.
- Some sandwich attributes, description, aliases, origin, and ingredients, are optionial. If they are empty, the UI will adapt. 
- NestedScrollView: Scroll through the content, while keeping the image and title in view.

[See Sandwich Club in action](https://github.com/David-Jackson/Android-Nanodegree/blob/master/01-sandwich-club/images/device-2018-04-25-143017.gif)
