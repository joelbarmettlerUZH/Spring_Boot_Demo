# Quick introductoin to Spring Boot
This Guide walks you through the process of how to build a RESTful API using the Spring Boot Framework.

## Spring Initializr
The [Spring Initializr](https://start.spring.io) helps you getting started with your app. What the Spring initializr essnetialy does is create the **build.gradle** (if you want to generate a gradle project, which is recommended) or the **pom.xml**-File (if you chose to work with Maven instead), including Gradle/Mavent itself and the right folder structure for your application.
- The **"Group Artifact"** should have the following pattern: com.domain.projectname *(com.yoursite.yourproject)*
- The **"Artifact"** is the real name of your project: your-project-name
- Under **"Dependencies"**, chose all the Spring Boot Starter dependencies your application needs, like "Web" or "JPA". You can find a list of starters [here](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-starter). For our project, we need **Web** *(Documentation: Starter for building web, including RESTful, applications using Spring MVC. Uses Tomcat as the default embedded container)*,  **JPA** *(Starter for using Spring Data JPA with Hibernate)* and **H2** *(The H2 database provides a browser-based console that Spring Boot can auto-configure for you.)*.


![Spring Initializr](https://github.com/joelbarmettlerUZH/Spring_Boot_Demo/raw/master/Resources/Spring_1_initializr.png)

Now click on "Generate Project". This will create a .zip-Folder that consists of the correct Folder-Structure a Spring-Boot Application should have, as well as the build.gradle-File and Gradle itself.

## Gradle
Now, unzip the downloaded folder and open it as a new Gradle Project with your IDE of [choice](https://www.jetbrains.com/idea/). Let's have a look inside the gradle.build file that was generated:

```gradle
buildscript {
	ext {
		springBootVersion = '2.0.0.RELEASE'
	}
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
	}
}

[...]
```

The springBootVersion indicates the spring version we are using. This is important because Spring Boot contains over 140 third-party library versions and - by upgrading this version number - spring automatically updates all these libraries for us as well.
The repositories marks the sources of our libraries. Since all our chosen dependencies are found on Maven, the mavenCentral() is our only source.
Finally, the dependencies gradle-plugin is respondible for matching the current spingBootVersion we are using with the right library versions we include into our project. The gradle-plugin is also responsible to pack all these libraries and bundles them into one runnable JAR application which is runnable on every machine in the JVM, which makes deployment of the application easy and powerfull.

You can specify other maven URLs with adding the URL to the repositories under the "repositories" attribute:
```gradle
// Showcase
    repositories {
        mavenCentral()
        maven { url "https://repo.spring.io/snapshot" }
        maven { url "https://repo.spring.io/milestone" }
        maven { url "https://plugins.gradle.org/m2/" }
    }
```

Next, let's focus on plugins:

```gradle
[...]
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = 1.8

repositories {
	mavenCentral()
}
[...]
```

The eclipse plugin is only usefull when you are working with Eclipse as your IDE, IntelliJ handles gralde files automatically. The other plugins are either needed by spring or gradle. Under group, you find your package group name and the sourceCompatibility indicates the Java version which can handle the project best.

```gradle
[..]
dependencies {
	compile('org.springframework.boot:spring-boot-starter-data-jpa')
	compile('org.springframework.boot:spring-boot-starter-web')
	runtime('com.h2database:h2')
	testCompile('org.springframework.boot:spring-boot-starter-test')
}
```

The dependencies shown here are the ones you have selected from your spring-initializr. When you want to include new dependencies, add them here. The dependencies listed here will be automatically integraded into our projects and made usable for us instantly. Note that *spring-boot-starter-test* was automatically added as well, which is a virtual package containing Spring Boot Tests, JUnit, Spring Test and many more tools to test our application.

# Spring Properties
A Spring Boot application comes with a file called **application.properties**, which is located in the "src - main - resources" folder. Initially, this file is completely empty since spring boot has its standard properties set. When we define a property in the application.properties file, we actually overwrite the standard property with our own values.  

You can find all the settings that you can configure in the application.properties file [here](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html). The few most important ones are:
- app.name = YOURAPPNAME
- app.description = YOUR APP DESCRIPTION
- server.port = 34567

You can reference these properties to each other to includ the app-name inside the app-description:
> app.description = The app ${app.name} is a Spring Boot application

# Starting Spring Boot
Now that Gradle is configured, let's have a look at the only **.java**-File that was created by the Spring initializr. You find it under "src - main - java - com - example - demo - DemoApplication.java". This path and Filename will be different depending on your set project name.

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
```

This tiny class is a fully operational web application. The **@SpringBootApplication** annotation tells Spring Boot to set up the default configuration for Spring Projects, Starts the applicatoin context including the whole RESTapi, Services or Repositories, starts the Tomcat server and finally performs a recursive path scan which finds all Spring Annotations (@Something) and injects methods into these classes, making them pure Java again. This **@Something** Annotation will be used heavily in String Boot, since Spring is an annotation-based Framework. Adding such an annotation tells Spring that this Class has a well-defined purpose, and spring will automatically extend our annotated class with important features matching our goals. The main method does nothing more than call SpringApplication to run our current Class. This code is already executable: When pressing "Run" in your IDE or right-clicking the build.gradle-File with hitting "Run", a webserver on the default Port :8080. When you visit [localhost](http://localhost:8080/) in your browser, you will see an error page since we have not defined what should happen when we visit the page */* - but not a message that the page is not found because indeed a server connection was found - it just does not yet return anything back to you.

We need such an annotated main-method because we are getting a single JAR file in the end that should be independently executable on every system that runs a JVM. To make this possible, we include a main method into our applicatoin that runs SpringApplicaton, which will then start our Webserver Container, initialize all services, hosts the Web-Services and so on. We then push our main Class into that server container in order to make it run inside the server. Which is exactly what we do with this single line of code:

```java
SpringApplication.run(DemoApplication.class, args);
```

Now let's have a look at our imports. **org.springframework.boot.autoconfigure.SpringBootApplication** let's us use the **@SpringBootApplication** annotation, while **org.springframework.boot.SpringApplication** includes the **SpringApplication**-Class on which we call the method **run**. We could have passed some arguments to the class we invoke (DemoApplication.class), which would then be retreived by the *String[] args*-Argument of the main method.

## REST Controller
A controller is a simple Java-Class annotated with **@RestController**. A Controller receives a request and calls a Method (Service) to handle it. The Requets can be of 4 different types:
- *GET*: The Person that sends the request to the server wants to **receive** information from it
- *POST*: The sender wants to **create** a new object in the backend.
- *PUT*: The sender would like to **modify** an existing resource.
- *DELETE*: The sender wants to delete an object

The Request-Types define the context, the intention of the request. In theory, you could create a GET-Request that deletes Objects, or create a PUT-Requests that modifies absolutely nothing and just returns data. These types serve more as "intention specifiers" and so that we can map multiple different request types to the same context / URL.

Every request is sent to a specific URL. Behind each URL, a REST Controller is located. One such controller handles either a *GET/POST/PUT/DELETE* Request. The REST-Controller receives one of these request types from the browser, together with an optional JSON containing information about the request. The Controller then invokes some Methods to handle the request and finally responds to the Request with a JSON and **STATUS CODES** that indicate whether the request was successfull/failed/invalid. The Controller itself is only responsible for taking the request - calling the right function - sending a JSON back. He is not responsible for computing anything.

To create a new Rest-Contorller, we need to define a **@RestController**, the URL where it should response to, and the request-type it should handle. First, we create a new Java package called **Controllers** and a new Java-Class inside of it that is named *functionality*Controller, like ExampleController. One Controller should always handle at most a small set of URLs and be responsible for one certain type of requests. Like when we want to controll User-data via the RESTapi, we create a UserController that allows managing, creating, deleting and modifying users in the backend. Accordingly, the URL will be like http://www.YourDomain.com/api/v1/user, to which the frontend can send GET/POST/PUT/DELTE requests, the UserController receives and handles them.

When the Java-Class is created, we create the new Controller as follows:

```java
package com.example.demo.Controllers;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExampleController{
    private final String CONTEXT = "/api/v1/example";
    [...]
}
```

Spring Boot knows that this Class is a RestController and we have set our context to the url */api/v1/example*. Each method we add to this class will now handle one specific requesttype to one specific URL. Let's add such a request handler:

```java
package com.example.demo.Controllers;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExampleController{
    private final String CONTEXT = "/api/v1/example";

    @GetMapping(value = CONTEXT)
    public String greeting(){
        return "Example content";
    }    
}
```

What our simple controller does is to wait for a GET-Request at the path "/api/v1/example", and then send back the text "Example content". To show that this already works, run your java application and navigate to [http://localhost:8080/api/v1/example](http://localhost:8080/api/v1/example) on your browser (the standard request type when you visit an URL is a GET-Request). See what is displayed?

>Example content  

Seems to work fine. Let's get a step further then and let the user provide some data to the Controller that we can use inside of our controller. Now, let's modify our Controller to receive two URL-Based parameter called "name" and "number".

```java
package com.example.demo.Controllers;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;

@RestController
public class ExampleController {
    private final String CONTEXT = "/api/v1/example";

    @GetMapping(value = CONTEXT)
    public String greeting(
            @RequestParam(required = true, defaultValue = "Unknown") String name,
            @RequestParam(required = false) String number) {
        if(number == null){
            return "Example content for " + name;
        }
        int num = Integer.parseInt(number);
        return "Example content for " + name + " with number " + (num + 3);
    }
}
```

There are several things going on here. First, we have specified two arguments for our greeting method: Two Strings, name and number. The first String is required but has a default value on "Unknown". The second String is not required. Then, our REST-Controller looks whether the user has specified a number of not and forms it response accordingly.

But how can the user specify the values for name and number? Well, he simply adds it to the API-Call URL like this:
**http://localhost:8080/api/v1/example?name=joel&number=4**. This results in the following response from our API:

> Example content for joel with number 7

But we are not quiet done yet. We talked about HTTP Status codes that indicate whether the request was successfull. We also would like our URL's to be more flexible, so let's add these two features.

```java
package com.example.demo.Controllers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;

@RestController
public class ExampleController {
    private final String CONTEXT = "/api/v1/example/{id}";

    @GetMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public String greeting(
            @PathVariable Long id,
            @RequestParam(required = true, defaultValue = "Unknown") String name,
            @RequestParam(required = false) String number) {
        if(number == null){
            return "Example content for " + name + " to the ID: "+id;
        }
        int num = Integer.parseInt(number);
        return "Example content for " + name + " with number " + (num + 3) + " to the ID: "+id;
    }
}
```

We did two things here. First, we modified the URL that handles the request. Instead of ""/api/v1/example", we now serve **"/api/v1/example/{id}"**. Note that the *id* is a variable and not a fixed value. We can now retreive this variable in the greeting method with the **@PathVariable** notation as a String or - in our case - a Long. This id variable can then be used in the context. Further, we added a **@ResponseStatus(HttpStatus.OK)** to the Controller, which gives an OK-Status code back to the frontend when the method is called. Other possible HttpStatus codes would be *ACCEPT*, *CONFLICT*, *CREATED* and many more. To send a request to this Controller, we now need to specify the ID in the URL-Path as follows: **http://localhost:8080/api/v1/example/3?name=joel&number=4**. Note that I chose the ID to be 3 here, just as an example.

But why would you need such a parameter inside of the URL-Path when you could equally provide is as a RequestParam? Well, it turns out that the URL defines more "what content you want to access", while the RequestParam is more "additional information to that request". Let me give you an example. When you have a UserAPI at "/Users/" and you want to get access to the user with a particular ID, you use a PathVariable: "/Users/{UserID}/". When you now want to specify what information you would like to get from the information, like "get me the last 5 books the user with ID = 1 has read", we use RequestParam: "/Users/1?NumberOfBooks=5" RequestParameters are often optional and let you filter/specify the information you want to get from the api.

# Resources

The resource Package in our application will hold all classes that we have in our system that are not directly involved into the REST-Communication. Such a class can be a User, a Game, or anything else.

For now, we have just passed arround primitive data types, but spring Boot is much more powerfull than that. Let's build a REST-Controller that returns Objects of type "User". First, we create a simple User-Java Class in a new Package called "Resource" and insert the User class into it.

```java
package com.example.demo.Resources;

public class User {
    private static int clsID;

    private String name;
    private long ID;

    public User(){
        this.ID = ++clsID;
    }

    public User(String name){
        this.name = name;
        this.ID = ++clsID;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getID() { return ID; }
}

```

We have successfully created a very simple Resource on which our RESTapi can rely. Note that we have two constructors: One that receives arguments (name) and one that does NOT. Your resources always have to have an empty constructors and Setters for all values that you want to set, because spring boot is going to parse JSON files into Objects later on by calling the empty constructors and setting all values via Setters (that's why we also need setters for every instance variable we want to set).

# Services

We want to make a new Controller that serves a GET-Request under the domain "/Users/". The GET-Request shall create three new users and return them as a JSON. But recap what he have learned about Controllers: "They shall only map requests to responses - nothing else". So where would we create our three users? We outsource this task into a **Service**. First, we create a new package called "Services" and a new Class "UserService".

```java
package com.example.demo.Services;
import com.example.demo.Resources.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>(Arrays.asList(
            new User("Joel"),
            new User("Marius"),
            new User("Daniela")
        )
    );

    public List<User> getUsers(){
        return users;
    }
}
```

We created a new Service that has a List of three Users and a public method *getUsers* which returns these Users. A RESTService is annotated via **@Service**.

Now we need create a new REST-Controller UserController that reacts to "/Users/" and calls the Service to return three users on GET-Requests.

```java
package com.example.demo.Controllers;

import com.example.demo.Resources.User;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final String CONTEXT = "/api/v1/User";

    @Autowired
    private UserService userService;

    @GetMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers(){
        return userService.getUsers();
    }
}
```

There is a few things going on here that need explenation. We created a new UserController at the context "/api/v1/User" as we did before. But now we want to use a REST-Service, so we have to create an instance of our UserService class. But we actually have not created such an instance, instead we told Spring to create one for us using the **@Autowired** annotation. Why haven't we just created an instance like *private userService = new UserService*? Well, because that would create a new instance of UserController every single time we needed that controller in one of our classes - which is definitely NOT what we want. We want to have one single instance of our userService, and we want this instance to be shared among all Controllers in our API. Why is that so important? Well, our Service is quiet simple. We have three users as instance variables. Let's imagine we create a Controller which let's the user add new users. Simple, right? If now every Controller had its own copy of the Service Instance, only the one controller modifying the Users-List would have new, updated user list. All the other services would still have their own User-List containing three users. But how would we make a shared instance between multiple classes? We don't - spring does. By annotating our Service as a *Service*, and calling spring to *Autowire* the userService, spring first checks whether there already IS an instance somewhere and injects this one, otherwises creates a new instance for us.

Now in our GetMapping controller, we can simply call the userService.getUsers() to receive the list of users from the UserService.

When you now visit **http://localhost:8080/api/v1/User**, you get a nicely formated JSON as a response containing all attributes of our User instances.

> [{"name":"Joel","id":1},{"name":"Marius","id":2},{"name":"Daniela","id":3}]

The last thing we want to have a look at is how to use the other Request-Types like POST/PUT/DELETE. As already mentioned, the type itself does not really matter since it is just a way of expressing the callers intention. But when we use POST or PUT, the caller wants to create or modify an object. Therefore, he needs a way of telling us how this object should look like. Of course, he could in theory pass all these arguments as @RequestParameters, but this would be highly unpractical with larger objects. Instead, he shall be able to provide a JSON-Representation of an actual object that he wants to create. Our Controller shall then parse this JSON into a real object and then call a Service with this object.

First, let's add a new Method called *addUser* to our UserService, which receives a User as an argument and adds it to the List of users.

```java
// in UserService.java
public void addUser(User user){
    users.add(user);
}
```

Now, we implement a new POST Method in our UserController.

```java
// in UserController.java
@PostMapping(value = CONTEXT)
@ResponseStatus(HttpStatus.CREATED)
public void createUser(@RequestBody User user){
    userService.addUser(user);
}
```

Looks like black magic. We have created a new method accepting POST-Requests on the Context, which is "/api/v1/User". The requests needs to have a **@RequestBody** that is a JSON representation of a User. This user is then passed to the UserService to add it to the list of users. How is that supposed to work? Well, in order to create a new User, we need to have a representation of its attributes in the form of a JSON file. Spring will then take that json file and parse it into an object. In our design, a User is defined by two attributes: an ID and a Name. Now have a look at the empty constructor we defined. It set's the ID for us, but it does not set the Name. Spring is going to call exactly this constructor and then call all Setters that correspond to the values we pass in the JSON file. So spring creates a new User which then has no "name" attribute. But in the JSON file we provide when calling the POST-Request, we have a line that states

> "name":"SOMENAME"

Spring will recognize that there is a Setter for name, called setName, and will call it with the value "SOMENAME" after the object is created. That way, we get a fully defined user-object.

To test whether our API works, first make a GET-Request to **http://localhost:8080/api/v1/User**. You will get our three users as usual:

>[{"name":"Joel","id":1},{"name":"Marius","id":2},{"name":"Daniela","id":3}]

But now, send a POST-Request via [Postman](https://www.getpostman.com/) to the same address with a *raw* response Body of Type **JSON(application/json)**. In the field underneath, write your JSON as follows:

>{"name": "Reto"}

This implies that we want to create (POST) a new user with name set to "Reto". Click on "Send". You will get no response, but a Status code stating **201 Created** - which is exactly what we want. Now make another GET-Reqeust to **http://localhost:8080/api/v1/User**. See what we get:

>[{"name":"Joel","id":1},{"name":"Marius","id":2},{"name":"Daniela","id":3},{"name":"Reto","id":4}]

# Entities

So far, our Spring Boot application only manipulated object instances through services, but we were not connected to a database yet. Remember the package JPA we have added to our dependenceis when creating the project through the initializr? JPA is a java library that lets us ue Object relational Mapping, a way to write classes into databases: Every Class becomes its own table, every Class attribute becomes a table column.

Every Class that we want to map into our database needs to be annotated as an Entity. Let's create a new Java package called "Entities". There, we create a new Entity called "UserEntity", which will be exactly like our User Resource, but as a database representation. We will modify our Program in a way such that we can delete the User Resource in the end and fully rely on the User Entity.

```java
package com.example.demo.Entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private long ID;

    @Column(unique = true)
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getID() { return ID; }
}
```

Our code changed quiet a bit. We sill have our fields for ID and name, but we have no constructor and no class variables anymore. Instead, we have marked our class with the **@Entity** annotation, showing Spring Boot that this class should be converted to a database representation. The **@Id** annotation marks the fields which act as a primary key in our database. **@GeneratedValue** means that this Value is not set by the Constructor but an automatically chosen and unique value, like the *Autoincrement* for SQL databases. Finally, **@Column** marks all fields in our class that should become a column. Column can takes additional optoinal attributes like *unique*, which states that no two entries with the same value of this attribute can exist in the table. Note that we do not have any constructors anymore since we will not create instances of this class anymore *(there could be entities which you want to instantiate by hand - there is nothing wrong with that. We just don't need it here in our application, we therefore do not construct a constructor)*, but only create instances through the database with the help of Spring. Therere, we do not need our constructor with arguments, which implies that we can get rid of the empty constructor as well since the empty constructor is implicitely given anyways when no other constructor is defined in the class.

Fine, we got our Database Table now. But how are we going to create new entries, manipulate and delete them, and create queries for our table? We will find out when we talk about repositories.

## Repositories
A repository the interface that we use to communicate with a database table. For each Entity, we also have to define a corresponding repository that defines methods which we can use to manipulate our table. Therefore, let's create a new package called "Repositories" and a new interface *(you see why an interface in a minute)* inside of it called "UserRepository".

```java
package com.example.demo.Repositories;

import com.example.demo.Entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
    List<UserEntity> findByName(String name);
}
```

Looks quiet empty, right. Let's see why. In this interface, we have to define the functions we want to make use of in order to access our table. When you think about it, it becomes clear that most of our Respository-interfaces will implement mostly the same methods: *getByID*, *getAll*, *deleteByID* and so on. Luckily, spring offers a sollution to inherit all such methods from **CrudRepository**. So we simply extend this CrudRepository and provide it with two types: What type of Class our from our DataBase the Repository should manage, and what type the primary key is. Now that we extend CrudRepository, we can use the UserRespository Interface for getting data out of the Table, inserting new data into it and manipulating existing data.

But there are certain limits to CrudRepository: There is no method that let's us find a User by its name, since this is no general query that nearly any Table needs. So we have to create this method by our own, which is why we have the *User findByName(String name)* method there. Even though we are just defining a method in an interface for which we do not provide an implementation, we can actually execute the methods we define here in the interface. Spring analyzes the name of the method and automatically decides what actions surely need to be taken, and finally implements the method for us. So by saying *findByName(String name)*, we implicitly state that the method shall find an enitity by its name given as an argument. This implies that we have to follow a naming scheme for our methods, which is:
**findByAttribute(argument)**, where *attribute* needs to be the name of an entity attribute and *argument* is an argument of the same type as the entity attribute.


## Services using Repositories
Now let's modify our Services to not have a hardcoded list of Users but instead work with the database to get/add the users. Let's create a new Service called *UserentityService* so that we have both of our services, just to compare them at the end. The UserentityService looks as follows:

```java
package com.example.demo.Services;
import com.example.demo.Entities.UserEntity;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserentityService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getUsers(){
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public void addUser(UserEntity user){
        userRepository.save(user);
    }
}
```

We are again marking this UserentityService as a Service via the **@Service** annotation. Then, we autowire the user repository to the variable called userRepository. Finally, we replaced the content of our methods by calls to the userRepository database.

To return a list of all UserEntities from our database, we call userRepository.findAll(), which returns us an iterable of all entries of our database. Then, we loop over these entires and call the List users to add the elemnet to its list via a lambda expression.

To add a user, we simply pass it to the database using userRepository.save(user).

## Controller using Entities
Let's create a new controller as well using the new Userentity. It's pretty much copy-paste from the already existing UserController.

```java
package com.example.demo.Controllers;

