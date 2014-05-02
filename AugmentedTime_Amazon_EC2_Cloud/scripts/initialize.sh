#!/bin/sh

trap '{ echo "Script interrupted. Cleaning up"; rm -rf ips.txt; exit 1; }' INT

cat data.txt |awk '{print $1}' > ips.txt

# for all ips in ips.txt file
count=1
for myip in `cat ips.txt`
do

cert=`cat data.txt |grep "$myip "|awk '{print $2}'`
ssh -t -t -i cert/$cert ubuntu@$myip < setup.sh

count=$((count + 1))
done

rm ips.txt
