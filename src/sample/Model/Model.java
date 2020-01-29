package sample.Model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class Model {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Object object;


    public Model() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("database");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void addService(String name, Long time, BigDecimal price){
        entityManager=entityManagerFactory.createEntityManager();
        Service service=new Service(name,price,time);
        entityManager.getTransaction().begin();
        entityManager.persist(service);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void removeService(Long id) throws Exception{
        entityManager=entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Service service=entityManager.find(Service.class,id);
            entityManager.remove(service);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new Exception();

        }
        finally {
            entityManager.close();
        }


    }
    public void updateService(Long id,String name){
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Service service=entityManager.find(Service.class,id);
        service.setName(name);
        entityManager.merge(service);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    public List<AppointmentObject> getAppointments(Long beuticanId, java.util.Date date1){
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Object[]> appointments= entityManager.createQuery(" SELECT a.id,a.timeFrom,a.timeTo,s.name as name,concat(c.firstName, ' ', c.familyName) as Klient,s.time from Appointment a" +
                " join  Beutican b on a.beuticanId=b.id" +
                " join Customer c on c.id=a.customerId" +
                " join Service s on a.serviceId=s.id" +
                " where a.beuticanId=:id  and a.date=:date" +
                " order by a.timeFrom asc")
                .setParameter("date",date1)
                .setParameter("id",beuticanId)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        List<AppointmentObject> appointments1=new ArrayList<>();
        for(int i=0;i<appointments.size();i++) {
            Object[] objects = appointments.get(i);
            AppointmentObject appointmentObject=new AppointmentObject( String.valueOf(objects[0]), String.valueOf(objects[1]), String.valueOf(objects[2]), String.valueOf(objects[3]), String.valueOf(objects[4]),String.valueOf(objects[5]));
            appointments1.add(appointmentObject);
        }


        return appointments1;
    }
    public List<Customer> getCustomers() {
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Customer> customers= entityManager.createQuery(" SELECT c from Customer c",Customer.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return customers;
    }
    public List<Beutican> getBeuticans() {
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Beutican> beuticans= entityManager.createQuery(" SELECT c from Beutican c",Beutican.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return beuticans;
    }
    public List<Service> getServices() {
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Service> services= entityManager.createQuery(" SELECT c from Service c",Service.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return services;
    }
    public void addAppointment( Long customerId, Long serviceId, Long beuticanId, Time timeFrom, Date date){
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Service service=entityManager.find(Service.class,serviceId);
        Time timeTo=new Time(timeFrom.getHours(),timeFrom.getMinutes()+(service.getTime()).intValue(),00);
        Appointment appointment=new Appointment(serviceId, customerId, beuticanId,  timeFrom,timeTo, date);
        entityManager.persist(appointment);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void removeAppointment (Long id) throws Exception{

        entityManager=entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Appointment appointment=entityManager.find(Appointment.class,id);
            entityManager.remove(appointment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new Exception();

        }
        finally {
            entityManager.close();
        }
    }
    public void updateAppointment(Long id,Time timeFrom, Date date,Long beuticanId){
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Appointment appointment=entityManager.find(Appointment.class,id);
        appointment.setBeuticanId(beuticanId);
        appointment.setTimeFrom(timeFrom);
        appointment.setDate(date);
        entityManager.merge(appointment);
        entityManager.getTransaction().commit();
        entityManager.close();

    }
    public void addCustomer(String firstName, String familyName, String phoneNumber, String email){
        entityManager=entityManagerFactory.createEntityManager();
        Customer customer=new Customer(firstName,familyName, phoneNumber,email);
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void removeCustomer(Long id)throws Exception{
        entityManager=entityManagerFactory.createEntityManager();
             try {

                 entityManager.getTransaction().begin();
                 Customer customer = entityManager.find(Customer.class, id);
                 entityManager.remove(customer);
                 entityManager.getTransaction().commit();
             } catch (Exception e) {
                 entityManager.getTransaction().rollback();
                 throw new Exception();

             }
             finally {
                 entityManager.close();
             }



    }
    public void updateCustomer(Long id,String firstName, String familyName, String phoneNumber, String  email){
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Customer customer=entityManager.find(Customer.class,id);
        if(!email.isEmpty())
            customer.setEmail(email);
        if(!familyName.isEmpty())
            customer.setFamilyName(familyName);
        if(!firstName.isEmpty())
            customer.setFirstName(firstName);
        if(!phoneNumber.isEmpty())
            customer.setPhoneNumber(phoneNumber);
        entityManager.merge(customer);
        entityManager.getTransaction().commit();
        entityManager.close();

    }
    public void addBeutican(String firstName, String familyName, String phoneNumber, String email){
        entityManager=entityManagerFactory.createEntityManager();
        Beutican beutican=new Beutican(firstName,familyName, phoneNumber,email);
        entityManager.getTransaction().begin();
        entityManager.persist(beutican);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void removeBeutican(Long id){
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Beutican beutican=entityManager.find(Beutican.class,id);
        entityManager.remove(beutican);
        entityManager.getTransaction().commit();
        entityManager.close();

    }
    public void updateBeutican(Long id,String firstName, String familyName, String phoneNumber, String  email){
        entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Beutican beutican=entityManager.find(Beutican.class,id);
        if(!email.isEmpty())
           beutican.setEmail(email);
        if(!familyName.isEmpty())
            beutican.setFamilyName(familyName);
        if(!firstName.isEmpty())
            beutican.setFirstName(firstName);
        if(!phoneNumber.isEmpty())
            beutican.setPhoneNumber(phoneNumber);
        entityManager.merge(beutican);
        entityManager.getTransaction().commit();
        entityManager.close();

    }


    public void close(){
        entityManager.close();
        entityManagerFactory.close();
    }
}
