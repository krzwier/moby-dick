# Word Counter Application

The goal of this project is to find and count the top 100 most frequently occurring 
words in Moby Dick.  I broadened the scope of the project by allowing the user to input
any text file and adding a simple GUI that displays the top 100 words in a histogram.  

This is a 48-hour project interview for Source Allies, Inc.

## Notes

My word count numbers for Moby Dick are lower than many others that I found from searching the web.  
This is because I made the choice to include the hyphen ('-') and apostrophe ('’') characters 
as word characters.  This means that "whale" and "whale's" are counted separately, as are "carpet"
and "carpet-bag".  

I built this project using IntelliJ IDEA CE.  I used the Maven and JavaFX frameworks, both of 
which are new to me.  (I fear that I don't have a firm grasp of the Maven directory structure
conventions, so files may not be located in their conventional places.)  I also used 
Tablesaw's Java wrappers for plotly.  
 
## Executables

The finished code produces two separate executables.  The main method in the WordCounter class 
produces a command-line program that prompts the user for the name of a text file, and the outputs 
a histogram in a web browser.  The other main method is a GUI
that is a bit prettier and more user-friendly.  Both executables rely on the same underlying logic
in the WordCounter and Sorter classes.

## Extra files

A few test files and a couple of extra text files of classics from Project Gutenberg are included 
in this repository for ease of testing.

## Acknowledgments

* I was inspired by Matt Pearce's awesome visualization here: https://roadtolarissa.com/whalewords/.
 (GitHub repo: https://github.com/1wheel/whalewords).  My visualization is modest compared to his.
* I also spent a lot of time on Tablesaw's GitHub pages here: 
https://jtablesaw.github.io/tablesaw/userguide/toc
* Matthew Gillard's post on "Getting Started with JavaFX" helped me get the GUI up and running.
  I modeled the basic structure of my stage on his. (https://www.twilio.com/blog/getting-started-with-javafx)

