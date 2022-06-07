import assortment.Assortment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
Создаём класс Shop который содержит в себе основную логику работы программы.
*/
public class Shop {

    private final Scanner scanner = new Scanner(System.in);
    protected static List<Assortment> assortmentList = new ArrayList<>();
    protected List<Assortment> startAssortmentList = new ArrayList<>();
    private final Cart cart = new Cart();

    private final History history = new History();

    private final FilterAssortment filterAssortment = new FilterAssortment();

    private String symbol;

    //добавляем ассортимент в магазин
    private void addAssortment() {
        Assortment.Manufacturer[] manufacturerList = Assortment.Manufacturer.values();
        for (Assortment.Manufacturer manufacturer : manufacturerList) {
            startAssortmentList.addAll(Arrays.asList(manufacturer.getAssortments()));
        }
        assortmentList = new ArrayList<>(startAssortmentList);
    }

    //главное меню, разделяем интерфейс для пользователя на разные категории по правилам Interface Segregation Principle.
    protected void start() {
        addAssortment();
        View.printMenu();
        while (true) {

            symbol = scanner.nextLine();
            if (symbol.equals("e")) {
                return;
            } else if (symbol.equals("a")) {
                assortmentList = new ArrayList<>(startAssortmentList);
                allAssortment();
            } else if (symbol.equals("f")) {
                assortmentList = new ArrayList<>(startAssortmentList);
                filterAssortment.startFilter();
            } else if (symbol.equals("k")) {
                cart.startCart();
            } else if (symbol.equals("h")) {
                history.startHistory();
            } else {
                View.invalidSymbol();
            }
        }
    }

    //показать весь список, разделяем интерфейс для пользователя на разные категории по правилам Interface Segregation Principle.
    protected void allAssortment() {

        View.explanation();
        View.printAssortment(assortmentList);
        while (true) {

            symbol = scanner.nextLine();
            if (symbol.equals("m")) {
                View.printMenu();
                return;
            } else if (symbol.equals("k")) {
                cart.startCart();
                View.printMenu();
                return;
            } else if (symbol.equals("f")) {
                filterAssortment.startFilter();
                return;
            } else if (isInvalidInt(symbol, assortmentList.size() - 1)) {
                buy(assortmentList, symbol);
                View.printAssortment(assortmentList);
            } else {
                View.invalidSymbol();
            }
        }
    }

    //проверка на правильное число, часто повторяющийся код, вынесен в отдельный метод по правилам DRY
    protected static boolean isInvalidInt(String symbol, int maxCount) {
        try {
            int tmp = Integer.parseInt(symbol);
            if (tmp >= 0 && tmp <= maxCount) {
                return true;
            }
        } catch (RuntimeException e) {
            return false;
        }
        return false;
    }

    protected static boolean isInvalidInt(String symbol) {
        try {
            int tmp = Integer.parseInt(symbol);
            if (tmp >= 0) {
                return true;
            }
        } catch (RuntimeException e) {
            return false;
        }
        return false;
    }

    //покупка товара
    private void buy(List<Assortment> assortmentList, String symbol) {
        int intSymbol = Integer.parseInt(symbol);
        System.out.println("Вы выбрали: " + assortmentList.get(intSymbol) + "\n" +
                "Введите желаемое кол-во товара:");
        String symbol2;
        int intSymbol2;
        while (true) {
            System.out.println("<ПОКУПКА ТОВАРА>");
            symbol2 = scanner.nextLine();
            if (isInvalidInt(symbol2) && !symbol2.equals("0")) {
                intSymbol2 = Integer.parseInt(symbol2);
                cart.addInCart(assortmentList.get(intSymbol), intSymbol2);

                System.out.println("Товар: " + cart.getCartList().get(cart.getCartList().size() - 1) + ",a добавлен в корзину\n");
                return;
            } else {
                View.invalidSymbol();
            }
        }
    }
}