import com.example.demo.Entities.UserEntity;
import com.example.demo.Services.UserentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserentityController {
    private final String CONTEXT = "/api/v1/Userentity";

    @Autowired
    private UserentityService userentityService;

    @GetMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> getUsers(){
        return userentityService.getUsers();
    }

    @PostMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserEntity user){
        userentityService.addUser(user);
    }
}
```

I have just replaced all "User" with "UserEntity" and the "UserService" with "UserentityService". Let's try this out. Restart the server and make a GET-request to "/api/v1/Userentity". Of course, it returns nothing since we have no users yet.
Now, create a new user using postmans POST-method.

>{"name": "Joel"}

Now make the GET-Request again. We successfully added a new entry into our database using our API.

>[{"name":"Joel","id":1}]

# Entity Relations

For now, our Entities only consisted of primitive datatypes. What if we want to have references to other objects inside of our database - like foreign keys? Let's create a simple new Entity called "University". Each user will have a field with a reference to one specific university where he is currently immatriculated.

```java
package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UniversityEntity {

    @Id
    private String name;

    @Column
    private int numberOfStudents;

    @Column
    @JsonIgnore
    private int semesterCosts;

    public UniversityEntity(String name, int numberOfStudents, int semesterCosts){
        this.name = name;
        this.numberOfStudents = numberOfStudents;
        this.semesterCosts = semesterCosts;
    }

    public UniversityEntity(){}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getNumberOfStudents() { return numberOfStudents; }
    public void setNumberOfStudents(int numberOfStudents) { this.numberOfStudents = numberOfStudents; }
    public int getSemesterCosts() { return semesterCosts; }
    public void setSemesterCosts(int semesterCosts ) { this.semesterCosts = semesterCosts; }
}
```

The semesterCosts field is marked as **@JsonIgnore** which implies that this value will be ignored when converting our Object instance to a Json representation - so we won't send this attribute to the user if he requests the object as a Json.

Let's quickly define the University Repository as well.

```java
package com.example.demo.Repositories;

