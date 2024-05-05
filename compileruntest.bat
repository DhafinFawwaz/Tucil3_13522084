javac -d bin ./src/TestCaseRunner.java -cp ./src
java -XX:CompileThreshold=1 -cp bin TestCaseRunner ./test/input.txt ./src/Asset/dictionary.bin