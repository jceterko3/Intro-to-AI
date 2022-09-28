Intro to AI: Assignment 1 Part 1
Contributors: Jennifer Ceterko, Amanda Kang, Kenisha Sequeira  

Files: 

    - Astar.java 
        Implementation of A* algorithm and methods
    - ThetaStar.java
        Implementation of Theta* algorithm and methods 
    - Node.java
        Implementation of node structure
    - minHeap.java
        Implementation of heap structure 
    - Frame.java
        Implementation of GUI 
    - Test.java
        Main file - used for compliation and tests
    - createFile.java
        used to generate test cases in format described in assignment 
    - tests folder
        holds all 50 100x50 grids used for experiments 

Compliation/Execution:

In the terminal, compile all files individually by using the following commands: 

    javac Node.java
    javac minHeap.java
    javac Astar.java
    javac ThetaStar.java
    javac Frame.java
    javac Test.java

To execute, use the following command format: 

    java Test filename.txt
    
    where filename is the input file following the format listed in the assignment 
        1st line = start vertex
        2nd line = goal vertex
        3rd line = grid dimension 
        remaining lines = cell number and its status (blocked or unblocked)
    the code will only run properly if the file is in the correct format with the correct number of cells 

GUI Description: 

    The GUI will appear in a popup window and it is best to expand the GUI to full screen if possible to ensure the whole grid is seen. The GUI will have all components (start vertex, goal vertex, theta* path, A* path, and number labels) appear at once. 

    The terminal will display the line "Enter a vertex in the form x y to view more info or close GUI to exit" where the user can enter a vertex in the form x y to view the f, g and h values associated to that vertex when using A* and theta*. The question will keep displaying until the user closes the GUI window. 

    
