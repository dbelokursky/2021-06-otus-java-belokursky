package homework;


import java.util.*;

public class CustomerService {

    private final NavigableMap<Customer, String> customers;

    public CustomerService() {
        customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> smallest = customers.firstEntry();
        return new AbstractMap.SimpleImmutableEntry<>(new Customer(smallest.getKey()), smallest.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> next = customers.higherEntry(customer);
        return next != null ? new AbstractMap.SimpleImmutableEntry<>(new Customer(next.getKey()), next.getValue()) : null;
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
