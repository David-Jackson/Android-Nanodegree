# Android Nanodegree

Here a repository of all work done on projects for Udacity's Android Nanodegree program.

## Project #1: Sandwich Club
*Design the layout for the detail activity so the different elements display in a sensible way. Implement the JSON parsing in JsonUtils so it produces a Sandwich Object that can be used to populate the UI that you designed.*

### Features
- Material Design UI: from the [title being placed on top of the media area](https://github.com/David-Jackson/Android-Nanodegree/raw/master/01-sandwich-club/images/device-2018-04-25-145254.png) to the use of [icons instead of text labels](https://github.com/David-Jackson/Android-Nanodegree/raw/master/01-sandwich-club/images/device-2018-04-25-145331.png), there are several Material Design principles being ultilized in this app.
- Dual Detail Views: Detail Activity and Detail Bottom Sheet.
- These dual detail views use the [same layout file](https://github.com/David-Jackson/Android-Nanodegree/blob/master/01-sandwich-club/app/src/main/res/layout/activity_detail.xml) and [same UI code](https://github.com/David-Jackson/Android-Nanodegree/blob/master/01-sandwich-club/app/src/main/java/com/udacity/sandwichclub/handlers/DetailViewHandler.java).
  - Only difference being that the Bottom Sheet view uses an [addition class](https://github.com/David-Jackson/Android-Nanodegree/blob/master/01-sandwich-club/app/src/main/java/com/udacity/sandwichclub/handlers/BottomSheetHandler.java) that extends the DetailViewHandler.
- Some sandwich attributes, description, aliases, origin, and ingredients, are optionial. If they are empty, the UI will adapt. 
- NestedScrollView: Scroll through the content, while keeping the image and title in view.

![Sandwich Club in action](https://github.com/David-Jackson/Android-Nanodegree/raw/master/01-sandwich-club/images/device-2018-04-25-143017.gif)
