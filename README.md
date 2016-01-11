# Hourglass
Boilerplate code for Android Wear watchfaces

This library comes from a lot of the watchfaces I've designed. Many have had similar interfaces and code, and I have had to copy and paste across them all. I've also wanted to tie in a lot of debug options but do so in a way that extends to all my watchfaces.

## Gradle
`compile 'com.github.fleker:hourglass:0.1.0'`
`compile 'com.github.fleker:hourglass-wear:0.1.0'`

## Mobile Preferences
A preferences activity is already included with an activity. Your mobile companion app can link to this class in an intent and have automatically synced settings.

## Wear Watchface
You can extend the `HourglassWatchface` class and override some of the methods to draw the watchface. There are methods you can use which are integrated with the Hourglass debugging system.

### Methods

| Method Name | Return Type | Description |
| :---        | :---        | :---        |
| `isAmbient()` | `boolean` | Returns true if the watch is in ambient mode or debugging ambient mode |
| `setWatchfaceParameters(WatchFaceStyle.Builder)` | `void` | This can be called to change the position of particular UI elements in the watchface |
| `setInteractiveUpdateRateMs(long)` | `void` | This changes the update rate for the watchface drawing. In a regular watchface, this should be `1000`. If you're doing animations, this should be much smaller, such as `30`. |
| `getTaps()` | `int` | Counts the number of times the screen is tapped and then returns that amount | 