package com.hmy.myapp.member.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.hmy.myapp.common.JDBC;
import com.hmy.myapp.member.MemberVO;

@Repository("memDAO")
public class MemberDAO {

	private Connection conn=null;
	private PreparedStatement pstmt=null;
	
	public void insertMember(MemberVO vo) {
		String sql="insert into member values((select NVL(MAX(num)+1, 0) from member), ?, ?, ?, ?, 0)";
	    System.out.println("insertMember() 수행중");
	    conn=JDBC.getConnection();
	    try {
	       pstmt=conn.prepareStatement(sql);
	       pstmt.setString(1, vo.getId());
	       pstmt.setString(2, vo.getPw());
	       pstmt.setString(3, vo.getName());
	       pstmt.setString(4, vo.getAddress());
	       pstmt.executeUpdate();
	    } catch (SQLException e) {
	       e.printStackTrace();
	    }
	    finally {
	       JDBC.close(conn, pstmt);
	    }
	}
	
	public MemberVO getMember(MemberVO vo) {
	      String sql="select * from member where id=? and pw=?";
	      System.out.println("getMember() 수행중");
	      MemberVO data=null;
	      conn=JDBC.getConnection();
	      try {
	         pstmt=conn.prepareStatement(sql);
	         pstmt.setString(1, vo.getId());
	         pstmt.setString(2, vo.getPw());
	         ResultSet rs=pstmt.executeQuery();
	         if(rs.next()) {
	            data=new MemberVO();
	            data.setNum(rs.getInt("num"));
	            data.setId(rs.getString("id"));
	            data.setPw(rs.getString("pw"));
	            data.setName(rs.getString("name"));
	            data.setAddress(rs.getString("address"));
	            data.setPoint(rs.getInt("point"));
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      finally {
	         JDBC.close(conn, pstmt);
	      }
	      return data;
	   }
	}
