# Overview #

### This is an application in Kotlin which represents the most popular articles from NY Times API. A new application is required to be created on NY Times Developer Portal https://developer.nytimes.com/ to enable Most Popular Articles API provider and get a new API_KEY which is required for our request. 
The application is designed in a typical master/detail app for tablet and mobile screens and able to work on offline mode.

On tablet, the screen presents the list of articles and article details side-by-side using two vertical panes.

On Mobile, two screens are presented:


#### A list of Articles screen ####

When touched, it leads to a separate single article details screen.


#### Article Details Screen ####

It shows a collapsed toolbar animated while scrolling. Data used for presentation are title, a large image of the article, published date, caption, by line, section, subsection and description.



### App Architecture and Libraries ###

1- Material Design

2- Android Architecture Components (MVVM, LiveData, Room)

3- Dagger Hilt for Dependency Injection

4- Kotlin Coroutines Flows

5- Retrofit for Networking

6- Glide for rendering images

7- Timber for logging

7- Google Truth for more readable test assertions and failure messages

8- Mockito for testing APIs tests cleanly & simply

9- MockWebServer to simulate a real server api with the responses of a fake server

10- Moshi to deserialize library to transform the JSON responses in objects

11- Roboelectric for testing database transactions


![tablet](https://user-images.githubusercontent.com/6572487/102697194-b493fb00-423c-11eb-8a67-b61729131211.png)


![mobile_screen1](https://user-images.githubusercontent.com/6572487/102697197-bfe72680-423c-11eb-9443-d72b6a50b6c3.png)


![mobile_screen2](https://user-images.githubusercontent.com/6572487/102697205-c4abda80-423c-11eb-9338-9743ca4c2a9f.png)


![mobile_screen2 1](https://user-images.githubusercontent.com/6572487/102697210-ca092500-423c-11eb-89b1-b5e66e8ff16b.png)
