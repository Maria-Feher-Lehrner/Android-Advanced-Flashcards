Android Advanced
Exercise

Android Advanced
APP-WEB-DEVELOPMENT



Exercise Overview

• You may not work in teams

• The topic of the app can be chosen by you

• For example: Contacts, Pets, Bitcoin, Quizzes, etc.

• The ANDA source code and documents can be used for help

• You must use Kotlin as the programming language

• The app from the lecture CANNOT be used for the exercise

• A generic requirement exists for each block

• The requirement will be implemented for the lecture app (Contacts)

• The specific requirement for the lecture app is explained too

• It can be used as a template for the implementation if you want to build your own app

Android Advanced
APP-WEB-DEVELOPMENT



Grading

• The exercise is split into blocks

• You need to implement at least the „Base“ block

• With the „Base“ block you get the grade „4“

• Each additional block improves your grade by one

• 1 additional block => grade „3“

• 2 additional blocks => grade „2“

• At least 3 additional blocks => grade „1“

• Upload the source code as archive on Moodle

• Deadline see Moodle

• Add a README which contains the blocks you have implemented 

• The 2. appointment (2. Antritt) for the exercise is set in Moodle

• The 3. appointment (3. Antritt, kommissionell) will be communicated individually

Android Advanced
APP-WEB-DEVELOPMENT



Base: Project Setup and ViewModels

• Create an Android Studio project

• Target at least SDK version 21 (Android 5)

• Use Kotlin as the programming language

• Use the „Empty Activity“ template and set the Activity as the launcher Activity

• Add at least 2 Fragments and provide navigation actions via the navigation component

• Add a ViewModel for each fragment and create it via the viewModels call

• Pass an argument from one fragment to another and read it in the target viewmodel

Android Advanced
APP-WEB-DEVELOPMENT



Contacts: Project Setup and ViewModels

• Use the starter project from Moodle

• The navigation and safe args plugins are already installed

• For the ListFragment create a ListViewModel and use the ViewModel as base class

• Create and store the ListViewModel in the fragment with the by viewModels() call

• Add a readAll () function which returns a List<Contact> to the view model for later

• For the DetailFragment create a DetailViewModel and use the ViewModel as base class

• Add the SavedStateHandle as constructor parameter

• Create and store the DetailViewModel in the fragment with the by viewModels call

• Add a read() function which returns a Contact

• Return the contact from the navigation in read() with savedStateHandle.get<Contact>(“contact”)

Android Advanced
APP-WEB-DEVELOPMENT



Base: Repository and Navigation
• Create a repository class and provide some data through it

• This can be a list or a single object

• Access the repository in the viewmodels and use it for reading and writing data

• Call the viewmodel's read function in onResume to refresh the Fragments data

• Provide at least a "readAll" function on the repository for getting the data

• Call the readAll function only in viewmodels

• Read the argument from the navigation component in a viewmodel during navigation

• This can be done with the SafeArgs component

Android Advanced
APP-WEB-DEVELOPMENT



Contacts: Repository and Navigation

• Add a ContactRepository class to the project

• Create a global variable for the repository so every class can access it

• Store the List<Contact> there and add a readAll function which returns the list

• Name the variable for the list “contacts”

• The list can be created with the createContacts function

• Call the readAll function from the global repository in the ListViewModel

• In the ListFragment in onResume call the readAll function from the ListViewModel

• Refresh the list adapter with the contacts from the viewmodel with updateContacts

• To access the adapter from other functions , it needs to be moved outside the setupList function

• Store the adapter as lateinit var in the ListFragment and create it in onViewCreated

Android Advanced
APP-WEB-DEVELOPMENT



Block 1: LiveData and Lifecycle Components

• Convert the readAll function of the repository to return a LiveData object

• MutableLiveData should be used internally as type

• The LiveData should contain the data of the repository

• Every time the data changes, the LiveData should be updated with setValue

• Pass through the LiveData from the repository to the ViewModel to the Fragment

• Note: Google Room can return LiveData objects too

• Bind the fragment to the repository data via the viewmodel and render it

• Use the observe call in the fragments

• Use the viewLifecycleOwner property of the fragment for binding

• Pass an observer function to observe

• Every time the observer function is called, do something like updating the UI

• Do not get the data anymore in the onResume function by calling the viewmodel

Android Advanced
APP-WEB-DEVELOPMENT



Block1 (Contacts) : LiveData and Lifecycle 
Components
• Convert the variable "contacts" in the repository to MutableLiveData <List<Contact>>

• Still use createContacts() in the MutableLiveData constructor to initially create data

• Pass through the LiveData object in the viewmodel to the fragment

• Observe the changes of the LiveData object in onViewCreated

