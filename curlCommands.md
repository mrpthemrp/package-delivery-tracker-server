# cURL Commands

Welcome to the cURL command guide for this project!

The commands listed in this document were **tested from a Git Bash console**.  
The User is responsible for knowing their OS console's specifications when it comes to quotes and dashes.

***Don't forget to run server, or else the commands will not work.***

The first 2 sections explains which curl commands to use for which option.  
Note that the commands are written out in one (very) long line.  
Feel free to break it up in your console according to your console's rules.  

There is a section at the very bottom that gives the Windows version of the add package command.  
Windows OS uses the slash to escape quotes (""), so it can be a little tedious to type everything out.

---

### SECTION 1: GET COMMANDS

#### 1. **GET/ping**  
> curl -i -H "Content-Type: application/json" -X GET localhost:8080/ping  

**Return:** "System is up!"

**Notes:** This is the first command to run, it will connect to the server.

#### 2. **GET/listAll**  
> curl -i -H "Content-Type: application/json" -X GET localhost:8080/listAll

**Return:** A JSON array of all data that is stored.

**Notes:** Will return "[]" if the array is empty.

#### 3. **GET/listOverduePackage**
> curl -i -H "Content-Type: application/json" -X GET localhost:8080/listOverduePackage

**Return:** A JSON array of all overdue packages.

**Notes:** Will return "[]" if the there are no overdue packages.

#### 4. **GET/listUpcomingPackage**
> curl -i -H "Content-Type: application/json" -X GET localhost:8080/listUpcomingPackage

**Return:** A JSON array of all upcoming packages.

**Notes:** Will return "[]" if the there are no upcoming packages.

#### 5. **GET/exit**
> curl -i -H "Content-Type: application/json" -X GET localhost:8080/exit

**Return:** "Client data saved."

**Notes:** Command is called only when client wants to exit.

---

### SECTION 2: POST COMMANDS

#### 6. POST/addPackage
**Book:**
> curl -i -H "Content-Type: application/json" -d '{"type":"Book","authorName":"AUTHOR_NAME","name":"BOOK_NAME","notes":"NOTES","price":"0.0","weight":"0.0","expectedDeliveryDate":"2022-08-08T00:00","isDelivered":"false"}' -X POST localhost:8080/addPackage

**Perishable:**
> curl -i -H "Content-Type: application/json" -d '{"type":"Perishable","expiryDate":"2023-08-08T00:00","name":"PERISHABLE_NAME","notes":"NOTES","price":"0.0","weight":"0.0","expectedDeliveryDate":"2022-08-08T00:00","isDelivered":"false"}' -X POST localhost:8080/addPackage

**Electronic:**
> curl -i -H "Content-Type: application/json" -d '{"type":"Electronic","handleFee":"0.0","name":"ELECTRONIC_NAME","notes":"NOTES","price":"0.0","weight":"0.0","expectedDeliveryDate":"2022-08-08T00:00","isDelivered":"false"}' -X POST localhost:8080/addPackage

**Return:** A JSON array of all updated packages.

**Notes:**
- Replace capitalized words and numbers with necessary values.
- Date format is \<YYYY\>-\<MM\>-\<DD\>T\<HH\>-\<MM\>
  - the "T" indicates time, do not remove.

#### 7. **POST/removePackage**
> curl -i -H "Content-Type: application/json" -d "["0","LIST_ALL"]" -X POST localhost:8080/removePackage

**Return:** A JSON array of all updated packages.

**Notes:**
- "LIST_ALL" is used because we are not interacting with a GUI (which uses HttpUrlConnection).
-  0 is the index of the package you want to remove, replace with any other valid array index.
-  We are passing a string array in Json Format hence the format "["0","LIST_ALL"]".

#### 8. **POST/markPackageAsDelivered**
> curl -i -H "Content-Type: application/json" -d "["0","true","LIST_ALL"]" -X POST localhost:8080/markPackageAsDelivered

**Return:** A JSON array of all packages.

**Notes:**
- "LIST_ALL" is used because we are not interacting with a GUI (which uses HttpUrlConnection).
- "true" is the new Delivery status, it may also be toggled to false.
-  0 is the index of the package you want to remove, replace with any other valid array index.
-  We are passing a string array in Json Format hence the format "["0","true","LIST_ALL"]".

---

### WINDOWS VERSION OF ADD PACKAGE COMMANDS

*Refer to previous section for explanations.*

#### 6. **POST/addPackage**
**Book:**
> curl -i -H "Content-Type: application/json" -d "{\"type\":\"Book\",\"authorName\":\"AUTHOR_NAME\",\"name\":\"BOOK_NAME\",\"notes\":\"NOTES\",\"price\":\"0.0\",\"weight\":\"0.0\",\"expectedDeliveryDate\":\"2022-08-08T00:00\",\"isDelivered\":\"false\"}" -X POST localhost:8080/addPackage

**Perishable:**
> curl -i -H "Content-Type: application/json" -d "{\"type\":\"Perishable\",\"expiryDate\":\"2023-08-08T00:00\",\"name\":\"PERISHABLE_NAME\",\"notes\":\"NOTES\",\"price\":\"0.0\",\"weight\":\"0.0\",\"expectedDeliveryDate\":\"2022-08-08T00:00\",\"isDelivered\":\"false\"}" -X POST localhost:8080/addPackage

**Electronic:**
> curl -i -H "Content-Type: application/json" -d "{\"type\":\"Electronic\",\"handleFee\":\"0.0\",\"name\":\"ELECTRONIC_NAME\",\"notes\":\"NOTES\",\"price\":\"0.0\",\"weight\":\"0.0\",\"expectedDeliveryDate\":\"2022-08-08T00:00\",\"isDelivered\":\"false\"}" -X POST localhost:8080/addPackage

---

Copyright 2022, Deborah Wang