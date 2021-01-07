package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;

public class NoticeService {
	  public List<Notice> getNoticeList(){
	      return getNoticeList("title", "", 1);
	   }
	   
	   public List<Notice> getNoticeList(int page){
	      return getNoticeList("title", "", page);
	   }
	   
	   public List<Notice> getNoticeList(String field, String query, int page){

		      List<Notice> list = new ArrayList<>();

		      String sql = "SELECT * FROM ("
		               + "    SELECT ROWNUM NUM, N.* "
		               + "    FROM (SELECT * FROM NOTICE WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
		               + ")"
		               + "WHERE NUM BETWEEN ? AND ?";
		         
		      
		      String url = "jdbc:oracle:thin:@localhost:1521:xe";

		      try {
		         Class.forName("oracle.jdbc.driver.OracleDriver");
		         Connection conn = DriverManager.getConnection(url,"aa","1111");
		         PreparedStatement st = conn.prepareStatement(sql);
		         st.setString(1, "%"+query+"%");
		         st.setInt(2, 1+(page-1)*10);
		         st.setInt(3, page*10);
		         
		         ResultSet rs = st.executeQuery();   
		         
		         
		         while(rs.next()) {
		            int id = rs.getInt("ID");
		            String title = rs.getString("TITLE");
		            String writerId = rs.getString("WRITER_ID");
		            Date regdate = rs.getDate("REGDATE");
		            int hit = rs.getInt("HIT");
		            String files = rs.getString("FILES");
		            String content = rs.getString("CONTENT");

		            
		            Notice notice = new Notice(
		                  id,
		                  title,
		                  writerId,
		                  regdate,
		                  hit,
		                  files,
		                  content
		                  );
		               
		            list.add(notice); //
		         }
		            
		            rs.close();
		            st.close();
		            conn.close();
		         } catch (ClassNotFoundException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		         } catch (SQLException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		         }
		         
		      return list;
		   }
	   public Notice getNotice(int id) {
		      String sql = "SELECT * FROM NOTICE WHERE ID=?";

		      return null;
		   }
		   
		   /*
		   public Notice getNextNotice(int id) {
		      return null;
		   }
		   
		   public Notice getPrevNotice(int id) {
		      return null;
		   }
		   */
		   public int deleteNoticeAll(int[] ids) {

		      return 0;
		   }

		
		   
		}