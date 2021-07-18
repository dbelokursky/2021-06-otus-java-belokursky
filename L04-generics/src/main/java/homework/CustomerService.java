package homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private final TreeMap<Customer, String> customers;

    public CustomerService() {
        customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return new AbstractMap.SimpleImmutableEntry<>(customers.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> next = customers.higherEntry(customer);
        return next != null ? new AbstractMap.SimpleImmutableEntry<>(next) : null; // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);

    }
}
