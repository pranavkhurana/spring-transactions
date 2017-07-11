package dao;

import entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Transactional
    public int save(User user){
        String query="insert into user(name,balance) values('"+user.getName()+"','"+user.getBalance()+"')";
        return jdbcTemplate.update(query);
    }

    @Transactional
    public int update(User user){
        String query="update user set name='"+user.getName()+"',balance='"+user.getBalance()+"' where id='"+user.getId()+"'";
        return jdbcTemplate.update(query);
    }

    @Transactional
    public int delete(int id){
        String query="delete from user where id='"+id+"'";
        return jdbcTemplate.update(query);
    }

    @Transactional
    public List<User> getAllUsers(){
        return jdbcTemplate.query("select * from user",new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user=new User(rs.getInt(1),rs.getString(2),rs.getInt(3));
                return user;
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean transfer(User sender,User receiver,int amount){
        String query="update user set balance ='"+(sender.getBalance()-amount)+"' where id='"+sender.getId()+"'";
        String query2="update user set balance ='"+(receiver.getBalance()+amount)+"' where id='"+receiver.getId()+"'";
        int result=jdbcTemplate.update(query);
        int result2=jdbcTemplate.update(query2);
        if(result>0 && result2>0)
            return true;
        return false;
    }
}
