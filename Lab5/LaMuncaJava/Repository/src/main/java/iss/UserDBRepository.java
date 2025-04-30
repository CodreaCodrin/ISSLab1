package iss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserDBRepository extends AbstractDBRepository<Integer, User> implements UserRepository {
    public UserDBRepository(Properties props) {
        super(props);
    }

    @Override
    User getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        String username = resultSet.getString("username");
        String type = resultSet.getString("type");

        return switch (type) {
            case "BOSS" -> new Boss(id, username, "", type);
            case "WORKER" -> new Worker(id, username, "", type);
            default -> throw new SQLException("Invalid user type: " + type);
        };
    }

    @Override
    String getSelectAllQuery() {
        return "select * from Users";
    }

    @Override
    String getSelectOneQuery(Integer integer) {
        logger.trace("Sql select querry: 'select * from Users where user_id = {}'", integer);
        return String.format("select * from Users where user_id = %d", integer);
    }

    @Override
    String getInsertQuery(User entity) {
        return String.format("insert into Users (user_id, username, password, type) values (%d, '%s', '%s', '%s')",
                entity.getId(), entity.getUsername(), entity.getPassword(), entity.getUserType());
    }

    @Override
    String getDeleteQuery(Integer integer) {
        return "";
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        logger.traceEntry("finding user with username {} and password {}", username, password);
        Connection con = dbUtils.getConnection();
        User entity = null;

        try(PreparedStatement preStmt = con.prepareStatement(
                "select * from Users where username = '" + username + "' and password = '" + password + "'"
        )){
            try(ResultSet rs = preStmt.executeQuery()){
                while(rs.next())
                    entity = getEntityFromResultSet(rs);
            }
        } catch (SQLException ex)
        {
            logger.error(ex);
            System.err.println(ex.getMessage());
        }

        logger.traceExit();
        return entity;
    }
}
