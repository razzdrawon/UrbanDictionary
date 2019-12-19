Android UrbanDictionary
=========================

Urban Dictionary for Android.  
Urban Dictionary app! Urban Dictionary is the online dictionary with definitions written by everyone

This App consumes Urban Dictionary API (https://rapidapi.com/community/api/urban-dictionary/endpoints)

## Arquitecture
* [Kotlin][7]
* MVVM
* [ViewModel][2] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
* [LiveData][1] - Build data objects that notify views when the underlying database changes.

## HTTP
* [Retrofit2][8] - A type-safe HTTP client for Android and Java
* Gson converter
* RxJava adapter

## UI
* RecyclerView
* [DiffUtil][6] - Utility class that can calculate the difference between two lists and output a list of update operations that converts the first list into the second one.
* ConstaintLayout
* [Fragment][4] - A basic unit of composable UI.
* [persistentsearchview][5] - Android library designed to simplify the process of implementing search-related functionality.

## DI
* [Dagger2][9] - Dagger is a fully static, compile-time dependency injection framework for both Java and Android.

## Test
* [JUnit][10]
* Mockito

[1]: https://developer.android.com/topic/libraries/architecture/livedata
[2]: https://developer.android.com/topic/libraries/architecture/viewmodel
[3]: https://developer.android.com/guide/topics/ui
[4]: https://developer.android.com/guide/components/fragments
[5]: https://github.com/mars885/persistentsearchview
[6]: https://developer.android.com/reference/android/support/v7/util/DiffUtil
[7]: https://kotlinlang.org/
[8]: https://square.github.io/retrofit/
[9]: https://dagger.dev/
[10]: https://developer.android.com/training/testing/unit-testing/local-unit-tests
[11]: https://developer.android.com/jetpack/androidx/releases/room

Assumptions
-----------------
* App work only online
* Api external, then it depends on the availability, time and response of it. 
* Assume the api will keep the same structure/names response.
* Assume response will come with a short number of definition entries.
* No paginating or any kind of control over a large response.

Possible Alternatives/Enhancements
-----------------
- This app could be made following a different pattern like MVC or MVP. MVVM was decided because of testing simplicity.
- RxJava was used with a Single observable since we allow to emit only one item or throw one error per http call. Other kind of adapters or observables could be implemented for this in case we have a flow/stream or something more complex.
- Cache tecniques could be implemented in this app in order to make it work even with no internet connection and/or to reduce the response time, number of hits to the API, etc. 
- It could store info in internal DB's using SQL DB with [Room][11], Realm, or other libraries. Ans also using a Repository patter to insolate the data source layer.
- Another way of caching would be using cache-control with some configuration in the Retrofit client.
- Dependency Injection is achieved using Dagger2 but we could also use Koin or any other library.

Screenshots
-----------

![Home](screenshots/home.png "Home")
![Search by Term](screenshots/search.png "Make a search by term")
![Sort by Thumbs Up/Down](screenshots/sort.png "Sort by Thumbs Up or Down")
![Loading](screenshots/loading.png "Loading")
![Error](screenshots/error.png "Error")
