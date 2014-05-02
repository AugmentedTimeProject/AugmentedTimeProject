#!/bin/sh

echo """
colorscheme elflord 
set nobk
set nowb
set tabstop=4
set shiftwidth=4
set softtabstop=4
set smarttab
set expandtab
set autoindent
set cindent
set background=dark
""" >> ~/.vimrc

echo Y|sudo apt-get update
echo Y|sudo apt-get install gcc g++ make gdb strace autoconf automake git libtool ntp

#sudo rm -rf zeromq-4.0.4.tar.gz zeromq-4.0.4
#wget http://download.zeromq.org/zeromq-4.0.4.tar.gz
#tar xvf zeromq-4.0.4.tar.gz

#curpath=`pwd`
#cd zeromq-4.0.4
#sudo ./configure
#sudo make
#sudo make install
#cd $curpath
#sudo rm -rf zeromq-4.0.4.tar.gz zeromq-4.0.4

curpath=`pwd`
rm -rf ~/AT
mkdir -p ~/AT
cd ~/AT
git clone https://github.com/AugmentedTimeProject/AugmentedTimeProject.git
cd AugmentedTimeProject/AugmentedTime_Amazon_EC2_Cloud
make
cd $curpath
exit
