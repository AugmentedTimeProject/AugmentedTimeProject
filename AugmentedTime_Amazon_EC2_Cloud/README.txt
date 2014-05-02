This module can be compiled in Linux environment with standard C library.
Our experiments are automated using several scripts in "scripts" directory
which require following packages to be installed in test machines.

Packages required in test machines:
-----------------------------------
gcc g++ make gdb strace autoconf automake git libtool ntp

We used Ubuntu 14 machines from amazon EC2 cloud to perform these tests.

STEPS TO REPEAT OUR EXPERIMENT:
-------------------------------
1. Create desired number of instances in Amazon EC2 cloud
2. Navigate to scripts directory
3. Create a file named "data.txt" and a directory named cert
4. Keep all .pem files corresponding to EC2 cloud in cert directory. Check the
file permissions. It should be 400
5. For each machine in EC2 cloud, add a line in data.txt with its IP address
and certificate file name. For example, if a machine's ip is 51.208.93.4 and
it uses "amazon.pem cert", Add a line "51.208.93.4 amazon.pem" in data.txt.
Each entry should start in new line
6. Run initialize.sh script to initialize all EC2 machines with required
software and AugmentedTime source code
7. Run runpeer.sh to start the test
8. Run getnumbers.sh to collect statistics from remote machines
9. To abort a running test, run killpeer.sh

Note: These scripts are tested on Ubuntu 14
