dir /b /s .\*.java > file.txt
findstr /i .java file.txt > src.txt
mkdir temp
javac --source 8 --target 8 -d temp @src.txt
del file.txt
del src.txt
cd temp
java test.Main
cd ../
@REM jar -cf ..\DAO.jar .
@REM cd ..
@REM del /f temp
@REM move /y DAO.jar E:\ITU\Collaboration-Framework\DAO\
@REM del  DAO.jar 
