package iss;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import iss.Utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class AbstractDBRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    protected final JdbcUtils dbUtils;

    protected static final Logger logger = LogManager.getLogger();

    public AbstractDBRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    abstract E getEntityFromResultSet(ResultSet resultSet) throws SQLException;
    abstract String getSelectAllQuery();
    abstract String getSelectOneQuery(ID id);
    abstract String getInsertQuery(E entity);
    abstract String getDeleteQuery(ID id);

    @Override
    public Iterable<E> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<E> entities = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement(getSelectAllQuery())){
            try(ResultSet rs = preStmt.executeQuery()){
                while(rs.next())
                    entities.add(getEntityFromResultSet(rs));
            }
        } catch (SQLException ex)
        {
            logger.error(ex);
            System.err.println(ex.getMessage());
        }

        logger.traceExit();
        return entities;
    }

    @Override
    public E findOne(ID id) {
        logger.traceEntry("finding one with id {}", id);
        Connection con = dbUtils.getConnection();
        E entity = null;

        try(PreparedStatement preStmt = con.prepareStatement(getSelectOneQuery(id))){
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

    @Override
    public void save(E entity) {
        logger.traceEntry("saving entity {}", entity);
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement(getInsertQuery(entity))){
            logger.trace("saved {} instances", preStmt.executeUpdate());
        } catch (SQLException ex)
        {
            logger.error(ex);
            System.err.println(ex.getMessage());
        }

        logger.traceExit();
    }

    @Override
    public E delete(ID id) {
        return null;
    }
}
