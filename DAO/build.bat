@REM set "lib=E:\LIBRARY"
Get-ChildItem -Recurse -Filter "*.java" | ForEach-Object { $_.FullName } > src.txt
mkdir temp
$src = Get-Content src.txt
javac --source 8 --target 8 -d temp $src
del src.txt
cd temp
jar -cf ..\DAO.jar .
cd ..
del -Recurse temp
@REM move DAO.jar "%lib%"