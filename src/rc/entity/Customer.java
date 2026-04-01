package rc.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Customer {
    private String customerId;
    private String customerName;
    private String email;
    private String phone;
    private String customerType;
    private LocalDate registrationDate;

    public Customer(){
    }

    public Customer(String customerId, String customerName, String email, String phone, String customerType, LocalDate registrationDate){
        this.customerId=customerId;
        this.customerName=customerName;
        this.email=email;
        this.phone=phone;
        this.customerType=customerType;
        this.registrationDate=registrationDate;
    }

    public String getCustomerId(){return customerId;}

    public void setCustomerId(String customerId){this.customerId=customerId;}

    public String getCustomerName(){return customerName;}

    public void setCustomerName(String customerName){this.customerName=customerName;}

    public String getEmail(){return email;}

    public void setEmail(String email){this.email=email;}

    public String getPhone(){return phone;}

    public void setPhone(String phone){this.phone=phone;}

    public String getCustomerType(){return customerType;}

    public void setCustomerType(String customerType){this.customerType=customerType;}

    public LocalDate getRegistrationDate(){return registrationDate;}

    public void setRegistrationDate(LocalDate registrationDate){this.registrationDate=registrationDate;}

    public void inputData(Scanner scanner){
        while(true){
            System.out.println("Nhap ma khach hang (Dinh dang: CXXX VD:C001, C002):");
            this.customerId=scanner.nextLine().trim();
            if(this.customerId.matches("^C\\d{3}$")) break;
            System.err.println("Loi: Dinh dang ma khach hang khong hop le");
        }

        while(true){
            System.out.println("Nhap ten khach hang: ");
            this.customerName=scanner.nextLine().trim();
            if(!this.customerName.isEmpty()) break;
            System.err.println("Ten khach hang khong duoc de trong");
        }

        while(true){
            System.out.println("Nhap email:");
            this.email=scanner.nextLine().trim();
            if(this.email.contains("@") && this.email.contains(".")) break;
            System.err.println("Loi dinh dang email khong hop le");
        }

        while(true){
            System.out.println("Nhap SDT:");
            this.phone=scanner.nextLine().trim();
            if(this.phone.matches("^0\\d{9,10}$")) break;
            System.err.println("SDT khong hop le");
        }

        while(true){
            System.out.println("Nhap loai khach hang (Ca nhan/ Doanh nghiep/ Uu dai:");
            this.customerType=scanner.nextLine().trim();
            if      (this.customerType.equalsIgnoreCase("Ca nhan")
                    || this.customerType.equalsIgnoreCase("Doanh nghiep")
                    || this.customerType.equalsIgnoreCase("Uu dai"))
            {
                this.customerType=this.customerType.substring(0,1).toUpperCase()+this.customerType.substring(1).toLowerCase();
                break;
            }
            System.err.println("Loai khach hang khong hop le");
        }

        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while(true){
            System.out.println("Nhap ngay dang ky (dd/MM/yyyy): ");
            String dateStr=scanner.nextLine().trim();
            try{
                this.registrationDate=LocalDate.parse(dateStr,formatter);
                if (!this.registrationDate.isAfter(LocalDate.now())) {
                    break;
                }
                else{
                    System.err.println("-> Loi: Ngay dang ky khong dung");
                }
            } catch (DateTimeParseException e){
                System.err.println("-> Loi: Sai dinh dang ngay");
            }
        }
    }

    public void displayData(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("| %-10s | %-20s | %-25s | %-15s | %-15s | %-15s |\n", customerId, customerName, email, phone, customerType, registrationDate);
    }
}
