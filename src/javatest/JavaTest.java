/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javatest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaTest {

    public static long MAX_OF_DAY;// Limit còn lại trong ngày
    public static ArrayList<Long> acceptArray;// Danh sách các mệnh giá cho phép
    public static ArrayList<Product> products;// Danh sách các sản phẩm
    public static HashMap<Date, Long> logs;// Danh sách dữ liệu file log

    public static void main(String[] args) throws ParseException {

        long yourMoney = 0;
        //Thêm danh sách các mệnh giá cho phép
        acceptArray = new ArrayList<>();
        acceptArray.add((long) 10000);
        acceptArray.add((long) 20000);
        acceptArray.add((long) 50000);
        acceptArray.add((long) 100000);
        acceptArray.add((long) 200000);

        Scanner scanner = new Scanner(System.in);
        //Thêm danh sách các sản phẩm có trong máy bán hàng
        products = new ArrayList<>();
        products.add(new Product(1, "Coke", 10000));
        products.add(new Product(2, "Pepsi", 10000));
        products.add(new Product(3, "Soda", 20000));
        String input;
        JavaTest obj = new JavaTest();
        obj.readLog();
        do {
            System.out.println("Hãy nhập vào sản phẩm bạn muốn mua : ");
            input = scanner.nextLine();
            if (input.equals("Exit")) {
                System.out.println("Kết thúc giao dịch");
            }

            if (obj.isNumberic(input)) {
                int choice = Integer.parseInt(input);
                //Tìm kiếm product
                Product product = obj.findProduct(choice);
                if (product == null) {
                    System.out.println("Không tìm thấy sản phẩm này");
                } else {
                    System.out.println("Sản phẩm bạn muốn mua là : " + product.getTenSanPham());
                    System.out.println("Nhập vào số lượng sản phẩm muốn mua : ");
                    input = scanner.nextLine();
                    //Kiểm tra nhập vào phải là số
                    while (!obj.isNumberic(input)) {
                        System.out.println("Hãy nhập vào một số : ");
                        input = scanner.nextLine();
                    }
                    int number = Integer.parseInt(input);
                    Random random = new Random();
                    int tmp = number;
                    int randomNumber = 0;
                    String dateString = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                    Date today = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
                    long sum = 1;
                    //Kiểm tra nếu số lượng sản phẩm lớn hơn hoặc bằng 3
                    if (number >= 3) {
                        if (obj.checkLimit(today) == false && MAX_OF_DAY >= product.getGiaSanPham()) {

                            randomNumber = random.nextInt(10);
                        }
                        if (obj.checkLimit(today) == true && MAX_OF_DAY >= product.getGiaSanPham()) {
                            randomNumber = random.nextInt(2);
                        }
                        //Nếu randomNumber == 1 thì thực hiện khuyến mãi
                        if (randomNumber == 0) {
                            sum = product.getGiaSanPham() * number;
                            number = number + 1;
                        } else {
                            sum = product.getGiaSanPham() * number;
                        }
                    } else {
                        sum = product.getGiaSanPham() * number;
                    }
                    System.out.println(randomNumber);
                    System.out.println("Tổng số lượng : " + number);
                    System.out.println("Tổng số tiền cần thanh toán : " + sum);
                    System.out.println("Nhập vào mệnh giá bạn sẽ thanh toán : ");
                    input = scanner.nextLine();
                    while (Long.parseLong(input) < sum) {
                        System.out.println("Mệnh giá bé hơn tổng tiền cần thanh toán");
                        System.out.println("Hãy chọn mệnh giá khác : ");
                        input = scanner.nextLine();
                    }
                    if (obj.isNumberic(input)) {
                        long money = Long.parseLong(input);
                        while (!acceptArray.contains(money)) {
                            System.out.println("Không hỗ trợ mạnh giá này");
                            System.out.println("Hãy nhập vào mệnh giá khác : ");
                            input = scanner.nextLine();
                        }
                        System.out.println("Bạn có muốn thực hiện thanh toán");
                        System.out.println("OK-Tiếp tục || Exit-Thoát");
                        input = scanner.nextLine();
                        while (!input.equals("OK") && !input.equals("Exit")) {
                            System.out.println("Bạn có muốn thực hiện thanh toán");
                            System.out.println("OK-Tiếp tục || Exit-Thoát");
                            input = scanner.nextLine();
                        }
                        yourMoney = money;
                        if (input.equals("OK")) {
                            System.out.println("Sô tiền nhận vào : " + yourMoney);
                            System.out.println("Số tiền cần thanh toán : " + sum);
                            System.out.println("Số tiền thối : " + (yourMoney - sum));
                            if (randomNumber == 1 && tmp >= 3 && obj.checkLimit(today) == false && MAX_OF_DAY >= product.getGiaSanPham()) {
                                MAX_OF_DAY = MAX_OF_DAY - product.getGiaSanPham();
                                obj.writeLogs(today);
                            }
                        }
                        if (input.equals("Exit")) {
                            System.out.println("Số tiền được hoàn lại : " + yourMoney);
                        }
                    }
                }
            }

        } while (!input.equals("Exit"));
    }

    //Kiểm tra n có phải là số hay không
    public boolean isNumberic(String n) {
        try {
            Long number = Long.parseLong(n);
            return true;
        } catch (NumberFormatException ex) {

        }
        return false;
    }

    //Kiểm tra product có tồn tại không
    public Product findProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    //Đọc log từ file log.txt
    public void readLog() {
        logs = new HashMap<>();
        File file = new File("src/javatest/log.txt");
        if (!file.exists() || file.isDirectory()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                System.out.println(date);
                long limit = Long.parseLong(scanner.nextLine());
                logs.put(date, limit);

            }
            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
            Date today = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
            if (logs.containsKey(today)) {
                MAX_OF_DAY = logs.get(today);
            } else {
                MAX_OF_DAY = 50000;
            }
        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Kiểm tra log của ngày trước đó
    public boolean checkLimit(Date today) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        if (logs.containsKey(yesterday) && logs.get(yesterday) == 0) {
            return true;
        }
        return false;
    }

    //Lưu log lại
    public void writeLogs(Date today) {
        if (logs.containsKey(today)) {
            logs.replace(today, MAX_OF_DAY);
        } else {
            logs.put(today, MAX_OF_DAY);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/javatest/log.txt"))) {
            int number = 1;
            int hashMapSize = logs.size();
            for (Map.Entry<Date, Long> entry : logs.entrySet()) {
                String line = new SimpleDateFormat("dd/MM/yyyy").format(entry.getKey());
                String limit = entry.getValue().toString();
                if (number == hashMapSize) {
                    writer.append(line).append("\n").append(limit);
                } else {
                    writer.append(line).append("\n");
                    writer.append(limit).append("\n");
                    number++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
