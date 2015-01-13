About

    A google playstore scrapper.

Dependencies

    Jsoup, Json, Mysql server, Java

Notes about implementation

    Takes input as package id (com.kiloo.subwaysurf  in this case) and crawl all the details of the app from playstore and stores them mysql  DB. 

    Apart from info like 
    - name, 
    - package id, 
    - category, 
    - description
    - screen shots 
    - overall rating of app
    - number of downloads, 
    - number of people who have rated the app, 
    - All the Ratings, Reviews, name of reviewer and his Google plus ID available in the URL when we click there. 

    Scraps all the reviews that are there for any app and not the initial/top ones.

Compiling

    1) Using Eclipse
	   a) Import project from git with repository address (https://github.com/pulkitnandan/PlayStoreScrapper).
       b) Add libraries in java bild path Jsoup, Json, Mysql connector from libraries folder of repository.

    2) Import source folders after creating new java project and provide libray Jsoup, Json, Mysql connector in java build path from libraries              folder of repository..	

Usage

    1) Create Database with name playstore.
	2) Provide username and password in DatabaseConnection  of package  com.playstore.database package.
	3) Run LaunchApp in com.playstore.main and provide package id of app.
	
Troubleshooting
    
    1) Reinsert  cookie in  LaunchApp.java of package com.playstore.main  from browser after opening playstore website.
    2) Check database settings in DatabaseConnection of package  com.playstore.database package.
