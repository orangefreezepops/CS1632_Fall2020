# CS 1632 - Software Quality Assurance
Fall Semester 2020

DUE: Oct 19 (Monday), 2020 09:00 AM

## Deliverable 3

For this assignment, you and a partner will write systems-level, automated
black-box tests for a web app using the Selenium IDE. 

The web app is located here: https://cs1632.appspot.com/

I am assuming that you have done Exercise 3 by now.  If you haven't, please go
do it before working on this deliverable.  I will skip detailed instructions on
the Selenium IDE that we already went over on Exercise 3.

For those of you who are working in groups, you will be working on the same
shared .side project file. So it is especially important that your pull before
opening the project file and push immediately after you have modified and saved
the project file. Otherwise, you may get merge conflicts. Merging conflicts is
possible by using the technique I went over with the
[Using\_Git](https://github.com/wonsunahn/CS1632_Summer2020/blob/master/lectures/Using_Git.pdf)
slides, but it's best to avoid it.

## Task 1: Write test cases

Write a test case for each requirement listed in
[requirements.md](requirements.md).  Name each test case same as the
requirement name.  Again, this is important for the purposes of GradeScope
autograding.  Specifically, write the following test scenarios:

FUN-WELCOME - Test that the homepage displays the quote "Debugging is twice as hard as writing the code in the first place. Therefore, if you write the code as cleverly as possible, you are, by definition, not smart enough to debug it.", and below it the author "- Brian W. Kernighan".

FUN-LINKS - Test that the first link "CS1632 D3 Home" is linked to "/".

FUN-FACT - Test that the factorial page ("/fact") displays the string "Factorial of 5 is 120!" upon pressing submit with the value "5".

FUN-FIB - Test that the Fibonacci page ("/fib") displays the string "Fibonacci of 5 is 5!" upon pressing submit with the value "5".

FUN-INVALID-VALUE - Test that the Fibonacci page ("/fib") displays the string "Fibonacci of -100 is 1!" upon pressing submit with the value "-100".

FUN-HELLO - Test that accessing the hello page ("/hello") with no trailing values in the URL displays the message "Hello CS1632, from Dr. Ahn!".

FUN-HELLO-TRAILING - Test that accessing "/hello/Jazzy" displays the message "Hello CS1632, from Jazzy!".

FUN-CATHY - Test that the Cathedral page ("/cathy") displays exactly three images in a numbered list.

## Task 2: Find three defects and write test cases for them

This is another buggy product.  You should find at least three defects and
report them using the standard defect template (just like in the first
deliverable).  You may want to do some exploratory testing first to see what
kind of issues are found before writing automated tests for them.  Think of
equivalence classes, edge cases and corner cases as we learned so far.

Next, write three additional test cases that fail and uncover those three
defects.  Name these test cases in this format: DEFECT[N]-[Requirement Name],
where N is 1, 2, or 3.  For example,  if you found 2 defects related to the
FUN-WELCOME requirement and 1 defect related to the FUN-CATHY requirement, you
will name them:

* DEFECT1-FUN-WELCOME
* DEFECT2-FUN-WELCOME
* DEFECT3-FUN-CATHY

Again, normally you wouldn't name them this way since there is no separate
category of tests that are meant to detect defects.  All tests are meant to
detect defects!  But this is only for ease of grading on GradeScope.

## Task 3: Add test cases to test suite and save project

1. Choose "Test Suites" from the left panel drop down menu.

1. There will already be a "Default Suite" there with possibly one or more tests.

1. Right click on "Default Suite", or click on the vertical-3-dot context menu button, and select "Rename" and rename to "D3".

1. Right click on "D3", or click on the vertical-3-dot context menu button, and
   select "Add tests".  Make sure all tests are checked.  Press the "Select"
button.

1. Click on the "Save project" button on the top right corner that looks like a
   floppy disk.  Save to file name "D3.side" in the deliverable root folder.

## Task 4: Export test suite to JUnit class

Once you are done writing your Selenium test suite, let's try exporting the test
suite in Selenium IDE to a Java JUnit test class.  

Follow these instructions:

1. Right click on "D3", or click on the vertical-3-dot context menu
   button, and select "Export".

1. Select "Java JUnit" in the list of language options and optionally check
   "Include step descriptions as a separate comment" to generate more detailed
comments.  Leave other boxes unchecked.

1. Save the resulting file into "D3Test.java".

You can now run the RedditCatsTest JUnit class using the provided
[TestRunner.java](TestRunner.java) using one of the following scripts:

* If you are running Windows:
   ```
   cd Windows
   run.bat
   ```
* If you are running Mac:
   ```
   cd Mac
   run.sh
   ```
* If you are running Linux:
   ```
   cd Linux
   run.sh
   ```

Make sure all tests pass by looking at the TestRunner result.  If there are any
failures, slightly touch up the D3Test.java Selenium tests to make them pass.
Although Selenium IDE usually does a good job in the translation, sometimes it
needs an extra hand.

## Submission

Each pairwise group will submit the exercise *once* to GradeScope, by *one
member* of the group.  The submitting member will press the "View or edit
group" link at the top-right corner of the assignment page after submission to
add his/her partner, as usual.  There are two submissions for this deliverable.

### GitHub submission

Submit your github repository to GradeScope at the "Deliverable 3 GitHub" link.
Once you submit, GradeScope will run the autograder to grade you and give
feedback.  If you get deductions, fix your code based on the feedback and
resubmit.  Repeat until you don't get deductions.

You can either create a GitHub repository just for Deliverable 3 and then submit
that.  Or, you may just directly upload files.  If you choose the latter
option, please make sure you upload the "D3.side" and
"D3Test.java" files.

### Report submission

Submit your report PDf file to "Deliverable 3 Report" on GradeScope.  Please
use the ReportTemplate.docx file provided in this directory to write your
report.  If you don't have a .docx compatible word processor, that's perfectly
fine as long as you follow the same organization.  A PDF version of the file is
at ReportTemplate.pdf.  

Everyone should have a title page with:
* Your names
* The title "CS 1632 - DELIVERABLE 3: Automated Web Testing"

Write a one paragraph summary and retrospective.  Describe issues you faced
when writing these tests and problems you would expect when using Selenium IDE
in your future workplaces based on your experiences.  Also write what you liked
about Selenium.

ON A SEPARATE PAGE, write a defect report.  There should be at least 3 defects.
Each defect should contain all necessary components including REPRODUCTION
STEPS, EXPECTED BEHAVIOR, OBSERVED BEHAVIOR, etc. described in Deliverable 1.  

## Grading

* Summary and retrospective - 5% 
* Defect reports - 15%
* GitHub autograder results - 80%

Please review grading\_rubric.txt for details.

## GradeScope Feedback

The GradeScope autograder works in 2 phases:

1. **D3Test on https://cs1632.appspot.com/**: This tests your D3Test.java file
   on the D3 website as originally intended.  All of your tests should pass.

1. **D3Test on https://cs1632-buggier.appspot.com/**: This tests your
   D3Test.java on a buggier version of the D3 website.  Now all tests should fail.

If you get deductions, both websites are available to you, so try them out
yourself.

## Resources

These links are the same ones posted at the end of the slides:

* Selenium IDE Command Reference:  
https://www.selenium.dev/selenium-ide/docs/en/api/commands
