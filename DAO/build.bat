@echo off
set "frameworkCodePATH=%USERPROFILE%\ITU\Mr_Naina\Framework\Framework"
set "lib=%USERPROFILE%\Documents\LIBRARY"

cd /d "%frameworkCodePATH%"

for /r %%i in (*.java) do (
    echo %%i >> src.txt
)

mkdir temp

javac -cp "%lib%\servlet-api.jar;%lib%\gson.jar" -d temp @src.txt

del src.txt

copy "%lib%\gson.jar" temp\gson.jar

cd temp

jar -cf ..\framework.jar .

cd ..

rmdir /s /q temp

move framework.jar "%lib%"