import com.example.demo.Entities.UniversityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UniversityRepository extends CrudRepository<UniversityEntity, Long>{
    List<UniversityEntity> findByName(String name);
}
```

Now we modify our UserEntity so that he has a field of type University. Don't forget to create new Getters and Setters for the University attribute.

```java
package com.example.demo.Entities;
import javax.persistence.*;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private long ID;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private UniversityEntity university;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getID() { return ID; }
    public void setUniversity(UniversityEntity university) { this.university = university; }
    public UniversityEntity getUniversity() { return university; }
}
```
Note that we marked the university field as a foreign key of type **@ManyToOne**, meaning many UserEntities can have a relationship to one university. Such relationships can also be of type **@OneToOne**, **@OneToMany** or of type **@ManyToMany**.

## Repository for accessing Entity Relations
How would we now define a method in our UserentityRepository what would get us all the users of a certain university? Well, our schema **findByattribute(argument)** would no longer hold since we do not want to provide an instance of *university* of which we want to find the users - but instead want to provide the ID of the university as a long. Well, all we need to do is to extend our method naming scheme a bit:
**findByEntityAttribute(argument)**. In this manner, a new Repository method to get all students that are immatriculated on an university with a certain ID would look as follows:

```java
package com.example.demo.Repositories;

        import com.example.demo.Entities.UserEntity;
        import org.springframework.data.repository.CrudRepository;

        import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
    List<UserEntity> findByName(String name);
    List<UserEntity> findByUniversityName(String name);
}
```

## Controller dealing with Entity Relations
When we now want to create a new instance of *User* via POST-Method, we also need to include the university in the Json file. Let's try that out. Make a POST-Request to "api/v1/Userentity" with the following Json:

>{"name": "Joel","university":{"name":"UZH","numberOfStudents":20000,"semesterCosts":720}}

This will throw you an error. Why? Because the University we specified does not even exist. We need to create the university first. But we actually don't want to create all our universities using POSt-methods. Nobody shall actually create universities, we want to have a fixed list of universities that can be mapped to users. How would we achieve that? Well, we preload our universities database with data. Let's see how.

# Preloading a database with data
We have our database which we can manipulate. But until now, we had to manually post all our data to the database after starting the application. In most cases, this is not what we want - we want to have prefilled data in our databases. To achieve this, we define a **CommandLineRunner**-Bean in the main Appliaction:

```java
package com.example.demo;

