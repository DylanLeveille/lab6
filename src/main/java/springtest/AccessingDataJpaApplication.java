package springtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

@SpringBootApplication
public class AccessingDataJpaApplication {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class);
    }

    @Bean
    public CommandLineRunner demo(BuddyRepository repository, AddressRepository repository2) {
        return (args) -> {
            // save a few buddies

            BuddyInfo owner = new BuddyInfo("Dave");
            AddressBook book = new AddressBook(owner);
            BuddyInfo buddy1 = new BuddyInfo("Jack");
            BuddyInfo buddy2 = new BuddyInfo("Paul");
            BuddyInfo buddy3 = new BuddyInfo("Bob");

            //repository.save(buddy1);
            //repository.save(buddy2);
            //repository.save(buddy3);
            //repository.save(owner);
            book.addBuddy(buddy1);
            book.addBuddy(buddy2);
            book.addBuddy(buddy3);

            repository2.save(book);

            // fetch all customers
            log.info("Entities found with findAll():");
            log.info("-------------------------------");
            for (Object item : repository.findAll()) {
                log.info(item.toString());
            }
            for (Object item : repository2.findAll()) {
                log.info(item.toString());
            }
            log.info("");

            // fetch an individual customer by ID
//            Customer customer = repository.findById(1L);
//            log.info("Customer found with findById(1L):");
//            log.info("--------------------------------");
//            log.info(customer.toString());
//            log.info("");
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            repository.findByLastName("Bauer").forEach(bauer -> {
//                log.info(bauer.toString());
//            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            //  log.info(bauer.toString());
            // }
            log.info("");
        };
    }

}