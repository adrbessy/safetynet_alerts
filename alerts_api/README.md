# SafetyNet alerts api
An application for sending information to urgency services.
This app uses Java to run and stores the data in H2 DB.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 4.0.0

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

### Running App

Finally, you will be ready to import the code into an IDE of your choice and run the AlertsApiApplication.java to launch the application.

### API calls (URI, parameters)
GET

http://localhost:9002/firestation?stationNumber=1

http://localhost:9002/childAlert?address=1509%20Culver%20St

http://localhost:9002/phoneAlert?firestation=3

http://localhost:9002/fire?address=1509%20Culver%20St

http://localhost:9002/flood?stations=1,2

http://localhost:9002/personInfo?firstName=Jacob&lastName=Boyd

http://localhost:9002/communityEmail?city=culver


PUT

http://localhost:9002/firestation/1509%20Culver%20St

http://localhost:9002/medicalRecord/1

http://localhost:9002/person/2



POST

http://localhost:9002/person

http://localhost:9002/firestation

http://localhost:9002/medicalRecord


DELETE

http://localhost:9002/person?firstName=Jacob&lastName=Boyd

http://localhost:9002/medicalRecord?firstName=Jacob&lastName=Boyd

http://localhost:9002/firestation/1509%20Culver%20St
