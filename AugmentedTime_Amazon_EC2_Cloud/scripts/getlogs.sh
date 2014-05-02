#!/bin/sh

trap '{ echo "Script interrupted. Cleaning up"; rm -rf ips.txt logs.tar.gz *.log *.out; exit 1; }' INT

cat data.txt |awk '{print $1}' > ips.txt

# for all ips in ips.txt file
count=1
for myip in `cat ips.txt`
do

cert=`cat data.txt |grep "$myip "|awk '{print $2}'`
scp -i cert/$cert ubuntu@$myip:/home/ubuntu/AT/AugmentedTimeProject/AugmentedTime_Amazon_EC2_Cloud/events.log $myip.events.log
scp -i cert/$cert ubuntu@$myip:/home/ubuntu/AT/AugmentedTimeProject/AugmentedTime_Amazon_EC2_Cloud/nohup.out $myip.nohup.out

count=$((count + 1))
done

rm ips.txt

rm -rf logs.tar.gz
tar cvfz logs.tar.gz `ls *.log` `ls *.out`
rm -rf *.log *.out
