package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.connection.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {

        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstm = null; //PreparedStatement는 Statement를 상속받음. 그래서 기능이 더 많음. 들어가보면 extends Statement

        try {
            con = getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1, member.getMemberId());
            pstm.setInt(2, member.getMoney());
            pstm.executeUpdate();

            return member;
        } catch (SQLException e) {
            log.info("SQLException = {}", e);
            throw e;
        } finally {
            close(con, pstm, null);
        }
    }

    public Member findById(String memberId) throws SQLException {

        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery(); //select 는 executeQuery
            if (rs.next()) { //처음 한번은 next 해줘야 함. 그 다음이 데이터임.
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, rs);
        }

    }


    //Statement는 sql을 그대로 넣음. PreparedStatement는 파라미터를 바인딩 하는거임.
    private void close(Connection con, Statement stmt, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error={}", e); //로그남기고 끝. 어떻게 처리할 수 있는게 없기 때문에.
            }
        }

        if (stmt != null) {
            try {
                stmt.close(); //Statement로 받을 수 있음. PreparedStatement가 기능이 더 많음.
            } catch (SQLException e) {
                log.info("error={}", e); //로그남기고 끝. 어떻게 처리할 수 있는게 없기 때문에.
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error={}", e); //로그남기고 끝. 어떻게 처리할 수 있는게 없기 때문에.
            }
        }


    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

}
