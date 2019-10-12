datapath='/home/jxq/hadoop/code/MapReduceNaiveBayes/NBCorpus'
trainpath='/home/jxq/hadoop/code/MapReduceNaiveBayes/training'
dirlist=`ls $datapath`

dirNum=`ls $datapath  -l|grep "^-"|wc -l`
#echo $dirNum
k=0
for fileName in $dirlist
do 
	fileNameArr[k]=$fileName
	k=$k+1
done

arr=( $(seq 1 $dirNum ))
num=${#arr[*]}
#echo $num

filterNum=120

res=${arr[$(( $RANDOM%num ))]}
fileArr[1]=$res
#echo $fileArr[1]

let  i=2
while(( i <= filterNum));
do
	res=${arr[$(($RANDOM%num))]}
	fileArr[i]=$res
	#echo $res
	#echo ${fileArr[i]}
	for ((j=1 ; j<i ; j++));
	do
		numJ=${fileArr[j]}
		if  [ $res = $numJ  ]
		then
			unset fileArr[i]
			((i--))#i=$i-1
			break
		fi
	done
	((i++))#i=$i+1
done

for (( indexNum = 0 ; indexNum < $filterNum ; indexNum++ ))
do
	#i=${fileArr[indexNum]}
	#echo $i
	#echo ${fileNameArr[$i]}
	echo $indexNum
	mv  $datapath/${fileNameArr[${fileArr[indexNum]}]} $trainpath
done
