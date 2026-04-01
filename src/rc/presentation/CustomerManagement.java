package rc.presentation;

import rc.business.CustomerBusiness;
import rc.entity.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class CustomerManagement {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        CustomerBusiness business=CustomerBusiness.getInstance();
        boolean isRunning=true;

        while (isRunning){
            try{
                System.out.println("\n===============QUAN LY KHACH HANG===============");
                System.out.println("1. Hien thi danh sach khach hang");
                System.out.println("2. Them khach hang");
                System.out.println("3. Cap nhat khach hang theo ID");
                System.out.println("4. Xoa khach hang theo ID");
                System.out.println("5. Tim kiem khach hang theo ten");
                System.out.println("6. Loc theo loai khach hang");
                System.out.println("7. Sap xep tang dan theo ten khach hang");
                System.out.println("8. Thoat");
                System.out.println("Lua chon cua ban: ");
                int choice=Integer.parseInt(scanner.nextLine().trim());
                switch(choice){
                    case 1:
                        business.displayAllCustomers();
                        break;

                    case 2:
                        do {
                            System.out.println("Them moi khach hang");
                            Customer newCustomer = new Customer();
                            newCustomer.inputData(scanner);
                            business.addCustomer(newCustomer);
                            System.out.println("Ban co muon them tiep khong? (Y/N): ");
                        } while (scanner.nextLine().trim().equalsIgnoreCase("Y"));
                        break;

                    case 3:
                        System.out.println("Nhap ma khach hang muon cap nhat: ");
                        String updateId=scanner.nextLine().trim();
                        Optional<Customer> customerOpt=business.findById(updateId);
                        if(customerOpt.isPresent()){
                            Customer c=customerOpt.get();
                            System.out.println("Da tim thay khach hang");

                            System.out.println("Ten Khach hang: ");
                            String newName=scanner.nextLine().trim();

                            if(!newName.isEmpty()) c.setCustomerName(newName);

                            System.out.println("Email: ");
                            String newEmail=scanner.nextLine().trim();

                            if(!newEmail.isEmpty()){
                                if(business.isExistedEmail(newEmail) && !newEmail.equalsIgnoreCase(c.getEmail())){
                                    System.err.println("-> Email da ton tai (Skipped)");
                                }
                                else{
                                    if(newEmail.contains("@") && newEmail.contains(".")) c.setEmail(newEmail);
                                    else System.err.println("Sai dinh dang email");
                                }
                            }

                            System.out.println("SDT: ");
                            String newPhone=scanner.nextLine().trim();
                            if(!newPhone.isEmpty()){
                                if(newPhone.matches("^0\\d{9,10}$")) c.setPhone(newPhone);
                            }

                            System.out.println("Loai KH (Ca nhan/ Doanh nghiep/ Uu dai: ");
                            String newType=scanner.nextLine().trim();
                            if(!newType.isEmpty()) c.setCustomerType(newType);

                            System.out.println("Ngay dang ky (dd/MM/yyyy): ");
                            String newDate=scanner.nextLine().trim();
                            if(!newDate.isEmpty()){
                                try{
                                    LocalDate date=LocalDate.parse(newDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                    if(!date.isAfter(LocalDate.now())) c.setRegistrationDate(date);
                                    else System.err.println("->Ngay khong hop le");
                                } catch (DateTimeParseException e) {
                                    System.err.println("->Sai dinh dang ngay");
                                }
                            }
                            business.updateCustomer(c);
                        } else{
                            System.err.println("->Ma KH khong ton tai");
                        }
                        break;

                    case 4:
                        System.out.println("\nNhap Ma KH can xoa: ");
                        String deleteId=scanner.nextLine().trim();

                        Optional<Customer> delOpt=business.findById(deleteId);
                        if(delOpt.isEmpty()){
                            System.err.println("->Ma KH khong ton tai");
                        }
                        else{
                            business.deleteCustomer(deleteId);
                        }
                        break;

                    case 5:
                        System.out.println("Nhap ten KH can tim: ");
                        String searchName=scanner.nextLine().trim();

                        business.searchByName(searchName);
                        break;

                    case 6:
                        System.out.println("Nhap loai KH can loc (Ca nhan/ Doanh nghiep/ Uu dai): ");
                        String filterType=scanner.nextLine().trim();
                        business.filterByType(filterType);
                        break;

                    case 7:
                        business.sortByNameAsc();
                        break;

                    case 8:
                        System.out.println("Tam biet");
                        isRunning=false;
                        break;
                    default:
                        System.err.println("->Lua chon ko hop le");
                }
            } catch(NumberFormatException e) {
                System.err.println("->Loi nhap lieu (Chi nhap so)");
            }
              catch(Exception e){
                System.err.println("->Loi he thong: "+ e.getMessage());
              }
        }
        scanner.close();
    }
}
