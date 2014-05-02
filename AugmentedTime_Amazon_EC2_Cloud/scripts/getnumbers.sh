#!/bin/sh

trap '{ echo "Script interrupted. Cleaning up"; rm -rf ips.txt logs.tar.gz logs numbers.txt big.log; exit 1; }' INT

. getlogs.sh

cwd=`pwd`

rm -rf logs
mkdir logs
cp logs.tar.gz logs
cd logs
tar xvf logs.tar.gz
rm -rf logs.tar.gz

rm -rf big.log
for i in `ls *.log`
do
    cat $i >> big.log
done

rm -rf $cwd/numbers.txt
for i in {0..20}
do
    res=`cat big.log|grep "\[$i\]"|wc -l`
    echo "Count $i: $res" >> $cwd/numbers.txt
done
rm -rf big.log

cd $cwd
rm -rf logs
