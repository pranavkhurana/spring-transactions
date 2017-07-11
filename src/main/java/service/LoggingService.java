package service;

import dao.LoggingDao;
import entity.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class LoggingService {

    private LoggingDao loggingDao;

    @Transactional(propagation = Propagation.NESTED)
    public void logTransaction(User sender,User receiver,int amount){
        System.out.println("logging in transaction table");
        loggingDao.log(sender,receiver,amount);
        throw new RuntimeException();
    }

    public void setLoggingDao(LoggingDao loggingDao) {
        this.loggingDao = loggingDao;
    }

    public LoggingDao getLoggingDao() {
        return loggingDao;
    }
}
