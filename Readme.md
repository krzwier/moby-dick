# Word Counter Application

The original goal of this project was to find and count the top 100 most frequently occurring words in Moby Dick.  I broadened the scope of the project by allowing the user to input any text file and adding a simple GUI that displays the top 100 words in a histogram.  

## Demo
![demo](https://user-images.githubusercontent.com/21344516/118546703-ef338e00-b71d-11eb-9d7d-7e0a65bf612c.gif)


## Notes

My word count numbers for Moby Dick are lower than many others that I found from searching the web.  This is because I made the choice to include the hyphen ('-') and apostrophe ('’') characters as word characters.  This means that "whale" and "whale's" are counted separately, as are "carpet" and "carpet-bag".  
 
## Executables

The finished code produces two separate executables.  The main method in the WordCounter class produces a command-line program that prompts the user for the name of a text file, and then outputs a histogram in a web browser.  The other main method is a GUI that is a bit prettier and more user-friendly.  Both executables rely on the same underlying logic in the WordCounter and Sorter classes.

## Extra files

A few test files and a couple of extra text files of classics from Project Gutenberg are included in this repository for ease of testing.

## Acknowledgments

* I was inspired by Matt Pearce's awesome visualization [here](https://roadtolarissa.com/whalewords/) (GitHub repo [here](https://github.com/1wheel/whalewords)).  My visualization is modest compared to his.
* I also spent a lot of time on Tablesaw's GitHub pages [here](https://jtablesaw.github.io/tablesaw/userguide/toc).
* Matthew Gillard's post on ["Getting Started with JavaFX"](https://www.twilio.com/blog/getting-started-with-javafx) helped me get the GUI up and running. I modeled the basic structure of my stage on his. 

