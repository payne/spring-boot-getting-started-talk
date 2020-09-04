---
marp: true
theme: invert
paginate: true
<!-- *template: invert -->
---

# Getting started building web applications using Java & Spring Boot
## by Matt Payne, Twitter.com/MattPayneOrg
## 24 September 2020.  
   * Last updated: Fri Aug 28 11:33:36 CDT 2020
   * https://github.com/payne/spring-boot-getting-started-talk
   * Pull Requests are welcome!
   * Slide tool: https://marp.app/

----
# Many Thanks, Aaron!
1. Aaron is a great friend who encouraged me to speak at HDC.

TODO(MGP): Get Aaron's photo

![Aaron Grothe rocks!](images/ChadHoman.jpeg)


-----
# Getting started building web applications using Java & Spring Boot

## Abstract: 

Less than 140 characters:
* "Get started with Spring Boot by building an application for sending scheduled tweets. Get started learning about the Spring Boot ecosystem."

-----
# A bit more detail:
We'll walk through the steps to get started with Spring Boot by building a 
simple application for sending scheduled tweets.  You'll leave the session understanding how to develop & deploy a Spring Boot application and how to start learning more about the Spring Boot ecosystem.

Plan:
1) Spring boot is an easy button for java web applications.   It's spring framework (1 October 2002) with many things made easier.
2) Build a hello world application and deploy it to Heroku.  Heroku is a platform as a service (PaaS) that has a free tier which does not
require a credit card.


-----
# start.spring.io 
1. JPA
2. CommandLineRunner
3. http://localhost:8080/h2-console
4. Demo time
5. Add `spring.datasource.url=jdbc:h2:mem:test_db` to `application.properties`

-----
# Forms & Thymleaf
1. Sequence diagram for HTTP GET and HTTP POST
2. `@Controller`
3. `resources/templates`
4. `resources/static`
-----
# Let's TWEET!
1. Mr.Potta on YouTube and others

-----
# Quartz Schedules

https://github.com/payne/nps

------
# Spring Profiles
1. Development
2. Production
3. Profiles add to the base

-----
# Heroku.com
1. No credit card required PaaS


