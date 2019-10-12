package Predition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Evaluation {

    private static String[] CLASS_NAMES = {"CHINA", "CANA"};
    // tp, tn, fp, fn, p, r, f1 7个数据结构
    public static Hashtable<String, Integer> TP = new Hashtable<String, Integer>();
    public static Hashtable<String, Integer> TN = new Hashtable<String, Integer>();
    public static Hashtable<String, Integer> FP = new Hashtable<String, Integer>();
    public static Hashtable<String, Integer> FN = new Hashtable<String, Integer>();
    public static Hashtable<String, Double> P = new Hashtable<String, Double>();
    public static Hashtable<String, Double> R = new Hashtable<String, Double>();
    public static Hashtable<String, Double> F1 = new Hashtable<String, Double>();

    public static void main(String[] args) throws IOException {
        calculatePrecision();

        for (String classname : CLASS_NAMES)
        {
            double p = 0, r = 0, f1 = 0, tp = 0, fp = 0, fn = 0, tn = 0;
            tp = TP.getOrDefault(classname,0);
            fp = FP.getOrDefault(classname,0);
            fn = FN.getOrDefault(classname,0);
            tn = TN.getOrDefault(classname,0);
            System.out.println(tp);
            System.out.println(fp);
            System.out.println(fn);
            System.out.println(tn);

            p = tp / (tp+fp);
            r = tp / (tp+fn);
            f1 = 2*p*r / (p+r);
            P.put(classname, p);
            R.put(classname, r);
            F1.put(classname, f1);
            System.out.println(String.format("%s precision: %f----recall: %f----f1:%f ", classname, p, r, f1));
        }
        printMicroAverage();
        printMacroAverage();
    }

    // 微平均是对数据集中的每一个实例不分类别进行统计建立全局混淆矩阵，然后计算相应指标
    private static void printMicroAverage() {
        // 计算微平均
        double sumP = 0, sumR = 0, sumF1 = 0, length = CLASS_NAMES.length;
        for(String classname : CLASS_NAMES){
            sumP += P.get(classname);
            sumR += R.get(classname);
            sumF1 += F1.get(classname);
        }
        System.out.println(String.format("all classes micro average P: %f",sumP / length));
        System.out.println(String.format("all classes micro average R: %f",sumR / length));
        System.out.println(String.format("all classes micro average F1: %f",sumF1 / length));
    }

    // 宏平均（Macro-averaging），是先对每一个类统计指标值，然后在对所有类求算术平均值
    private static void printMacroAverage() {
        // 计算宏平均
        double tp = 0, fp = 0, fn = 0;
        double p = 0, r = 0, f1 = 0;
        for(String classname : CLASS_NAMES){
            tp += TP.get(classname);
            fp += FP.getOrDefault(classname,0);
            fn += FN.getOrDefault(classname,0);
        }
        p = tp / (tp+fp);
        r = tp / (tp+fn);
        f1 = 2*p*r / (p+r);
        System.out.println(String.format("all classes macro average P: %f",p));
        System.out.println(String.format("all classes macro average R: %f",r));
        System.out.println(String.format("all classes macro average F1: %f",f1));
    }

    public static void calculatePrecision() throws IOException {
        // 读取预测结果，计算TP,FP,FN,TN值并存入hash表
        BufferedReader reader=new BufferedReader(new FileReader("/home/jxq/hadoop/code/MapReduceNaiveBayes/jxq/OUTPUT/ClassOutput/"+"part-r-00000"));

        // key : 文件名&真实类别  value : 预测类别
        while(reader.ready()){
            String line=reader.readLine();
            String[] args = line.split("\t");
            String[] args1 = args[0].split("&");

            String truth = args1[1];
            String predict = args[1];

            for(String classname : CLASS_NAMES) {
                if(truth.equals(classname) && predict.equals(classname)) {
                    TP.put(classname,TP.getOrDefault(classname,0)+1);
                }else if(truth.equals(classname) && !predict.equals(classname)) {
                    FN.put(classname,FN.getOrDefault(classname,0)+1);
                }else if(!truth.equals(classname) && predict.equals(classname)) {
                    FP.put(classname,FP.getOrDefault(classname,0)+1);
                }else if(!truth.equals(classname) && !predict.equals(classname)) {
                    TN.put(classname,TN.getOrDefault(classname,0)+1);
                }
            }
        }
    }
}
