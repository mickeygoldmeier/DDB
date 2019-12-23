
# DDB - Warehouse app
**Developed by Michael Goldmeier and Natan Manor**

## Introduction
This app allow to the warehouse to control and upload new parcels to the app database for future use by the private user.
The app supports English and Hebrew, depending on the phone language.
To view the general UML diagram of the classes in the system, [click here](Android%20Project%20UML.pdf).
The app has the following permissions:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
App data is stored on Google&apos;s Firebase platform:
```json
dependencies {
	...
	implementation 'com.google.firebase:firebase-storage:16.0.4'
	implementation 'com.google.firebase:firebase-auth:16.0.5'
	...
}
```
<br>

## Description of screens and actions

### Log in screen
The login screen allow the warehouse worker to login to the system using its ID number and the secret password.
![Log in screen 1](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/Log%20in%201.jpg?raw=true "Log in 1")
<br>After typing the ID the app checking whether the ID belong to an existing user. If so, the app will suggest the user to enter their password. If not she will tell him he does not exist.
![Log in screen 2](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/Log%20in%202.jpg?raw=true "Log in screen 2")
<br>

### Sign up screen
In this screen new warehous can sign up for an app.
To make sure that the user is indeed warehouse the app does not open an account immediately, it will send their details by email to a human representative to handle the request.
![Sign up screen](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/Sign%20up.jpg?raw=true "Sign up screen")
<br>

### Parcels Management Screen
On this screen, the employee can view all parcels listed in the warehouse where he works.
![Management screen](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/Mangmnet%201.jpg?raw=true "Management screen")
<br>By clicking on a package name, the employee can do the following:
- View all data stored on the same package
- Delete the package from the database
- Print the QR code and paste it on the package itself
- Share the QR code by the apps that support photo sharing found on his cell phone
![Parcel info](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/Parcel%20info.jpg?raw=true "Parcel info")
<br>

### Parcel registration screen
This screen is divided into 3 different fragments.
In the first fragment, the employee must enter the customers phone number for which the package is addressed.
It does not matter if the employee enters the number with a local prefix (05...) or with an international prefix (+97205...) because the app will convert the number to an international prefix anyway, but the phone number must be a correct number.
![Registration fragment 1](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/Registration%201.jpg?raw=true "Registration fragment 1")
<br>In the second fragment, the warehouse worker must enter the following details on the parcel:
- Enum type variable of one of the following options
```java
public enum Parcel_Type {
    Envelope, SmallPackage, LargePackage;
}
```
- Option to choose whether or not the package contains fragile content
- Enter the weight of the package
![Registration fragment 2](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/Registration%202.jpg?raw=true "Registration fragment 2")
<br>In the third slice, the app alone finds the location of the cell phone through the built-in Android GPS service. If a GPS is turned off or the user is not interested in his current location, a location can be entered manually but the city names are checked (and automatically completed) so that a city that does not exist cannot be entered. The list of cities is regularly updated according to the Ministry of the Interior lists.
![Registration fragment 3](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/Registration%203.jpg?raw=true "Registration fragment 3")
<br>If GPS services are off, the following message will be displayed:
![No GPS connection](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/No%20GPS%20connection.jpg?raw=true "No GPS connection")
<br>At the end of the process the employee is required to press the done button, and if all the details are correct and complete the parcel will be uploaded to the server and a screen will be displayed to the employee telling him that the upload was done successfully.
![Registration 4](https://github.com/mickeygoldmeier/DDB/blob/master/screenshots/Registration%204.jpg?raw=true "Registration 4")
