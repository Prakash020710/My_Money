
# Pre-requisites
* Java 1.8
* Gradle 5.1

# How to run the code

We have provided scripts to execute the code. 

Use `run.sh` if you are Linux/Unix/macOS Operating systems and `run.bat` if you are on Windows.  Both the files run the commands silently and prints only output from the input file `sample_input/input1.txt`. You are supposed to add the input commands in the file from the appropriate problem statement. 

Internally both the scripts run the following commands 

 * `gradle clean build -x test --no-daemon` - This will create a jar file `geektrust.jar` in the `build/libs` folder.
 * `java -jar build/libs/geektrust.jar sample_input/input1.txt` - This will execute the jar file passing in the sample input file as the command line argument

 We expect your program to take the location to the text file as parameter. Input needs to be read from a text file, and output should be printed to the console. The text file will contain only commands in the format prescribed by the respective problem.

 Use the build.gradle file provided along with this project. Please change the main class entry under the `jar` task

 ```
 manifest {
        attributes 'Main-Class' : 'com.example.geektrust.Main' //Change this to the main class of your program which will be executed
    }
```
in the build.gradle if your main class has changed.

 # Running the code for multiple test cases

 Please fill `input1.txt` and `input2.txt` with the input commands and use those files in `run.bat` or `run.sh`. Replace `java -jar build/libs/geektrust.jar sample_input/input1.txt` with `java -jar build/libs/geektrust.jar sample_input/input2.txt` to run the test case from the second file. 

 # How to execute the unit tests

 `gradle clean test --no-daemon` will execute the unit test cases.

# Help

You can refer our help documents [here](https://help.geektrust.com)
You can read build instructions [here](https://github.com/geektrust/coding-problem-artefacts/tree/master/Java)

# Problem statment
Problem statement can be found [here](https://codu.ai/coding-problem/mymoney)

# Assumptions
Assumptions are as per the problem statement
Rounding can impact the expacted result with 1,2 digits
File commands are expected in order, for example ALLOCATE should come first before SIP or CHANGE, else processing will fail with message : Failed to process file!

# Limitations/Obervations
1.> Gradle version used is old as per instructions, latest like 8x should be used.
2.> Spring boot is not required or used much in solution, its used to make solution as per enterprise solutions/ codebase. Also SPring comes with fetaure like Dependency injection.
3.> Some tests are only for code coverage however its still useful as it will test the solution is not throwing error and processing supplied input.

# Code coverage
Main class, Command line class, dtos, config, constants etc are excluded from coverage. 
Code coverage is 70+.
