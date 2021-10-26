import com.lbw.domian.User;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;

/**
 * @author ：lbw
 * @date ：Created in 2021/8/9 15:16
 * @description：TODO
 */
public class lucene {
    @Test
    public void createIndexDB() throws Exception {

        //把数据填充到JavaBean对象中
        User user = new User("1", "钟福成", "未来的程序员");

        //创建Document对象【导入的是Lucene包下的Document对象】
        Document document = new Document();

        //将JavaBean对象所有的属性值，均放到Document对象中去，属性名可以和JavaBean相同或不同


        /**
         * 向Document对象加入一个字段
         * 参数一：字段的关键字
         * 参数二：字符的值
         * 参数三：是否要存储到原始记录表中
         *      YES表示是
         *      NO表示否
         * 参数四：是否需要将存储的数据拆分到词汇表中
         *      ANALYZED表示拆分
         *      NOT_ANALYZED表示不拆分
         *
         * */
        document.add(new StringField("id", user.getId(), Field.Store.YES));
        document.add(new StringField("userName", user.getUserName(), Field.Store.YES));
        document.add(new StringField("sal", user.getSal(), Field.Store.YES));

        //创建IndexWriter对象
        //目录指定为E:/createIndexDB
        Directory directory = FSDirectory.open(new File("D:/createIndexDB").toPath());

        //使用标准的分词算法对原始记录表进行拆分
        Analyzer analyzer = new StandardAnalyzer();


        /**
         * IndexWriter将我们的document对象写到硬盘中
         *
         * 参数一：Directory d,写到硬盘中的目录路径是什么
         * 参数二：Analyzer a, 以何种算法来对document中的原始记录表数据进行拆分成词汇表
         * 参数三：MaxFieldLength mfl 最多将文本拆分出多少个词汇
         *
         * */
        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));

        //将Document对象通过IndexWriter对象写入索引库中
        indexWriter.addDocument(document);
        //关闭IndexWriter对象
        indexWriter.close();

    }

}
