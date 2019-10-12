## FileInputFormat 和 RecordReader
>> 无论我们以怎样的方式从分片中读取一条记录，每读取一条记录都会调用RecordReader类；
   系统默认的RecordReader是LineRecordReader，TextInputFormat；
   LineRecordReader是用每行的偏移量作为map的key，每行的内容作为map的value；
   应用场景：自定义读取每一条记录的方式；自定义读入key的类型，如希望读取的key是文件的路径或名字而不是该行在文件中的偏移量。


[Ubuntu 16.04上安装Hadoop并成功运行](https://wangchangchung.github.io/2017/09/28/Ubuntu-16-04%E4%B8%8A%E5%AE%89%E8%A3%85Hadoop%E5%B9%B6%E6%88%90%E5%8A%9F%E8%BF%90%E8%A1%8C/)    

[Hadoop教程](https://www.w3cschool.cn/hadoop/g94s1p36.html)    

[Hadoop Map/Reduce教程](https://hadoop.apache.org/docs/r1.0.4/cn/mapred_tutorial.html)

[Hadoop 实现朴素贝叶斯 Naive Bayes 文本分类](https://tengzi-will.github.io/2018/12/24/Hadoop-%E5%AE%9E%E7%8E%B0%E6%9C%B4%E7%B4%A0%E8%B4%9D%E5%8F%B6%E6%96%AF-Naive-Bayes-%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB/)   

[分类算法之朴素贝叶斯分类(Naive Bayesian classification)](https://www.cnblogs.com/leoo2sk/archive/2010/09/17/naive-bayesian-classifier.html?login=1)

[7个实例全面掌握Hadoop MapReduce](https://dbaplus.cn/news-73-1277-1.html)

[基于贝叶斯算法的文本分析算法](https://pangjiuzala.github.io/2015/10/03/基于贝叶斯算法的文本分析算法/)

[机器学习：准确率(Precision)、召回率(Recall)、F值(F-Measure)、ROC曲线、PR曲线](https://blog.csdn.net/quiet_girl/article/details/70830796)

[谈谈评价指标中的宏平均和微平均](https://www.cnblogs.com/robert-dlut/p/5276927.html)