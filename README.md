<h1>Course Management Software</h1>
<h2>Software Engineering Course Final Project</h2>


<h3>Summary of the Course</h3>
<p>In CSCI 4711, we spent the semester learning about the entire process of software development. We learned about gathering functional/non-functional requirements and use cases from clients, creating interface mockups,
and designing objects, classes, and other system components.
  
The course somewhat simulated communicating with clients throughout the process (our professor acted as the client). Check out the RAD to see all the diagrams 
and models my team created. Unfortunately, the semester reached its end before any teams actually had time to build their software.</p>

<p><b>HOWEVER</b>, I wasn't going to be satisfied calling it quits there, so I went ahead and built my team's software over two weeks at the end of the semester. Our professor was quite surprised, but pleased, and asked
about my experience.</p>

<h3>What I Experienced Building the Software</h3>
<p>The sequence diagrams and object models were amazingly helpful when constructing the backbone of the software. This is the main reason I was so adamant about actually building the software after spending the semester
planning it out!

The hard part came after the backbone was done. I tried to follow the diagrams and models exactly, but I ended up feeling like we needed some extra methods/classes to make the software work. More exactly, I decided to add
extra classes to handle entity objects and extra methods to handle communication between control, entity, and boundary objects. I wanted to adhere to our plan as much as possible, so I spent more time than necessary trying
to get the separate components to work together.

Reflecting on this experience, I realized that I should be more flexible rather than attempt to follow the RAD exactly. I also learned the importance of separation of duties (i.e. having only the controllers talk to the database
rather than also letting the entity objects talk to the database).</p>

<h3>Summary of Software</h3>
<p>Our course management software (non-web-based; implemented in Java) incldues uses for teachers and students.

- First, and most importantly, user credentials are stored securely as SHA256 hashes.

- Teachers log in, select a course, and enter/edit grades for students.

- Students log in, select a course, select a section, and register for that section.

- When users makes changes to grades/registration, the SQLite database is updated, and changes are reflected in the interface.

- Logging out returns the user to the login screen.</p>
