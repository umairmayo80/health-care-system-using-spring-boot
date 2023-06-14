public class Main {
    public static void main(String[] args){
        System.out.println("ok");

//        User user = new User("Umair");
//        System.out.println(user);

        Admin admin = new Admin("A2","admin123","admin123");
        System.out.println(admin);




        admin.addUser(admin);

        User user = new User(-1,"test user","user","user123","user123",true);

        admin.addUser(user);

        admin.viewUsers();

    }


}
