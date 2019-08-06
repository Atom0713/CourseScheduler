package DB;

import java.sql.*;

class DBConnection {
    public static void main(String args[]) throws Exception{
//        try{
////            root.Class.forName("com.mysql.cj.jdbc.Driver");
////            Connection con= DriverManager.getConnection(
////                    "jdbc:mysql://localhost:3306/sonoo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false","root","Vandal#13");
//////here sonoo is database name, root is username and password
////            Statement stmt=con.createStatement();
////            ResultSet rs=stmt.executeQuery("select * from emp");
////            while(rs.next())
////                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
////            con.close();
////        }catch(Exception e){ System.out.println(e);}
        Connection con = createTable();
        //getTableData(con);
    }

    public static Connection getConnection() throws Exception{
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/sonoo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
            String username = "root";
            String password = "Vandal#13";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
        }catch (Exception e){ System.out.println(e); }
        return null;
    }
    public static Connection createTable() throws  Exception{
        try{
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Names(id int NOT NULL AUTO_INCREMENT, first varchar(20), last varchar(20), PRIMARY KEY(id)) ");
            create.executeUpdate();
            fillTable(con);
            getTableData(con);
            return con;
        }catch (Exception e){ System.out.println(e); }
        finally { System.out.println("Function completed."); return null;}
    }
    public static void fillTable(Connection con)throws  Exception{
        try {
            PreparedStatement fill = con.prepareStatement("INSERT Names(first, last) VALUES ('Enzo','Dbar')");
            fill.executeUpdate();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    public static void getTableData(Connection con){
        try {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Names");
            while(rs.next()){
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            }
            con.close();
        }catch(Exception ex){
            System.out.println("Here"+ex);
        }
    }
}
