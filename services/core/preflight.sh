#!/bin/bash
seed=$(date +%F%T | sed -e "s/-/h/g" -e "s/:/c/g" -e "s/0/z/g" -e "s/1/f/g")
len=${#seed}
rs=""
for i in `seq 8`
do
    r=$(( RANDOM % len ))
    rs=$rs""${seed:r:1}
done
export init_code="$rs"
echo "Your secret is: "$rs

