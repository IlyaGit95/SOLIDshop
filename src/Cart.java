import assortment.Assortment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
По правилам Interface Segregation Principle и Single Responsibility Principle
создаём отдельный класс Cart хранящий в себе логику корзины покупок в магазине
и разделяем интерфейс для пользователя на разные категории.
*/
public class Cart {
    protected static List<Assortment> cartList = new ArrayList<>();

    protected Scanner scanner = new Scanner(System.in);

    protected List<Assortment> getCartList() {
        return cartList;
    }

    //корзина, разделяем интерфейс для пользователя на разные категории по правилам Interface Segregation Principle.
    protected void startCart() {
        printCart();
        if (cartList.size() == 0) {
            return;
        }
        while (true) {
            String symbol = scanner.nextLine();
            if (symbol.equals("m")) {
                View.printMenu();
                return;
            } else if (Shop.isInvalidInt(symbol, cartList.size() - 1)) {
                int intSymbol = Integer.parseInt(symbol);
                removeAssortment(cartList.get(intSymbol), intSymbol);
            } else if (symbol.equals("p")) {
                pay();
                System.out.println("Заказ оплачен! Вы можете его посмотреть в истории покупок\n");
                return;
            } else {
                View.invalidSymbol();
            }
            if (cartList.size() == 0) {
                System.out.println("\"m\" -> Вернуться в меню\n");
                while (true) {
                    symbol = scanner.nextLine();
                    if (symbol.equals("m")) {
                        View.printMenu();
                        return;
                    } else {
                        View.invalidSymbol();
                    }
                }
            }
        }
    }

    //оплата товара
    protected void pay() {
        History history = new History();
        history.histAssortmentList.addAll(cartList);
        for (Assortment assortment : cartList) {
            history.histAmountList.add(assortment.getAmount());
            assortment.clearAmount();
        }
        cartList = new ArrayList<>();
        History.localHistoryList.add(history);
    }

    //добавляем товар в корзину
    protected void addInCart(Assortment assortment, int amount) {
        assortment.addAmount(amount);
        if (!cartList.contains(assortment)) {
            cartList.add(assortment);
        }
    }

    protected static int getResultPrise(Assortment assortment) {
        return assortment.getPrice() * assortment.getAmount();
    }

    //удаляем товар из корзины
    protected void removeAssortment(Assortment assortment, int number) {
        assortment.clearAmount();
        cartList.remove(number);
        System.out.println("Товар удалён");
        printCart();
    }

    protected static int getAllResultPrise(List<Assortment> cartList) {
        int sum = 0;
        for (Assortment assortment : cartList) {
            sum += getResultPrise(assortment);
        }
        return sum;
    }

    // выводим на экран содержимое корзины, разделяем интерфейс
    // для пользователя на разные категории по правилам Interface Segregation Principle.
    protected void printCart() {
        if (cartList.size() == 0) {
            System.out.println("Корзина пуста! выберете другой пункт:");
            return;
        }
        View.explanation();
        System.out.println("<КОРЗИНА>");
        System.out.println("Ваша корзина: ");

        int number = 0;
        for (Assortment assortment : cartList) {
            System.out.printf("\"%d\" %s: цена: %d %s, производитель: %s, в корзине: %d, рейтинг: %s, на сумму = %d\n", number++,
                    assortment.getName(), assortment.getPrice(), assortment.getConventionalUnits(), assortment.getManufacturer().getValue(),
                    assortment.getAmount(), assortment.getRating(), getResultPrise(assortment));
        }
        System.out.println("Всего сумма к оплате составляет: " + getAllResultPrise(cartList) + "\n");


        System.out.println("\n\"<номер товара>\" -> Введите номер товара для удаления из корзины\n" +
                "\"p\" -> оплатить товары\n" +
                "\"m\" -> Вернуться в меню\n");
    }

}
