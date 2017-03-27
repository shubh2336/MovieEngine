# Buzzotel

## Steps to configure & execute the code

Fetch repository on your local
```
git fetch https://github.com/shubh2336/MovieEngine.git
```

Export the jar file (json-simple-1.1.jar) present in vendor directory to CLASSPATH variable
```
export CLASSPATH=$CLASSPATH:<absolute_path>/MovieEngine/vendor/json-simple-1.1.jar:<absolute_path>/MovieEngine/vendor/json-simple-1.1.jar
```

Navigate to lib folder
```
cd lib
```

Compile *.java files
```
javac *.java
```

Execute the main module
```
java Main
```

Output file gets generated in data folder with the name output.json