• To do so, call viewModel.readAll().observe(viewLifecycleOwner) {}

• In the observer function refresh the adapter with updateContacts(ArrayList(it))

• Remove the call to readAll in onResume in the fragment

• To test the observing, add an addContact() function to the viewmodel and repository

• Call the viewmodel addContact function in the refresh listener in the ListFragment

• The viewmodel then calls the repository

• The repository then reads the current list with contacts.value, adds a Contact to the list and then 
sets the whole list with contacts.setValue()

Android Basics
APP-WEB-DEVELOPMENT



Block 2: SQL with Google Room

• Setup an SQL database with Google Room

• Add at least 1 entity with a primary key and some other properties

• Create at least 1 DAO class for the SQL queries

• Use a SELECT statement for reading data

• Provide a database class, the entity class for the annotations and getters for the DAOs

• Create a database object with the Google Room builder in the MainActivity

• Main thread queries are allowed for the exercise

• Add the database object to the repository via constructor

• Only call the DAO functions from within the repository

• Use at least a reading function from the database (like Select * From X) for the readAll function 

in the repository

Android Advanced
APP-WEB-DEVELOPMENT



Block 2 (Contacts): SQL with Google Room

• Add the dependencies for Google Room to the gradle file and apply the "kapt“ or “ksp” 

plugin

• Add an "id" var to the Contact class with type Int

• Add the @Entity annotation to the class and the @PrimaryKey(autoGenerate = true) to the 

id property

• Create a ContactDAO interface and annotate it with @Dao

• Add a saveAll function which takes List<Contact> as parameter and annotate it with @Insert

• Add a function readAll() which returns List<Contact>

• Annotate it with @Query("Select * from Contact ORDER BY name")

• Add an ANDADatabase abstract class and annotate it with @Database(entities = 

arrayOf(Contact::class), version = 1)

• Add an abstract function getContactDao() which returns ContactDAO

Android Advanced
APP-WEB-DEVELOPMENT



Block 2 (Contacts): SQL with Google Room cont.

• In the MainActivity onCreate function create the database

• Call Room.databaseBuilder() with "this", ANDADatabase::class.java, and "ANDA“ as 

parameters

• Allow main thread queries with allowMainThreadQueries()

• Call build() and hold the return value in a val "database"

• Create the global ContactRepository variable in the MainActivity

• Add an ANDADatabase parameter to the ContactRepository constructor

• Create the repository in onCreate with the created database variable

• Still hold the repository in a global var in the ContactRepository file

• Add initial data in the MainActivity when no data is in the database

• Call readAll(): when the list is empty call saveAll() with createContacts()

Android Advanced
APP-WEB-DEVELOPMENT



Block 3: API Access and Serialization with ktor

• Install the Ktor dependencies: core, client-android, content-negotiation and kotlinx serialization

• Add the INTERNET permission to the AndroidManifest

• Create an HttpClient with the Android engine inside a function

• Pass the HttpClient to the repository as constructor argument

• Create a class for the data you want to retrieve and annotate it as @Serializable

• Add a "load" suspend function to the repository

• Inside the function call the get() function of the HttpClient with an URL of your choice

• Retrieve the result via the body() call

• Call the load function in the viewmodel through the fragment

• Wrap the call in a lifecycleScope.launch(Dispatchers.IO){ } so the UI doesn't get blocked

• When the load function finishes, reload the UI

Android Advanced
APP-WEB-DEVELOPMENT



Block 3 (Contacts): API Access and Serialization with
ktor

• Add the Ktor core and client-android dependency to Gradle

• For parsing add the content negotation and Kotlin JSON serialization plugin to Gradle

• In the project Gradle file also add the Kotlin serialization plugin and apply it (see recordings)

• Add the INTERNET permission to the AndroidManifest with uses-permission

• Create an ApiAccess file and create the HttpClient in a function "createHttpClient"

• Use the Android engine and set expectSuccess to true

• Install the ContentNegotiation plugin with JSON and set ignoreUnknownKeys to true

• Create an ApiContact data class for the web contract with name, telephoneNumber and age as 

properties

• Add the @Serializable annotation to the class

• Pass the HttpClient to the repository as constructor argument

Android Advanced
APP-WEB-DEVELOPMENT



Block 3 (Contacts): API Access and Serialization with
ktor cont.

• Add a "load" suspend function to the repository which returns List<Contact>

• Call the get method of the HttpClient with this URL

• The get() call returns List<ApiContact>, but must be mapped to Contact

• Convert the web contacts to Contacts manually and return them from the load method

• Call the "load" function from the ListViewModel and return the result to the ListFragment

• In the ListFragment call the viewmodel function when the refresh listener is triggered and 

