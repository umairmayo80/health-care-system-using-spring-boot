package server.context;
import server.dao.impl.database.AppointmentV1RepoDbImpl;
import server.dao.impl.database.SlotRepoDbImpl;
import server.dao.impl.database.UserRepoDbImpl;
import server.dao.impl.fileSystem.AppointmentV1RepoFileImpl;
import server.dao.impl.fileSystem.SlotRepoFileImpl;
import server.dao.impl.fileSystem.UserRepoFileImpl;
import server.dao.impl.hibernate.AppointmentRepoHibernate;
import server.dao.impl.hibernate.SlotRepoHibernateImpl;
import server.dao.impl.hibernate.UserRepoHibernateImpl;

public class RepositoryContext {

    private static UserRepoFileImpl userRepoFile;
    private static SlotRepoFileImpl slotRepoFile;
    private static AppointmentV1RepoFileImpl appointmentV1RepoFile;

    private static UserRepoDbImpl userRepoDb;
    private static SlotRepoDbImpl slotRepoDb;
    private static AppointmentV1RepoDbImpl appointmentV1RepoDb;


    private static UserRepoHibernateImpl userRepoHibernate;
    private static SlotRepoHibernateImpl slotRepoHibernate;
    private static AppointmentRepoHibernate appointmentRepoHibernate;

    private RepositoryContext(){
    }


    public static UserRepoFileImpl getUserRepoFile(){
        if(userRepoFile == null){
            userRepoFile = new UserRepoFileImpl();
        }
        return userRepoFile;
    }

    public static SlotRepoFileImpl getSlotRepoFile(){
        if(slotRepoFile == null){
            slotRepoFile = new SlotRepoFileImpl();
        }
        return slotRepoFile;
    }

    public static AppointmentV1RepoFileImpl getAppointmentV1RepoFile(){
        if(appointmentV1RepoFile == null){
            appointmentV1RepoFile = new AppointmentV1RepoFileImpl();
        }
        return appointmentV1RepoFile;
    }


    public static UserRepoDbImpl getUserRepoDb(){
        if(userRepoDb == null){
            userRepoDb = new UserRepoDbImpl();
        }
        return userRepoDb;
    }

    public static SlotRepoDbImpl getSlotRepoDb(){
        if(slotRepoDb == null){
            slotRepoDb = new SlotRepoDbImpl() ;
        }
        return slotRepoDb;
    }

    public static AppointmentV1RepoDbImpl getAppointmentV1RepoDb() {
        if(appointmentV1RepoDb == null){
            appointmentV1RepoDb = new AppointmentV1RepoDbImpl() ;
        }
        return appointmentV1RepoDb;
    }

    public static UserRepoHibernateImpl getUserRepoHibernate() {
        if(userRepoHibernate == null){
            userRepoHibernate = new UserRepoHibernateImpl();
        }
        return userRepoHibernate;
    }

    public static SlotRepoHibernateImpl getSlotRepoHibernate() {
        if(slotRepoHibernate == null){
            slotRepoHibernate = new SlotRepoHibernateImpl();
        }
        return slotRepoHibernate;
    }

    public static AppointmentRepoHibernate getAppointmentRepoHibernate() {
        if(appointmentRepoHibernate == null){
            appointmentRepoHibernate = new AppointmentRepoHibernate() ;
        }
        return appointmentRepoHibernate;
    }




}

