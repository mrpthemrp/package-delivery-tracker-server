# Package Delivery Tracker Web Server :desktop_computer:

This repository contains a :leaves::hiking_boot: Spring Boot web server.  
It needs to be run with its counterpart, a :coffee: [Java Swing program](https://github.com/mrpthemrp/A-213).

:warning: ***Please DO NOT FORK this repository/project!*** :warning:  
:red_square: ***Please DO NOT COPY ANY CODE from this project!*** :red_square:

This project is not intended to be open-source, feel free to use it as a reference but DO NOT FORK OR COPY!  
If used as reference, please cite by providing link to project and author name \([see section below](#4-citation-format)\).

:hotsprings: **[JavaDocs Link](https://mrpthemrp.github.io/A-213-WebServer/)**
  
Watch a quick demo of the project through the link below!  
:vhs: **[Video Demo Link](??)**

## :bookmark_tabs: Table of Contents
1. [Project Description](#1-project-description)
2. [Installation Guide](#2-installation-guide)
   1. [Software Requirements](#computer-software-requirements)
   2. [Steps](#memo-steps)
3. [References](#3-references)
4. [Citation Format](#4-citation-format)

## 1. Project Description

This project is the final assignment for CMPT 213 ([Dr. Victor Cheung](http://www.victorcheung.net/)) at SFU.

This repository contains a Spring Boot application that acts as a web server to hold and manipulate data (in [JSON](https://en.wikipedia.org/wiki/JSON) form) from the client app. Data is stored in a .json file in the [gson folder](/src/main/java/cmpt213/assignment4/packagedeliveries/webappserver/gson), and is created on first run.

For a full project description, see the **[web app repository](https://github.com/mrpthemrp/A-213)**.

**See [video demo](#package-delivery-tracker-web-server-desktop_computer) for more comprehensive walkthrough.**

## 2. Installation Guide
***This project was created through IntelliJ.***

### :computer: Software Requirements
- **Windows OS** or **MacOS**
  - ***This project HAS BEEN tested on MacOS and does work.***
  - Windows 7 and up is recommended
  - MacOS 10.12 and up is recommended.
- **Latest version of [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows)**
  - Either Ultimate or Community version works.

### :memo: Steps
***The following steps are intended for use with the IntelliJ IDE***

<details><summary><h4>Part I - Download Code and Open on IntelliJ</h4></summary><br>

1. Download this project as a **ZIP file**.
    - Click **'Code'** and then **'Download ZIP'**.
2. Unzip the main folder - **'A-213-WebServer-master'**.
3. Open **IntelliJ** and select **'Open'** or 'Open Project'.
4. **Locate** where the **unzipped folder** from step 2 is on your machine. Click on the folder to open the project.
    - Click 'Trust Project' when the pop-up appears.
    - The project will now open.
</details>
<details><summary><h4>Part II - Configure Libraries and Run Program</h4></summary><br>

5. Configure the SDK by **File > Project Structure > Project Settings > Project**
    - *We will set the JDK, Language Level, and Compiler output here.*
6. Select ***JDK 18***
    - It is HIGHLY important that JDK 18 and up is used!
7. Select **'Language Level' to be 18**
    - Or whichever JDK number you are using.
8. Make sure **'Compiler output:'** is set to the out folder
    - It should already be set but if not make sure the path is **../A-213-WebServer-master/out**
9. Go to **Libraries** which is **also under Project Settings**
10. **Click on the "+" button** to add a library
11. **Click "Maven"** from the library options
12. **Type 'com.google.gson'** and click on the search button
13. **Select the newest gson library** and click ok
    - Version 2.9.1 was used to write this project
14. **Click 'OK'** when pop-up comes up on adding 'webserverapp' to module
    - If asked to replace old libary, click CANCEL and the old libary will be added to path.
15. Click **'Apply'** and then **'OK'**
16. **Click Run** and the server will run properly. Make sure to run the client side of the program too!
</details>

## 3. References

All references are cited within the program's [API](https://mrpthemrp.github.io/A-213-WebServer/).

However, there are a few external libraries used that should be noted:
- [Google GSON Libray](https://github.com/google/gson)
    - [Runtime Type Adapter Factory](https://github.com/google/gson/blob/master/extras/src/main/java/com/google/gson/typeadapters/RuntimeTypeAdapterFactory.java)

## 4. Citation Format
Example of citing this project as a reference:
> Reference used for writing a SpringBoot GetMapping: https://github.com/mrpthemrp/A-213-WebServer/blob/master/src/main/java/cmpt213/assignment4/packagedeliveries/webappserver/controllers/Controller.java  
> Date Accessed: August 2022  
> Developer: [Deborah Wang](https://github.com/mrpthemrp)

If using this project as a reference please copy and paste the following into your references/citations:
```diff
Reference for <code referenced>: <file/folder URL>
Date Accessed: <date accessed>
Developer: Deborah Wang (https://github.com/mrpthemrp)
```

---
Last Code Update Date: August 2022

Copyright August 2022, Deborah Wang
