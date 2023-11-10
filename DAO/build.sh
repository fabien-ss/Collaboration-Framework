# lib=$HOME/Documents/LIBRARY

find -name '*.java' > src.txt

mkdir temp

javac --source 8 --target 8 -d temp @src.txt

rm src.txt  

cd temp

jar -cf ../Dao.jar .
cd ../
rm -r temp
# mv Dao.jar $lib