import com.example.demo.Entities.UniversityEntity;
import com.example.demo.Repositories.UniversityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UniversityRepository universityRepository) {
        return (args) -> {
            universityRepository.save(new UniversityEntity("UZH", 20000, 720));
            universityRepository.save(new UniversityEntity("ETHZ", 18000, 980));
            universityRepository.save(new UniversityEntity("ZHAW", 9000, 530));
            universityRepository.save(new UniversityEntity("EPFL", 12000, 600));
            universityRepository.save(new UniversityEntity("UNILU", 120, 440));
        };
    }
}
```

This creates universities for us and saves them into the database. The CommandLineRunner get's executed first before any other custom application runs on our server.

Let's now crate a user via Json POST-Request to "api/v1/Userentity" again:

>{"name": "Joel","university":{"name":"UZH"}}

Note that we only have to include the primary key *(here, the name)* of an already existing university to make the connection between a user and its corresponding university. Now let's make a GET-Request to the same URL:

>[{"name": "Joel","university": {"name": "UZH","numberOfStudents": 0},"id": 1}]

Nice! We have created a new User entity which has a university as an attribute.

# Preloading a database from a **data-h2.sql**-File
What we want to do now is write an SQL-Script that inserts initial values for us inside a database entity. First, we need to prepare our UniversityEntity class such that we can access the attributes via SQL statements:

```java
package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UNIVERSITY")
public class UniversityEntity {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "NUMBEROFSTUDENTS")
    private int numberOfStudents;

    @JsonIgnore
    @Column(name = "SEMESTERCOSTS")
    private int semesterCosts;

    public UniversityEntity(String name, int numberOfStudents, int semesterCosts){
        this.name = name;
        this.numberOfStudents = numberOfStudents;
        this.semesterCosts = semesterCosts;
    }

    public UniversityEntity(){}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getNumberOfStudents() { return numberOfStudents; }
    public void setNumberOfStudents(int numberOfStudents) { this.numberOfStudents = numberOfStudents; }
    public int getSemesterCosts() { return semesterCosts; }
    public void setSemesterCosts(int semesterCosts ) { this.semesterCosts = semesterCosts; }
}
```

Note that all we did is name our table *UNIVERSITY* and all our columns.

Next, we write a script called **data.sql** inside the *src - main - java - resources* folder with a simple SQL Query that inserts a few values inside our university database.

```sql
INSERT INTO UNIVERSITY (NAME, NUMBEROFSTUDENTS, SEMESTERCOSTS) VALUES
  ('UZH', 20000, 720),
  ('ETHZ', 18000, 980),
  ('ZHAW', 9000, 530),
  ('EPFL', 12000, 600),
  ('UNILU', 120, 440);
```

After starting our application, we can directly post new Users that belong to one of the universities we have preconfigured in our database via the **data.sql** file.

License
----

MIT License

Copyright (c) 2018 Joel Barmettler

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.