package DocCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DocCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        args = new String[]{"/home/jxq/hadoop/code/MapReduceNaiveBayes/jxq/INPUT/TRAIN", "/home/jxq/hadoop/code/MapReduceNaiveBayes/jxq/OUTPUT/DocOutput"};

        // 1.获取job信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2.获取jar包位置
        job.setJarByClass(DocCountDriver.class);

        // 3.关联自定义的mapper和reducer
        job.setMapperClass(DocCountMapper.class);
        job.setReducerClass(DocCountReducer.class);

        // 4.设置自定义的InputFormat类
        job.setInputFormatClass(WholeFileInputFormat.class);

        // 5.设置map输出数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 6.设置最终输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 7.设置输入和输出文件路径
        ArrayList<Path> paths = GetPath(args[0]);
        for (int i = 0; i < paths.size(); i++)  // 看文件夹下是否还有文件夹
        {
            FileInputFormat.addInputPath(job, paths.get(i));
        }
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 8.提交代码
        boolean result = job.waitForCompletion(true);
        System.exit(result?0:1);
    }

    private static ArrayList<Path> GetPath(String path) {
        // 获取path路径下的所有子文件夹路径
        ArrayList<Path> paths = new ArrayList<Path>();
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory())
        {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; ++i)
            {
                // 如果还是文件夹
                if (files[i].isDirectory())
                {
                    // 加入路径列表
                    paths.add(new Path(files[i].getPath()));
                }
                else
                {
                    continue;
                }
            }
        }
        return paths;
    }
}
