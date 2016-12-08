import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.*;
import com.mysql.jdbc.PreparedStatement;
 
public class InsertTest {
 
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
/*        final String url = "jdbc:mysql://127.0.0.1/teamtalk_bonc";
        final String name = "com.mysql.jdbc.Driver"; 
        final String user = "root"; 
        final String password = "root";*/
        final String url = "jdbc:mysql://172.16.63.198/teamtalk";
        final String name = "com.mysql.jdbc.Driver";
        final String user = "teamtalk";
        final String password = "test@123";
        Connection conn = null; 
        Class.forName(name);//指定连接类型 
        conn = DriverManager.getConnection(url, user, password);//获取连接 
        if (conn!=null) {
            System.out.println("获取连接成功");
            insert(conn);
        }else {
            System.out.println("获取连接失败");
        }
 
    }
    public static void insert(Connection conn) {
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
     //   String prefix = "INSERT INTO t_teacher (id,t_name,t_password,sex,description,pic_url,school_name,regist_date,remark) VALUES ";
        String prefix = "INSERT INTO IMUser (sex,name,domain,nick,password,salt,phone,email,avatar,departId,status,created,updated)  VALUES ";
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement  pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
            // 外层循环，总提交事务次数
            for (int i = 1; i <= 10; i++) {
                suffix = new StringBuffer();
                // 第j次提交步长
                for (int j = 1; j <= 3000; j++) {
                    // 构建SQL后缀
               //     suffix.append("('" + uutil.UUIDUtil.getUUID()+"','"+i*j+"','123456'"+ ",'男'"+",'教师'"+",'www.bbk.com'"+",'XX大学'"+",'"+"2016-08-12 14:43:26"+"','备注'" +"),");
               //     suffix.append("('" + uutil.UUIDUtil.getUUID()+"','"+i*j+"','123456'"+ ",'男'"+",'教师'"+",'www.bbk.com'"+",'XX大学'"+",'"+"2016-08-12 14:43:26"+"','备注'" +"),");
                //      suffix.append("('1'"+",'"+i*j+"','0'"+",'"+i*j+"','123456'"+ ",'6798'"+",'18701183963'"+",'wangnan@bonc.com.cn'"+",'http://192.168.8.1:8700/'"+",'3'"+",'3'"+",'1458804206'"+",'1458804206'" +"),");
                      suffix.append("('1'"+",'"+i*j+"','0'"+",'"+i*j+"','aba90e3a4c7ecd6343b4803b4bf6c2a7'"+ ",'1825'"+",''"+",''"+",''"+",'3'"+",'0'"+",'1481181493'"+",'1481181493'" +"),");

                }
                // 构建完整SQL
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                // 添加执行SQL
                pst.addBatch(sql);
                // 执行操作
                pst.executeBatch();
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("1000万条数据插入花费时间 : " + (end - begin) / 1000 + " s");
        System.out.println("插入完成");
    }
}