wrap it in lifecycleScope.launch(Dispatchers.IO){ } to not block the UI

• Use the contacts from the load method to update the adapter

• Note: If you implemented the LiveData block, you can display the contacts easily when 

using the LiveData.setValue function instead of returning the contacts

Android Advanced
APP-WEB-DEVELOPMENT



Block 4: Dependency Injection with Koin

• Install the dependency for Koin Android

• Create a DependencyInjection file for the setup of Koin

• Add a Koin module for the dependencies

• Register the repository as singleton with "singleOf" or "single"

• Register both viewmodels with "viewModel" or "viewModelOf"

• Add the repository to the viewmodel constructors instead of referencing the global variable

• Create an Application subclass and reference it in the AndroidManifest

• Set it in the application tag in XML with android:name=".MyApplicationName"

• In onCreate start Koin with androidContext() and the created appModule

• Replace the "by viewModels" calls in the fragments with "by viewModel" calls from Koin

Android Advanced
APP-WEB-DEVELOPMENT



Block 4 (Contacts): Dependency Injection with Koin

• Install the dependency for Koin Android

• Create a file DependencyInjection and declare a Koin module named "appModule"

• Inside the module register the ContactRepository with singleOf(::ContactRepository)

• Register the ListViewModel with viewModelOf(::ListViewModel)

• Register the DetailViewModel with viewModelOf(::DetailViewModel)

• Instead of using the global repository variable, add the ContactRepository as constructor parameter to the 
ListViewModel

• In the ListFragment replace the "by viewModels" call with "by viewModel"

• In the DetailFragment replace the "by viewModels" call with "by stateViewModel"

• Beware: SavedStateHandle doesn't contain the navArgs (known bug in Koin)

• Create a new class ANDAApplication and subclass it from Application

• In onCreate call startKoin() with the androidLogger() androidContext() and appModule

• Set the ANDAApplication class in the AndroidManifest in the application tag

Android Advanced
APP-WEB-DEVELOPMENT



Block 5: Dependency Injection with Koin
(Google Room)

• You need to register the Google Room Database with Koin if you implemented the 

database block

• Register the database class as a "single"

• Call the Room database builder from within the "single" function

• Remove the database builder from the MainActivity

• To retrieve the Android Context in the builder, call "get()"

• For the repository nothing needs to be changed

Android Advanced
APP-WEB-DEVELOPMENT



Block 5: Dependency Injection with Koin (Ktor)

• You need to register the Ktor HTTP Client with Koin if you implemented the API access 

block

• Move the function for creating the HttpClient in the DependencyInjection file

• Call this function now from a "single" or "singleOf" call inside the Koin module

• For the repository nothing needs to be changed since the HttpClient is already a 

constructor parameter

Android Advanced
APP-WEB-DEVELOPMENT



Block 5 (Contacts): Dependency Injection with Koin
(Google Room & Ktor)

• Instead of creating the database in the MainActivity, move it to the module

• Register the database with a "single" call

• In the builder call "get()" for the Android Context

• For Ktor: Call the HttpClient create function inside a single {} function

Android Advanced
APP-WEB-DEVELOPMENT



Block 6: Concurrency with Coroutines and
Flow

• Use a Kotlin coroutine to execute a long running operation on another thread

• Add a suspend function to the repository

• Inside this function delay the execution for 3 seconds with delay()

• Perform some logic, like creating random contacts or download something from the web

• If needed, update the data in the repository

• Pass through the suspend function from the viewmodel to the fragment

• In the fragment, call the function inside the lifecycleScope.launch method

• After completion, reload the UI

• Hint: If you are implementing the API access block, you implicitly added this block too and 

do not need to add a separate suspend function

Android Advanced
APP-WEB-DEVELOPMENT



Block 6 (Contacts): Concurrency with Coroutines

• Add a "createContacts" suspend function to the repository

• First, delay the execution for 3 seconds

• Then generate a list of 1000 contacts with random ages

• Use the Random.nextInt function to generate ages

• After creation, remove every contact which is older than 40 with the filter function and return

• In the ListFragment in the onRefreshListener, call the createContacts function through the 

ListViewModel

• Wrap the call in a lifecycleScope.launch(Dispatchers.IO){ } to not block the UI

• Use the contacts from the function to update the adapter with updateContacts

Android Advanced
APP-WEB-DEVELOPMENT



Hints

• If you need help or are stuck -> contact me on Moodle or via E-Mail

• If the problem may be interesting for others, open a discussion in the discussion forum

• The project can be exported as ZIP from Android Studio

• File -> Export -> Export to Zip File

• The Material Design library may be helpful to style views

Android Advanced
APP-WEB-DEVELOPMENT