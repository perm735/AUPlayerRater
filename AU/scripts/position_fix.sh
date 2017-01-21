#!/bin/bash
# You can use this find command to execute this script on all csv's:
# find . -maxdepth 1 -name "*.csv" -exec ~/Builds/AUPlayerRater/AU/scripts/position_fix.sh {} \ 

if [ -z "$1" ]; then
    echo "First argument should be the file name"
    exit 1;
fi

echo "Processing $1"

FILE=${1:2}

TMPFILE="/tmp/$FILE"
if [ -e "$TMPFILE" ]; then
    echo "remove $TMPFILE"
    rm $TMPFILE
fi

PREFILE="/tmp/One$FILE"
if [ -e "$PREFILE" ]; then
    echo "remove $PREFILE"
    rm $PREFILE
fi

cut -d, -f1 --complement $FILE >> $PREFILE

#TODO: don't do the first two lines
counter=0
while read line
do
    # echo "Text read from file - $name"
    name=$line

    ((counter++))

    if [ $counter -gt 1 ]; then

        if [ $counter -gt 2 ]; then
            newline=$(echo "$name" | sed 's/,/#/')
            newline=$(echo "$newline" | sed 's/^/\"/')
            newline=$(echo "$newline" | sed 's/#/\"\t/')

            # First strip off the '|'
            newline=$(echo "$newline" | sed 's/|//')
        
            # then replace comma's with tabs
            newline=$(echo "$newline" | sed 's/,/\t/g')

            # next replace the first space with a comma
            newline=$(echo "$newline" | sed 's/\" /\",/')
    
        else
            # special case
            newline=$(echo "$name" | sed 's/,/\t/g')
            newline=$(echo "$newline" | sed 's/\t/,/')
        fi

        # if the line contains Report,Updated, don't write it out
        if [[ $string != *"Report Updated"* ]]; then
            echo "$newline" >> $TMPFILE
        fi
    else
        echo "$name" >> $TMPFILE
    fi
done < $PREFILE

# all done
mv $TMPFILE $1
#rm $PREFILE
echo "Done with $1"
