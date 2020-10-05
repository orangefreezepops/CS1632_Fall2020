# CS 1632 - Software Quality Assurance
Fall Semester 2020

* DUE: Oct 5, 2020 9:00 AM 

WARNING: Keep your GitHub repository _PRIVATE_.  Otherwise, you may be held responsible for abetting plagiarism.

## Deliverable 2

For this assignment, your group will write code and unit tests for an
authorized reproduction of Coffee Maker Quest.  

Requirements for this program are in the requirements.txt file in this
directory.  In case of ambiguity, please see the original program
coffeemaker.jar as an example of what to display and how the system should
work.

Some of the work has already been done for you.  Classes such as
CoffeeMakerQuest.java, Config.java, Game.java, Player.java, Room.java, and
TestRunner.java are already complete.  You need only modify
CoffeeMakerQuestImpl.java and CoffeeMakerQuestTest.java.  As in the
exercise, the places where you need to modify code are marked by the // TODO
comments.  DO NOT TOUCH the already complete classes as they will be used AS
IS during grading.  Here is a brief rundown of the classes:

* CoffeeMakerQuest.java - the interface for the CoffeeMakerQuest game engine
* Config.java - allows configuration of bug injection into various classes
* Game.java - contains the main method; generates rooms and runs the game using the CoffeeMakerQuest engine
* Player.java - player object with inventory information
* Room.java - room object with furnishings and items
* TestRunner.java - the runner for the JUnit test class CoffeeMakerQuestTest
* CoffeeMakerQuestImpl.java - an implementation of CoffeeMakerQuest (_modify_)
* CoffeeMakerQuestTest.java - JUnit test class CoffeeMakerQuest (_modify_)


1. To run the game you need to invoke the Game class.  For Windows:
    ```
    runGame.bat
    ```
    For Mac or Linux, try doing:
    ```
    bash runGame.sh
    ```
    When you run it without any modification, you will suffer an exception and crash.  That is of course because you have not completed implementing CoffeeMakerQuestImpl.java!

1. To run the JUnit tests on CoffeeMakerQuestImpl, for Windows:
    ```
    runTest.bat
    ```
    For Mac or Linux, try doing:
    ```
    bash runTest.sh
    ```
    When you run it without any modification, you will get "ALL TESTS PASSED".  But don't get delirious.  That is because all your tests are currently empty.

1. To run the JUnit tests on CoffeeMakerQuestBuggy (included in the form of
   the coffeemaker-buggy.jar file), for Windows:
    ```
    runTestBuggy.bat
    ```
    For Mac or Linux, try doing:
    ```
    bash runTestBuggy.sh
    ```

## Development Methodology

Like Exercise 2, we will try to apply the Test Driven Development (TDD) model
here.  Try writing the test case(s) FIRST before writing the code for a
feature.  This way, you will always have 100% test coverage for the code you
have written and are writing.  Hence, if you break any part of it in the course
of adding a feature or refactoring your code, you will know immediately.

## Expected Outcome

You should see the following output when running runTest.bat (or runTest.sh):
```
ALL TESTS PASSED
```

And after running runTestBuggy.bat (or runTestBuggy.sh), you should get output that looks like [runTestBuggy.output.txt](runTestBuggy.output.txt).  If you do, this tells you that you have written your JUnit tests well so that they are able to find the bugs in CoffeeMakerQuestBuggy.  Note that I've commented out the following line at TestRunner.java:30 to make the output less verbose:
```
System.out.println(f.getTrace());
```
The above will print a full Java stack trace for every failure.  It is useful when a test fails due to a crash in your program and you want to locate exactly in which source code line the Java exception was thrown.  The defects in this CoffeeMakerQuestBuggy does not involve crashes due to exceptions so I've temporarily commented it out for brevity.

## Additional Requirements

* Code coverage of the class CoffeeMakerQuestImpl when the JUnit TestRunner is
  run should be at an absolute minimum of 80%.  If coverage falls below that
number, add more unit tests in CoffeeMakerQuestTest.

* Write at least one private method while implementing CoffeeMakerQuestImpl.
  Add at least one unit test that tests that private method at the very bottom
of CoffeeMakerQuestTest.

* Coding style is also important for software quality in the long run (even
  though they are not technically defects as we learned).  In particular, a
