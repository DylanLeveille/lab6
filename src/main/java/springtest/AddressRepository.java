package springtest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "addressbook", path = "addressbook")
public interface AddressRepository extends PagingAndSortingRepository<AddressBook, Long> {

   // List<AddressBook> findByName(BuddyInfo bookOwner);
}