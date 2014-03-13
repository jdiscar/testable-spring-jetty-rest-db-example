This is a prototype I put together in 2011. I had previous experience with the Spring framework and was considering using it for personal web development. I ended up making a very barebones testable rest service that I've decided to share. Aside from authentication, it has a lot of features that someone learning Spring might like. IMHO, Spring is a very difficult framework to get started with, so the more bite sized examples out there, the better. This code is a bit outdated, especially in using XML instead of annotations for configurations, but I still think it can be useful.
  
The application itself is very simple (and also non-sensical). It provides two three rest services:  
	1. Create a Comment in the database
	2. Get Comments from the database as JSON
	3. Get Preconfigured Comments as JSON

My goals for this prototype were as follows:  
	1. Cleanly define REST api that returns JSON.
	2. Injectable database interaction with an ORM.
	3. JSON responses that can be generically wrapped by other objects without explicit serialization logic.
	4. Built with integration testing via maven.

(1) was satisfied by using jersey and jackson annotation. Since doing this, I don't think I care for Jackson annotations, but I do like the annotations for the REST services.  
(2) is standard Spring practice. I went with Hibernate for the ORM as it's the most popular and I was already familiar with it.  
(3) was somewhat messy using the Jackson annotations. I think I'd go into a new project without having a clear idea of what the JSON so look like and just accepting whatever was spit out by default. I did go out of the way to make sure properties with empty values were not included.  
(4) was done by using an embedded Application Server (Jetty) and an in memory database (HSQLDB.) It works quite nicely.  

Making a tiny app makes the scaffolding logic very obvious.   Even without maven/spring/hibernate knowledge, it should be straightforward to figure out how things work. But here's a short guide:  

- The source code is under the java folders.  
- Spring and Hibernate Configurations are under the resources folders.  
- The webapp configuration is under the webapp folders.  
- Test contains everything needed for test, Main is everything for production.  
- pom.xml is used by maven to test and build the project. The maven is somewhat complicated due to the embedded jetty server.  

mvn install builds and tests the webapp.  A war is built under target, which can be deployed on a production server.  

The JSON Responses look like this:  
- Multiple Comments, Version Defined: {"status":"200","message":{"comments":[{"id":"2","version":"version","message":"first"},{"id":"1","version":"version","message":"second"}]}}
- Single Comment, Version not Defined: {"status":"200","message":{"comments":{"id":"3","message":"Test Create Message"}}}

In the end, I decided to use the Play! Framework for my personal web development. I find Spring clunky and painful to set up for a new project. Play! is a lot easier to prototype on and it performs/scales extremely well in production. If you have a choice, I'd recommend giving the Play! Framework a try if you're considering using Spring.