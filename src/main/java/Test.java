import org.example.couponjpaproject.job.CouponExpirationDailyJob;
import org.example.couponjpaproject.login_manager.ClientType;
import org.example.couponjpaproject.login_manager.LoginManager;
import org.example.couponjpaproject.beans.Category;
import org.example.couponjpaproject.beans.Company;
import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.beans.Customer;
import org.example.couponjpaproject.services.TestServices;
import org.example.couponjpaproject.services.CustomerServices;
import org.example.couponjpaproject.services.AdminServices;
import org.example.couponjpaproject.services.CompanyServices;
import org.example.couponjpaproject.services.exceptions.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class Test{ //implements CommandLineRunner

    private CouponExpirationDailyJob job;
    private LoginManager loginManager;
    private TestServices testServices;

    public Test(CouponExpirationDailyJob job, LoginManager loginManager, TestServices testServices) {
        this.job = job;
        this.loginManager = loginManager;
        this.testServices = testServices;
    }


//    @Override
//    public void run(String... args) {
//        // go through admin service again
//        // need to check all the delete methods and how they work by accociation
//        try {
//            banner();
//            testAdmin();
//            testCompany();
//            testCustomer();
//            Thread t = new Thread(job);
//            jobObjects();
//            t.start();
//            job.stop();
//            testServices.dropSchema();
//        } catch (RuntimeException | CouponIsExpiredException | CompanyMayNotExistException |
//                 CompanyAlreadyExistsException | OwnedCouponException | CouponMayAlreadyExistException |
//                 CustomerMayNotExistException | CustomerAlreadyExistsException | CouponMayNotExistException e) {
//            testServices.dropSchema();
//            System.out.println(e.getMessage());
//        }
//
//    }

    public void testAdmin() throws CompanyAlreadyExistsException, CompanyMayNotExistException, CustomerAlreadyExistsException, CustomerMayNotExistException {

        AdminServices adminServices = (AdminServices) loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
        System.out.println("  ______          __     ___       __          _       ______                     __   \n" +
                " /_  __/__  _____/ /_   /   | ____/ /___ ___  (_)___  / ____/___ __________ _____/ /__ \n" +
                "  / / / _ \\/ ___/ __/  / /| |/ __  / __ `__ \\/ / __ \\/ /_  / __ `/ ___/ __ `/ __  / _ \\\n" +
                " / / /  __(__  ) /_   / ___ / /_/ / / / / / / / / / / __/ / /_/ / /__/ /_/ / /_/ /  __/\n" +
                "/_/  \\___/____/\\__/  /_/  |_\\__,_/_/ /_/ /_/_/_/ /_/_/    \\__,_/\\___/\\__,_/\\__,_/\\___/ \n" +
                "                                                                                       ");
        System.out.println("************************************************************");
        System.out.println("Create company test");
        Company company1 = new Company("Coca Cola", "cc@cc.com", "123456");
        adminServices.addCompany(company1);
        System.out.println(adminServices.getOneCompany(1));
        // same email check
//        Company companyT1 = new Company("Coca Colla", "cc@cc.com", "123456");
//        adminServices.addCompany(companyT1);
        //same name check
        //Company companyT1 = new Company("Coca Cola", "cc55@cc.com", "123456");
        //adminServices.addCompany(companyT1);
//        System.out.println("************************************************************");
//        System.out.println("Update + getOneCompany companies test");
        //Set methods are interntionally not hard coded, so we'll check with a custom entry
        // #update name
//        Company companyUT1  = adminServices.getOneCompany(1);
//        companyUT1.setName("potato");
//        adminServices.updateCompany(companyUT1);
        // #updae id - it is not possible to update Query ID via java, its only possible through SQL.
        Company company2 = adminServices.getOneCompany(1);
        System.out.println(company2);
        company2.setPassword("1234567");
        adminServices.updateCompany(company2);
        System.out.println(adminServices.getOneCompany(1));
        company2.setPassword("123456");
        adminServices.updateCompany(company2);
        System.out.println(adminServices.getOneCompany(1));
        System.out.println("************************************************************");
        System.out.println("Print all companies Test");
        ArrayList<Company> companies = (ArrayList<Company>) adminServices.getAllCompanies();
        for (Company c : companies) {
            System.out.println(c);
        }
        System.out.println("************************************************************");
        System.out.println("Create customer test");
        Customer customer1 = new Customer("Ellay", "Ojer", "ellay@cc.com", "123456");
        adminServices.addCustomer(customer1);
        // #email duplicate;
        //customer1.setFirst_name("John");
        //adminServices.addCustomer(customer1);
        System.out.println("************************************************************");
        System.out.println("Update+getOneCustomer customer test");
        Customer customer2 = adminServices.getOneCustomer(1);
        System.out.println(customer2);
        customer2.setPassword("1234567");
        adminServices.updateCustomer(customer2);
        System.out.println(adminServices.getOneCustomer(1));
        customer2.setPassword("123456");
        adminServices.updateCustomer(customer2);
        System.out.println(customer2);
        System.out.println(adminServices.getOneCompany(1));
        //changing id is not possible due to it not being coded in
        // what does happen is updating another customer info which is okay.
        // nothing was said about duplicate emails after an update.
        System.out.println("************************************************************");
        System.out.println("Print all Customers Test");
        ArrayList<Customer> customers = (ArrayList<Customer>) adminServices.getAllCustomers();
        for (Customer c : customers) {
            System.out.println(c);
        }
        System.out.println("************************************************************");

    }

    public void testCompany() throws CouponMayAlreadyExistException, CouponMayNotExistException, CouponIsExpiredException {
        CompanyServices companyServices = (CompanyServices) loginManager.login("cc@cc.com", "123456", ClientType.Company);
        Company tCompany = companyServices.getCompanyDetails();
        System.out.println("  ______          __     ______                                        ______                     __   \n" +
                " /_  __/__  _____/ /_   / ____/___  ____ ___  ____  ____ _____  __  __/ ____/___ __________ _____/ /__ \n" +
                "  / / / _ \\/ ___/ __/  / /   / __ \\/ __ `__ \\/ __ \\/ __ `/ __ \\/ / / / /_  / __ `/ ___/ __ `/ __  / _ \\\n" +
                " / / /  __(__  ) /_   / /___/ /_/ / / / / / / /_/ / /_/ / / / / /_/ / __/ / /_/ / /__/ /_/ / /_/ /  __/\n" +
                "/_/  \\___/____/\\__/   \\____/\\____/_/ /_/ /_/ .___/\\__,_/_/ /_/\\__, /_/    \\__,_/\\___/\\__,_/\\__,_/\\___/ \n" +
                "                                          /_/                /____/                                    ");
        System.out.println("************************************************************");
        System.out.println("Test add coupon");
        Coupon coupon1 = new Coupon(tCompany, Category.Food, "Dominos Pizza discount", "Cheaper Pizza!",
                new Date(125, 6, 11), new Date(124, 9, 12), 5, 55.9, "dominos.jpg");
        //#duplicate title test
//        Coupon coupon1T = new Coupon(tCompany, Category.Food, "Dominos Pizza discount", "Cheaper Pizza!",
//               new Date(124, 6, 11), new Date(124, 9, 12), 5, 55.9, "dominos.jpg");
//         companyServices.addCoupon(coupon1T);
        Coupon coupon2 = new Coupon(tCompany, Category.Electricity, "Heater at 20% off", "Cheaper heater!",
                new Date(125, 6, 11), new Date(124, 9, 12), 5, 88.5, "heater.jpg");
        ;
        Coupon coupon3 = new Coupon(tCompany, Category.Food, "Hamburger at 20% off", "Cheaper Hamburger!",
                new Date(125, 6, 11), new Date(124, 9, 2), 1, 65.6, "Hamburger.jpg");
        companyServices.addCoupon(coupon1);
        companyServices.addCoupon(coupon2);
        companyServices.addCoupon(coupon3);
        System.out.println("************************************************************");
        System.out.println("Test Company coupons");
        List<Coupon> coupons = companyServices.getCompanyCoupons();
        for (Coupon c : coupons) {
            System.out.println(c);

        }
        System.out.println("************************************************************");
        System.out.println("get coupons by price >70");
        List<Coupon> couponsPrice = companyServices.getCompanyCoupons(70);
        for (Coupon c1 : couponsPrice) {
            System.out.println(c1);

        }
        System.out.println("************************************************************");
        System.out.println("Get coupons by category - Electricity");
        List<Coupon> couponsCategory = companyServices.getCompanyCoupons(Category.Electricity);
        for (Coupon c2 : couponsCategory) {
            System.out.println(c2);
        }
        System.out.println("************************************************************");
        System.out.println("Test Update Coupons");
        Coupon coupon4 = new Coupon(tCompany, Category.Food, "Dominos Pizza discount", "Cheaper Pizza!",
                new Date(125, 8, 11), new Date(124, 11, 12), 2, 555.5, "dominos.jpg");
        System.out.println("Update to: " + coupon4);
        companyServices.updateCoupon(coupon4);
        System.out.println("Call: " + testServices.getComRep().findById(1));
        System.out.println("Update to: " + coupon4);
        //Not possible to update company due to code making sure its not possible, its only possible to create
        // the same coupon for a different company.
        System.out.println("************************************************************");
        System.out.println("Test Get Company Coupons");
        List<Coupon> coupons3 = companyServices.getCompanyCoupons();
        for (Coupon c : coupons3) {
            System.out.println(c);
        }
        System.out.println("************************************************************");
        System.out.println("Test Get Company Details");
        System.out.println(companyServices.getCompanyDetails());
        System.out.println("************************************************************");


    }

    public void testCustomer() throws CouponIsExpiredException, OwnedCouponException {
        CustomerServices customerServices = (CustomerServices) loginManager.login("ellay@cc.com", "123456", ClientType.Customer);
        System.out.println("  ______          __     ______           __                            ______                     __   \n" +
                " /_  __/__  _____/ /_   / ____/_  _______/ /_____  ____ ___  ___  _____/ ____/___ __________ _____/ /__ \n" +
                "  / / / _ \\/ ___/ __/  / /   / / / / ___/ __/ __ \\/ __ `__ \\/ _ \\/ ___/ /_  / __ `/ ___/ __ `/ __  / _ \\\n" +
                " / / /  __(__  ) /_   / /___/ /_/ (__  ) /_/ /_/ / / / / / /  __/ /  / __/ / /_/ / /__/ /_/ / /_/ /  __/\n" +
                "/_/  \\___/____/\\__/   \\____/\\__,_/____/\\__/\\____/_/ /_/ /_/\\___/_/  /_/    \\__,_/\\___/\\__,_/\\__,_/\\___/ \n" +
                "                                                                                                        ");
        System.out.println("************************************************************");
        System.out.println("Test Purchase Coupon");
        Coupon coupon1 = testServices.getOneCoupon(1);
        Coupon coupon2 = testServices.getOneCoupon(2);
        Coupon coupon3 = testServices.getOneCoupon(3);
        customerServices.purchaseCoupon(coupon1);
        customerServices.purchaseCoupon(coupon2);
        customerServices.purchaseCoupon(coupon3);
        Company tCompany = testServices.getOneCompany(1);
        Coupon coupon4 = new Coupon(tCompany, Category.Food, "Dominos Pizza discount", "Cheaper Pizza!",
                new Date(124, 6, 11), new Date(124, 7, 1), 5, 55.9, "dominos.jpg");
        //duplicate coupon;
//        customerServices.purchaseCoupon(coupon1);
        // Out of Date purchase
//         customerServices.purchaseCoupon(coupon4);
        System.out.println("************************************************************");
        System.out.println("Test GetCustomersCoupons");
        Set<Coupon> c1 = customerServices.getCustomerCoupons();
        for (Coupon c : c1) {
            System.out.println(c);
        }
        System.out.println("************************************************************");
        System.out.println("Test GetCustomersCoupons By Category - Electricity");
        List<Coupon> c2 = customerServices.getCustomerCoupons(Category.Electricity);
        for (Coupon c : c2) {
            System.out.println(c);
        }
        System.out.println("************************************************************");
        System.out.println("Test GetCustomersCoupons By Max Price - > 70");
        List<Coupon> c3 = customerServices.getCustomerCoupons(70);
        for (Coupon c : c3) {
            System.out.println(c);
        }
        System.out.println("************************************************************");
        System.out.println("Test GetCustomersDetails");
        System.out.println(customerServices.getCustomerDetails());
        System.out.println("************************************************************");


    }

    public static void banner() {
        System.out.println("   ______                                      ____               _           __     ______          __ \n" +
                "  / ____/___  __  ______  ____  ____  _____   / __ \\_________    (_)__  _____/ /_   /_  __/__  _____/ /_\n" +
                " / /   / __ \\/ / / / __ \\/ __ \\/ __ \\/ ___/  / /_/ / ___/ __ \\  / / _ \\/ ___/ __/    / / / _ \\/ ___/ __/\n" +
                "/ /___/ /_/ / /_/ / /_/ / /_/ / / / (__  )  / ____/ /  / /_/ / / /  __/ /__/ /_     / / /  __(__  ) /_  \n" +
                "\\____/\\____/\\__,_/ .___/\\____/_/ /_/____/  /_/   /_/   \\____/_/ /\\___/\\___/\\__/    /_/  \\___/____/\\__/  \n" +
                "                /_/                                        /___/                                        ");
    }

    public void jobObjects() {
        System.out.println("************************************************************");
        System.out.println("       __      __    ______           _            __  _                ______          __ \n" +
                "      / /___  / /_  / ____/  ______  (_)________ _/ /_(_)___  ____     /_  __/__  _____/ /_\n" +
                " __  / / __ \\/ __ \\/ __/ | |/_/ __ \\/ / ___/ __ `/ __/ / __ \\/ __ \\     / / / _ \\/ ___/ __/\n" +
                "/ /_/ / /_/ / /_/ / /____>  </ /_/ / / /  / /_/ / /_/ / /_/ / / / /    / / /  __(__  ) /_  \n" +
                "\\____/\\____/_.___/_____/_/|_/ .___/_/_/   \\__,_/\\__/_/\\____/_/ /_/    /_/  \\___/____/\\__/  \n" +
                "                           /_/                                                              ");
        System.out.println("************************************************************");
        Company company = testServices.getOneCompany(1);
        Coupon coupon1 = new Coupon(company, Category.Restaurant, "TEST 1", "Test!",
                new Date(124, 6, 11), new Date(System.currentTimeMillis()), 5, 55.9, "Test.jpg");
        Coupon coupon2 = new Coupon(company, Category.Leisure, "TEST 2", "Test!",
                new Date(124, 6, 11), new Date(System.currentTimeMillis()), 5, 55.9, "Test2.jpg");
        Coupon coupon3 = new Coupon(company, Category.Vacation, "TEST 3", "Test!",
                new Date(124, 6, 11), new Date(System.currentTimeMillis()), 5, 55.9, "Test3.jpg");
        testServices.getCupRep().save(coupon1);
        testServices.getCupRep().save(coupon2);
        testServices.getCupRep().save(coupon3);
        testServices.purchaseExpiredCoupon(1, 4);
        testServices.purchaseExpiredCoupon(1, 5);
        testServices.purchaseExpiredCoupon(1, 6);
    }



}