uniform naming convention greatly improves the readability of your code.  A
widely used convention is called
[lowerCamelCase](https://en.wikipedia.org/wiki/Naming_convention_(programming)#Java)
convention.  That is the convention that was [first adopted when Sun
Microsystems first created the Java
language](https://www.oracle.com/technetwork/java/codeconventions-135099.html).
This is still the convention at the biggest companies using Java like
[Oracle](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/variables.html)
and [Google](https://google.github.io/styleguide/javaguide.html#s5-naming).
Please make sure you follow the lower camel case convention for all your
variables and methods for this project.  There is less agreement on other
formatting issues like indentation and line wrapping, but try to maintain a
uniform convention whatever you choose.

## Grading

* GradeScope autograder: 70% of grade
* Private method added and tested: 5% of grade
* Source code style (lower camel case naming / formatting): 10% of grade
* Code coverage: 15% of grade

Please review the grading_rubric.txt for details.

## Submission

Each pairwise group will submit the exercise *once* to GradeScope, by *one member* of the group.  The submitting member will press the "View or edit group" link at the top-right corner of the assignment page after submission to add his/her partner.  That way, both of you will get a grade.  I recommend that you divide the list of methods to implement / test into two halves and working on one half each.

You will do two submissions for this deliverable.

1. You will create a github repository just for deliverable 2.  Add your partner as a collaborator so both of you have access.  Make sure you keep the repository *PRIVATE* so that nobody else can access your repository.  This applies to all future submissions for this course.  Once you are done modifying code, don't forget to commit and push your changes to the github repository.  When you are done, submit your github repository to GradeScope at the "Deliverable 2 GitHub" link.  Once you submit, GradeScope will run the autograder to grade you and give feedback.  If you get deductions, fix your code based on the feedback and resubmit.  Repeat until you don't get deductions.

1. Create a screenshot of code coverage stats given by your IDE of choice and name it code_coverage.png. Example:

    https://github.com/wonsunahn/CS1632_Spring2020/blob/master/deliverables/2/code_coverage.png

    I used Eclipse to generate the screenshot.  Here is the user guide: https://www.eclemma.org/userdoc/launching.html.  It is just a click of a button and requires no extra installation.  You don't have to have 100% coverage for this exercise but you will have coverage requirements for your deliverable.  I have already created an Eclipse project for you in the exercise directory so you can just open that to run TestRunner using File > Open Projects from File System from the menu.  If you can't open the project for some reason, you need to create a new project using File > New > Java Project.  For those of you who are new to eclipse, you need to include the four JAR files under CommandLineJUnit/ as external JARs for it to compile.  You need to go to project properties > Java Build Path > Libraries and Add JARs or Add External JARs.  Also, don't create module-info.java when prompted.
    
    When you run the code coverage tool, make sure you run TestRunner, not Game.  You can do that by clicking on and highlighting TestRunner.java before clicking on the code coverage button.  Alternatively, you can right click on TestRunner.java and click on the "Coverage as" item in the menu that pops up.  This is important.  If you run Game.java, you will be getting the code coverage while playing the game.

    After you have created the screenshot, save the picture to a PDF file and submit to GradeScope at the "Deliverable 2 Coverage" link.  Make sure the picture fits in one page for easy viewing and grading.

## GradeScope Feedback

It is encouraged that you submit to GradeScope early and often.  Please use the feedback you get on each submission to improve your code!

The GradeScope autograder works in 3 phases:

1. CoffeeMakerQuestImpl verification using CoffeeMakerQuestTestSolution:
   CoffeeMakerQuestTestSolution is the solution implementation of
CoffeeMakerQuestTest.  The purpose of this phase is to verify that CoffeeMakerQuestImpl (your CoffeeMakerQuest implementation) does not have any defects.

1. CoffeeMakerQuestTest on CoffeeMakerQuestSolution: CoffeeMakerQuestTest is your submitted JUnit test for CoffeeMakerQuest.  The purpose of this phase is
   to test CoffeeMakerQuestTest itself for defects.  CoffeeMakerQuestSolution is the solution implementation of CoffeeMakerQuest and contains no defects (that I know of).  Hence, all tests in CoffeeMakerQuestTest should pass.

1. CoffeeMakerQuestTest on CoffeeMakerQuestBuggy: CoffeeMakerQuestTest is your submitted JUnit test for CoffeeMakerQuest.  The purpose of this phase is
   to test CoffeeMakerQuestTest against the buggy CoffeeMakerQuestBuggy
implementation.  The class CoffeeMakerQuestBuggy is given to you in the form of
the coffeemaker-buggy.jar file.  Since CoffeeMakerQuestBuggy is buggy, you
expect the tests to fail this time.  If CoffeeMakerQuestTestSolution fails a
test but CoffeeMakerQuestTest passes a test (or vice versa), then this indicates a problem.

## Resources

These links are the same ones posted at the end of the slides:

* JUnit User manual:  
https://junit.org/junit5/docs/current/user-guide/  
The Writing Tests section is probably the most useful.

* JUnit Reference Javadoc:  
http://junit.sourceforge.net/javadoc/  
For looking up methods only, not a user guide.

* Mockito User Manual:  
https://javadoc.io/static/org.mockito/mockito-core/3.2.4/org/mockito/Mockito.html  
Most useful is the sections about verification and stubbing.

* Eclipse IDE
If you want more information, here is a page put up by a U Chicago professor:  
http://people.cs.uchicago.edu/~kaharris/10200/tutorials/eclipse/index.html  
It uses a much earlier version of Eclipse, but other than the outdated UI, the operations are the same.  I looked at several resources and this one was the most concise and to the point.  A more comprehensive manual is at eclipse.org:  
https://help.eclipse.org/2019-12/index.jsp  
Look at the "Java development user guide" chapter on the left.

