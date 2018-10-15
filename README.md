# Android Nanodegree

Here a repository of all work done on projects for Udacity's Android Nanodegree program.

## Project #8: Capstone Stage 2 - Build
*This is your chance to take the skills that you've learned across your Nanodegree journey and apply it to an app idea of your own. You control the vision!*

Here is the design concept side-by-side with the end result:

| Design | Build |
|--------|-------|
| <img alt='' height='500px' src='https://github.com/David-Jackson/Android-Nanodegree/raw/master/07-capstone-design/mockups/exports/01%20Start%20Screen.png'/> | <img alt='' height='500px' src='https://github.com/David-Jackson/Android-Nanodegree/blob/master/08-capstone-build/images/Screenshot_1539372019.png'/> |
| <img alt='' height='500px' src='https://github.com/David-Jackson/Android-Nanodegree/raw/master/07-capstone-design/mockups/exports/02%20Recording%20Screen.png'/> | <img alt='' height='500px' src='https://github.com/David-Jackson/Android-Nanodegree/blob/master/08-capstone-build/images/Screenshot_1539372023.png'/> |
| <img alt='' height='500px' src='https://github.com/David-Jackson/Android-Nanodegree/raw/master/07-capstone-design/mockups/exports/04%20Recording%20Screen%20Expanded.png'/> | <img alt='' height='500px' src='https://github.com/David-Jackson/Android-Nanodegree/blob/master/08-capstone-build/images/Screenshot_1539371294.png'/> |
| <img alt='' height='500px' src='https://github.com/David-Jackson/Android-Nanodegree/raw/master/07-capstone-design/mockups/exports/06%20Edit%20Trip%20Screen.png'/> | <img alt='' height='500px' src='https://github.com/David-Jackson/Android-Nanodegree/blob/master/08-capstone-build/images/Screenshot_1539372113.png'/> |
| <img alt='' width='250px' src='https://github.com/David-Jackson/Android-Nanodegree/raw/master/07-capstone-design/mockups/exports/07%20App%20Widgets.png'/> | <img alt='' height='500px' src='https://github.com/David-Jackson/Android-Nanodegree/blob/master/08-capstone-build/images/Screenshot_1539372096.png'/> |

<a href='https://play.google.com/store/apps/details?id=fyi.jackson.activejournal'><img alt='Get it on Google Play' width='300px' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'/></a>

## Project #7: Capstone Stage 1 - Design
*This is your chance to take the skills that you've learned across your Nanodegree journey and apply it to an app idea of your own. You control the vision! In Stage 1, you will design and plan your app, and receive feedback prior to building it in Stage 2.*

I have created UI mockups and a proposal that can be found [here](07-capstone-design)

## Project #6: Make Your App Material
*In this project, you will update the look and feel of an app to meet Material Design specifications.*

### Result
| Before | After |
|--------|-------|
|[XYZ Reader before redesign](https://github.com/David-Jackson/Android-Nanodegree/blob/master/06-make-your-app-material/images/device-2018-08-28-130020.gif) | [XYZ Reader after redesign](https://github.com/David-Jackson/Android-Nanodegree/blob/master/06-make-your-app-material/images/device-2018-08-28-124459.gif) |

## Project #5: Build It Bigger
*In this project, you will use Gradle to build a joke-telling app, factoring functionality into libraries and flavors to keep the build simple. You'll also configure a Google Cloud Endpoints development server to supply the jokes.*

This app did not leave a lot of room for creative input. It was still very informative, showing how to create free and paid versions of your app, how to create Android and Java Libraries, and briefly showing how to connect to backend services. 

## Project #4: Baking App
*In this project, you will create an app to view video recipes. You will handle media loading, verify your user interfaces with UI tests, and integrate third party libraries. You'll also provide a complete user experience with a home screen widget.*

### Features
- Material Design: from shared element transitions, to [animated vector drawables](https://github.com/David-Jackson/Android-Nanodegree/raw/master/04-baking-app/images/navigation_avd.gif), this app was designed from the ground up to incorporate material design principles.
- [Tablet Layouts](https://github.com/David-Jackson/Android-Nanodegree/raw/master/04-baking-app/images/device-2018-08-17-165802.png): this app also incorporates fragments and reusable layouts to provide a much better experience on bigger screens.
- Video Playback
- Homescreen Widget

[See the Baking App in action](https://github.com/David-Jackson/Android-Nanodegree/blob/master/04-baking-app/images/device-2018-08-17-163316.gif)

## Project #3: Popular Movies Part 2
*In this project, you will add to the app you built in Stage 1 by building on the detail view for each movie, allowing users to 'favorite' movies.*

### NOTE: 
To run this app, you must also have an [API Key](https://www.themoviedb.org/documentation/api) defined as a variable named `API_KEY` in the project's `gradle.properties` file.

### Features
- **Animated Vector Drawables**: The favorite floating action button features an animation when clicked (on devices with API 21+)
- Offline Use: API calls are cached in a SQLite database, and served when offline. This along with Picasso's Image caching enables this app to be usable when offline.
- Reviews and Videos: The detail screen shows user reviews and videos related to the movie that can be viewed in the browser or YouTube.

[See Popular Movies Part 2 in action](https://github.com/David-Jackson/Android-Nanodegree/blob/master/03-popular-movies-part-2/images/device-2018-05-16-112129.gif)

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
