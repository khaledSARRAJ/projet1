cd /d "%~dp0"
REM projet1
call mvn clean install -f pom.xml
 java -jar ./target/projet1.jar
pause
