killall mongod

rm -rf mongodb/data1
rm -rf mongodb/data2
rm -rf mongodb/data3

mkdir mongodb/data1
mkdir mongodb/data2
mkdir mongodb/data3 

mongod --oplogSize 30 --replSet rs0 --port 20001 --logpath mongodb/log/1.log --dbpath mongodb/data1 --nojournal --smallfiles &
mongod --oplogSize 30 --replSet rs0 --port 20002 --logpath mongodb/log/2.log --dbpath mongodb/data2 --nojournal --smallfiles &
mongod --oplogSize 30 --replSet rs0 --port 20003 --logpath mongodb/log/3.log --dbpath mongodb/data3 --nojournal --smallfiles &

