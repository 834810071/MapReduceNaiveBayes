package Predition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

// 多项式模型
public class Prediction {
    private static Hashtable<String, Double> class_prob = new Hashtable<String, Double>();
    private static Hashtable<Map<String, String>, Double> class_term_prob = new Hashtable<Map<String, String>, Double>();
    private static Hashtable<String, Double> class_term_total = new Hashtable<String, Double>();
    private static Hashtable<String, Double> class_term_num = new Hashtable<String, Double>();

    Prediction() throws IOException {
        //计算先验概率class_prob
        BufferedReader reader = new BufferedReader(new FileReader(new File("/home/jxq/hadoop/code/MapReduceNaiveBayes/jxq/OUTPUT/DocOutput/part-r-00000")));
        double file_total = 0;  // 统计文档总数
        while (reader.ready())
        {
            String line = reader.readLine();    // 0：类   1:文档数量
            String[] args = line.split("\t");
            file_total += Double.valueOf(args[1]);
        }

        // 先验概率P(c)= 类c下文件总数/整个训练样本的单词总数
        reader = new BufferedReader(new FileReader(new File("/home/jxq/hadoop/code/MapReduceNaiveBayes/jxq/OUTPUT/DocOutput/part-r-00000")));
        while (reader.ready())
        {
            String line = reader.readLine();
            String[] args = line.split("\t");
            class_prob.put(args[0], Double.valueOf(args[1]) / file_total);
            System.out.println(String.format("%s : %f", args[0], Double.valueOf(args[1]) / file_total));
        }

        // 分别计算每个类别的单词总数
        reader = new BufferedReader(new FileReader(new File("/home/jxq/hadoop/code/MapReduceNaiveBayes/jxq/OUTPUT/WordOutput/part-r-00000")));
        while (reader.ready())
        {
            String line = reader.readLine();
            String[] args = line.split("\t");   // 0：类，1：词条，2：词频
            double count = Double.valueOf(args[2]);
            String classname = args[0];
            class_term_total.put(classname, class_term_total.getOrDefault(classname, 0.0) + count);
        }

        // 计算单词集合大小  也就是看出现过多少个单词[不包括重复出现的]
        reader = new BufferedReader(new FileReader(new File("/home/jxq/hadoop/code/MapReduceNaiveBayes/jxq/OUTPUT/WordOutput/part-r-00000")));
        while (reader.ready())
        {
            String line = reader.readLine();
            String[] args = line.split("\t");   // 0：类，1：词条，2：词频
            String classname = args[0];
            class_term_num.put(classname, class_term_num.getOrDefault(classname, 0.0) + 1.0);
        }
        System.out.println(String.format(("%f:%f"),class_term_num.get("CANA"),class_term_num.get("CHINA")));

        // 类条件概率P(tk|c)=(类c下单词tk在各个文档中出现过的次数之和+1)/(类c下单词总数+|V|)
        // 计算每个类别里面出现的词条概率class-term prob  P(c|t)
        reader = new BufferedReader(new FileReader(new File("/home/jxq/hadoop/code/MapReduceNaiveBayes/jxq/OUTPUT/WordOutput/part-r-00000")));
        while (reader.ready())
        {
            String line = reader.readLine();
            String[] args = line.split("\t");   // 0：类，1：词条，2：词频
            double count = Double.valueOf(args[2]);
            String classname = args[0];
            String term = args[1];
            Map<String, String> map = new Hashtable<String, String>();
            map.put(classname, term);
            class_term_prob.put(map, (count+1) / (class_term_total.get(classname) + class_term_num.get(classname)));
        }
    }

    // P(c|d) = max { 求和 p(d|c)p(c)}
    public static double conditionalProbabilityForClass(String content, String classname)
    {
        // 计算一个文档属于某类的概率
        double result = 0;
        String[] words = content.split("\n");
        for (String word : words)
        {
            Map<String, String> map = new Hashtable<String, String>();
            map.put(classname, word);
            result += Math.log(class_term_prob.getOrDefault(map, 1.0 / (class_term_total.get(classname) + class_term_num.get(classname))));
        }
        result += Math.abs(Math.log(class_prob.get(classname)));
        return result;
    }

}
