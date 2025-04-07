@echo off
echo Cleaning project...
call mvn clean

echo Compiling project..
call mvn compile

echo Running project...
call mvn exec:java -Dexec.mainClass="com.taxcalculator.Main"

pause