package server.context;
import server.dao.impl.database.AppointmentV1RepoDbImpl;
import server.dao.impl.database.SlotRepoDbImpl;
import server.dao.impl.database.UserRepoDbImpl;

import server.dao.impl.hibernate.SlotRepoHibernateImpl;


public class RepositoryContext {

    private static UserRepoDbImpl userRepoDb;
    private static SlotRepoDbImpl slotRepoDb;
    private static AppointmentV1RepoDbImpl appointmentV1RepoDb;


    private static SlotRepoHibernateImpl slotRepoHibernate;


    private RepositoryContext(){
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








}

