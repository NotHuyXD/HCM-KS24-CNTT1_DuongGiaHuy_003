package rc.business;

import rc.entity.Customer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerBusiness {
    private static CustomerBusiness instance;
    private List<Customer> customerList;

    private CustomerBusiness(){
        customerList=new ArrayList<>();
    }

    public static CustomerBusiness getInstance(){
        if(instance==null){
            instance=new CustomerBusiness();
        }
        return instance;
    }

    public Optional<Customer> findById(String id){
        return customerList.stream().filter(c->c.getCustomerId().equalsIgnoreCase(id)).findFirst();
    }

    public boolean isExistedEmail(String email){
        return customerList.stream().anyMatch(c->c.getEmail().equalsIgnoreCase(email));
    }

    public void displayAllCustomers(){
        if(customerList.isEmpty()){
            System.err.println("Danh sach khach hang hien dang trong");
            return;
        }
        printTableHeader();
        customerList.forEach(Customer::displayData);
        printTableFooter();
    }

    public void addCustomer (Customer newCustomer){
        if(findById(newCustomer.getCustomerId()).isPresent() || isExistedEmail(newCustomer.getEmail())){
            System.err.println("-> Loi: ID hoac Email da ton tai");
        }
        else {
            customerList.add(newCustomer);
            System.out.println("-> Them khach hang thanh cong");
        }
    }

    public void updateCustomer(Customer updatedCustomer){
        System.out.println("Cap nhat thong tin thanh cong");
    }

    public void deleteCustomer(String id){
        Optional<Customer> customerOpt= findById(id);

        if(customerOpt.isPresent()){
            customerList.removeIf(c->c.getCustomerId().equalsIgnoreCase(id));
            System.out.println("Xoa khach hang thanh cong");
        }
        else{
            System.err.println("ID khong ton tai");
        }
    }

    public void searchByName(String name){
        List<Customer> result = customerList.stream().filter(c->c.getCustomerName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        if(result.isEmpty()){
            System.err.println("->Khong tim thay khach hang");
        }
        else{
            System.out.println("->Tim thay "+ result.size() + " khach hang: ");
            printTableHeader();
            result.forEach(Customer::displayData);
            printTableFooter();
        }
    }

    public void filterByType(String type){
        List<Customer> result=customerList.stream().filter(c->c.getCustomerType().equalsIgnoreCase(type)).collect(Collectors.toList());
        if(result.isEmpty()){
            System.err.println("-> Khong tim thay khach hang cung loai");
        }
        else{
            printTableHeader();
            result.forEach(Customer::displayData);
            printTableFooter();
        }
    }

    public void sortByNameAsc(){
        if(customerList.isEmpty()){
            System.err.println("-> Danh sach trong");
            return;
        }
        customerList.sort(Comparator.comparing(Customer::getCustomerName));
        System.out.println("Sap xep thanh cong");
    }

    public void printTableHeader(){
        System.out.println(new String(new char[118]).replace("\0","-"));
        System.out.printf("| %-10s | %-20s | %-25s | %-15s | %-15s | %-15s |\n", "MA KH", "TEN KH", "EMAIL", "SDT", "LOAI KH", "NGAY DANG KY");
        System.out.println(new String(new char[118]).replace("\0","-"));
    }

    public void printTableFooter(){
        System.out.println(new String(new char[118]).replace("\0","-"));
    }
}
