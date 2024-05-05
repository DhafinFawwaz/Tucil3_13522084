# Tucil 3

## Author
- 13522084 Dhafin Fawwaz Ikramullah

## GUI
### Compile & Run GUI
Run the following command to compile and run the GUI
```
./compilerun.bat
```
or
```

### Run GUI
Run the following command to run the GUI
```
./run.bat
```
or
```
java -XX:CompileThreshold=1 -cp bin Main
```

## Test Case Runner

### Running Test Case
You can use the TestCaseRunner to run multiple test case easily. Use the following command
```
./compileruntest.bat
```
or
```
java -XX:CompileThreshold=1 -cp bin TestCaseRunner <input file path> <dictionary file path>
```
For example
```
java -XX:CompileThreshold=1 -cp bin TestCaseRunner ./test/input.txt ./src/Asset/dictionary.bin
```

### Writing Test Case to File
To write test case result in file, use pipe like the following
```
./compileruntest.bat > test/output.txt
```
or
```
java -XX:CompileThreshold=1 -cp bin TestCaseRunner ./test/input.txt ./src/Asset/dictionary.bin > ./test/output.txt
```

### Tips
you can also replace the dictionary by replacing the file in `Asset/dictionary.txt`

