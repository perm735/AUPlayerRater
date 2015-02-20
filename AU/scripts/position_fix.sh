#!/bin/bash
if [ -z "$1" ]; then
    echo "First argument should be the file name"
    exit 1;
fi

TMPFILE="/tmp/$1"
if [ -e "$TMPFILE" ]; then
    echo "remove $TMPFILE"
    rm $TMPFILE
fi

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
            newline=$(echo "$newline" | sed 's/#/\" /')
    
            # First strip off the '|'
            newline=$(echo "$newline" | sed 's/|//')
        
            # then replace comma's with tabs
            newline=$(echo "$newline" | sed 's/,/\t/g')
        
            # next replace the first space with a comma
            newline=$(echo "$newline" | sed 's/ /,/')
        else
            # special case
            newline=$(echo "$name" | sed 's/,/\t/g')
            newline=$(echo "$newline" | sed 's/\t/,/')
        fi
        echo "$newline" >> $TMPFILE
    else
        echo "$name" >> $TMPFILE
    fi
done < $1

# all done
mv $TMPFILE $1
