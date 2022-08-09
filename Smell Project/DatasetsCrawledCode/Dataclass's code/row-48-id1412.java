public interface Customer583Repository extends CrudRepository<Customer583, Long> {


 List<Customer583> findByLastName(String lastName);
}