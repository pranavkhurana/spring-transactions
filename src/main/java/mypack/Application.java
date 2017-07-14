//create database springDemo; use springDemo;
//create table user( id int(4) Not null auto_increment, name varchar(50), balance int(10), primary key(id) );
//create table account_transaction ( id int(5) not null auto_increment, sender varchar(50), receiver varchar(50), balance_transferred int(6), primary key(id) );
package mypack;
import entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.AccountService;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-trancaction-config.xml");
        AccountService accountService=applicationContext.getBean("accountService", AccountService.class);

        //Solution 2

        //Create
        accountService.addUser(new User("John",10000));
        accountService.addUser(new User("Peter",5000));
        accountService.addUser(new User("Jane",15000));

        //Read
        List<User> list=accountService.getAll();
        list.forEach(System.out::println);

        //Update
        int listLength=list.size();
        User updatedUser=list.get(listLength-1);
        updatedUser.setName("new_name");
        accountService.update(updatedUser);

        //Delete
        accountService.deleteUser(list.get(listLength-2).getId());
        System.out.println("deleted user "+list.get(listLength-2).getName());

        //Solution 4 and 6
        accountService.transfer(list.get(listLength-1),list.get(listLength-3),1000);

    }
}