import assortment.Assortment;

import java.util.ArrayList;
import java.util.Scanner;

/*
По правилам Interface Segregation Principle и Single Responsibility Principle
создаём отдельный класс History хранящий в себе логику истории покупок в магазине
и разделяем интерфейс для пользователя на разные категории.
*/
public class History {
    protected static ArrayList<History> localHistoryList = new ArrayList<>();
    protected ArrayList<Assortment> histAssortmentList = new ArrayList<>();
    protected ArrayList<Integer> histAmountList = new ArrayList<>();
    private String symbol;
    private int intSymbol;
    protected Scanner scanner = new Scanner(System.in);

    //истории покупок, разделяем интерфейс для пользователя на разные категории по правилам Interface Segregation Principle.
    protected void startHistory() {
        printHist();
        if (localHistoryList.size() == 0) {
            return;
        }
        while (true) {

            symbol = scanner.nextLine();
            if (symbol.equals("m")) {
                View.printMenu();
                return;
            } else if (Shop.isInvalidInt(symbol, localHistoryList.size() - 1)) {
                intSymbol = Integer.parseInt(symbol);
                raringOrReturn(localHistoryList.get(intSymbol));
            } else {
                View.invalidSymbol();
            }
            if (localHistoryList.size() == 0) {
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

    //функционал оценки или возврата товара
    protected void raringOrReturn(History history) {
        System.out.println("Введите номер позиции товара в заказе:");
        while (true) {
            symbol = scanner.nextLine();
            if (Shop.isInvalidInt(symbol, history.histAssortmentList.size() - 1)) {
                intSymbol = Integer.parseInt(symbol);
                Assortment assortment = history.histAssortmentList.get(intSymbol);
                System.out.println("Вы выбрали товар: " + assortment);
                System.out.println("Выберете действие:");
                System.out.println("\"r\" -> Оценить товар\n" +
                        "\"b\" -> Вернуть товар\n" +
                        "\"h\" -> Отмена действия\n");
                while (true) {
                    symbol = scanner.nextLine();
                    if (symbol.equals("r")) {
                        addRating(assortment);
                        return;
                    } else if (symbol.equals("b")) {
                        removeAssortment(history, intSymbol);
                        return;
                    } else if (symbol.equals("h")) {
                        printHist();
                        return;
                    } else {
                        View.invalidSymbol();
                    }
                }
            } else {
                View.invalidSymbol();
            }
        }
    }

    //возврата товара
    protected void removeAssortment(History history, int intSymbol) {
        history.histAssortmentList.remove(intSymbol);
        history.histAmountList.remove(intSymbol);
        if (history.histAssortmentList.size() == 0) {
            localHistoryList.remove(history);
        }
        System.out.println("Возврат совершён!");

        printHist();

    }

    //оценки товара
    protected void addRating(Assortment assortment) {
        System.out.println("Введите вашу оценку от 0 до 5:");

        while (true) {
            symbol = scanner.nextLine();

            if (Shop.isInvalidInt(symbol, 5)) {
                intSymbol = Integer.parseInt(symbol);
                assortment.addRating(intSymbol);
                System.out.println("Спасибо за оценку товара!");
                printHist();
                return;
            } else {
                View.invalidSymbol();
            }
        }
    }


    protected int getHistoryResultPrise(Assortment assortment, int amount) {
        return assortment.getPrice() * amount;
    }

    // выводим на экран содержимое истории покупок, разделяем интерфейс
    // для пользователя на разные категории по правилам Interface Segregation Principle.
    protected void printHist() {
        if (localHistoryList.size() == 0) {
            System.out.println("История покупок пуста! выберете другой пункт:");
            return;
        }
        View.explanation();
        System.out.println("<ИСТОРИЯ ПОКУПОК>");
        int numberOrder = 0;

        for (History history : localHistoryList) {
            System.out.println("Ваш заказ номер -> " + "\"" + numberOrder++ + "\"");
            int numberPosition = 0;
            int allResultPrise = 0;

            for (int i = 0; i < history.histAssortmentList.size(); i++) {
                int resultPrise = getHistoryResultPrise(history.histAssortmentList.get(i), history.histAmountList.get(i));
                System.out.printf("      \"%d\"  %s: цена: %d %s, производитель: %s, куплено в кол-ве: %d, рейтинг: %s, на сумму = %d\n",
                        numberPosition++, history.histAssortmentList.get(i).getName(), history.histAssortmentList.get(i).getPrice(),
                        history.histAssortmentList.get(i).getConventionalUnits(), history.histAssortmentList.get(i).getManufacturer().getValue(),
                        history.histAmountList.get(i), history.histAssortmentList.get(i).getRating(), resultPrise);
                allResultPrise += resultPrise;
            }
            System.out.println("Итоговая сумма оплаты составляет: " + allResultPrise + "\n");
        }

        System.out.println("\n\"<номер товара>\" -> Введите номер заказа для возврата или оценки товара\n" +
                "\"m\" -> Вернуться в меню\n");
    }
}

