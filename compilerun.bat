javac -d bin ./src/Main.java -cp ./src
java -XX:CompileThreshold=1 -cp bin Main