### GatherGrid


> web Jakarta EE based application developped to  enable users to create, promote, manage, and participate in events. This platform provides event organizers with a user-friendly way to plan and promote events, while participants can search for, register for, and interact with various types of events 


 

### Technologies



#### 1. Wildfly as provider (Application Server)
#### 2. MYSQL database
#### 3. Maven Build Tool
#### 4. Servlets
#### 5. JPA / Hibernate
#### 6. JTA (Java Transaction API)
#### 7. Wild SE as implementation of CDI (Context and dependency injection)
#### 8. WildFly Elytron Security Implementation 


### UML DIAGRAM

![class diagram](./UML/CLASS_DIAGRAM.png)



### STRUCTURE (INITIAL)
```
├───src
│   ├───main
│   │   ├───java
│   │   │   └───ma
│   │   │       └───youcode
│   │   │           └───gathergrid
│   │   │               ├───domain
│   │   │               ├───repositories
│   │   │               ├───resources
│   │   │               ├───service
│   │   │               └───utils
│   │   ├───resources
│   │   │   └───META-INF
│   │   └───webapp
│   │       └───WEB-INF
│   └───test
│       ├───java
│       └───resources
├───target
└───UML
```
### FIGMA LINK
https://www.figma.com/file/31CTIVv1zUEwLSyfaWTBSn/Untitled?type=design&node-id=0%3A1&mode=design&t=A8tvm7RnPRH1Uw1s-1

# TESTS

# Event Validation Method After Correction

**Method:** `public List<Error> validate()`

## Test Descriptions

### Test 1: Validation of All Required Fields
- **Objective:** Verify that the method returns all errors when all required fields are empty or missing.
- **Method:** Create an Event object without setting the required fields.
- **Assertion:** The method should return 6 errors, one for each missing field.

### Test 2: Successful Validation
- **Objective:** Verify that the method returns an empty list when a valid Event object is provided.
- **Method:** Create a valid Event object with all the required information.
- **Assertion:** The method should return an empty list.

### Test 3: Event Date in the Past
- **Objective:** Verify that the method correctly detects a past event date as an error.
- **Method:** Create an Event object with an event date in the past.
- **Assertion:** The method should return an error "Event date should not be old."

## Conclusion

**Test 1** aims to ensure that the validation method correctly detects all missing required fields and returns appropriate errors.

**Test 2** confirms that the method works correctly by returning an empty list when all required fields are provided correctly.

**Test 3** checks if the method correctly identifies a past event date and returns the appropriate error.

These tests ensure that the event validation method functions correctly and identifies potential errors while accepting valid data.
