//package testing;
//
//import server.context.ServiceContext;
//import server.domain.User;
//import server.service.impl.hibernate.AdminServiceHibernateImpl;
//
//public class AdminServiceHibernateImplTesting {
//    public static void main(String[] args){
//        AdminServiceHibernateImpl adminServiceHibernate = ServiceContext.getAdminServiceHibernate();
//
//        User user3  = new User("Umer","doctor","user2","user123");
//        System.out.println(user3);
//        System.out.println(adminServiceHibernate.addUser(user3));
//
//
//        User user4  = new User("Umer","doctor","user3","user123");
//        System.out.println(user3);
//        System.out.println(adminServiceHibernate.addUser(user4));
//
//        User user5 = new User("Tom","doctor","user3","user123");
//        System.out.println(adminServiceHibernate.addUser(user4)); // if we add entirely same user that is already added it returns true
//        System.out.println(adminServiceHibernate.addUser(user5));
//
//        System.out.println(adminServiceHibernate.setUserAccountStatus("user-1",true));
//        System.out.println(adminServiceHibernate.setUserAccountStatus("user3",true));
//    }
//}
