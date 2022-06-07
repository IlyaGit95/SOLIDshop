import assortment.Assortment;
import assortment.Type;

import java.util.Scanner;
import java.util.stream.Collectors;

/*
По правилам Interface Segregation Principle и Single Responsibility Principle
создаём отдельный класс FilterAssortment хранящий в себе логику фильтрации товаров в магазине
и разделяем интерфейс для пользователя на разные категории.
*/
public class FilterAssortment {
    private String symbol;
    protected Shop shop;

    protected Scanner scanner = new Scanner(System.in);

    //фильтр товаров, разделяем интерфейс для пользователя на разные категории по правилам Interface Segregation Principle.
    protected void startFilter() {
        shop = new Shop();
        printFilter();
        while (true) {

            symbol = scanner.nextLine();
            if (symbol.equals("m")) {
                View.printMenu();
                return;
            } else if (symbol.equals("i")) {
                manufactureFilter();
                shop.allAssortment();
                return;
            } else if (symbol.equals("s")) {
                keywordFilter();
                shop.allAssortment();
                return;
            } else if (symbol.equals("p")) {
                priseFilter();
                shop.allAssortment();
                return;
            } else if (symbol.equals("c")) {
                categoriesFilter();
                shop.allAssortment();
                return;
            } else {
                View.invalidSymbol();
            }
        }

    }

    //фильтр по цене
    protected void priseFilter() {
        System.out.println("Выберите действие:\n" +
                "\"u\" -> Отсортировать по возрастанию цен\n" +
                "\"l\" -> Отсортировать по убыванию цен\n");
        while (true) {
            symbol = scanner.nextLine();
            if (symbol.equals("u")) {
                Shop.assortmentList = Shop.assortmentList.stream()
                        .sorted((assortment1, assortment2) -> assortment1.getPrice() - assortment2.getPrice())
                        .collect(Collectors.toList());
                return;
            } else if (symbol.equals("l")) {
                Shop.assortmentList = Shop.assortmentList.stream()
                        .sorted((assortment1, assortment2) -> assortment2.getPrice() - assortment1.getPrice())
                        .collect(Collectors.toList());
                return;
            } else {
                View.invalidSymbol();
            }
        }
    }

    //фильтр по производителю
    protected void manufactureFilter() {
        System.out.println("Выберите производителя:\n" +
                "\"p\" -> \"OOO ПРОМТОРГ\"\n" +
                "\"m\" -> \"OOO МИРФТОРГ\"\n" +
                "\"g\" -> \"OOO ГРОВЕРС\"\n" +
                "\"k\" -> \"OOO КОРКУНОВ\"\n");

        Assortment.Manufacturer manufacturer;
        while (true) {
            symbol = scanner.nextLine();
            if (symbol.equals("p")) {
                manufacturer = Assortment.Manufacturer.PROMTORG;
                break;
            } else if (symbol.equals("m")) {
                manufacturer = Assortment.Manufacturer.MIRATORG;
                break;
            } else if (symbol.equals("g")) {
                manufacturer = Assortment.Manufacturer.GROWERS;
                break;
            } else if (symbol.equals("k")) {
                manufacturer = Assortment.Manufacturer.KORKYNOV;
                break;
            } else {
                View.invalidSymbol();
            }
        }
        Shop.assortmentList = Shop.assortmentList.stream()
                .filter(assortment -> assortment.getManufacturer().equals(manufacturer))
                .collect(Collectors.toList());
    }

    //фильтр по типу продукта
    protected void categoriesFilter() {
        System.out.println("Выберите тип продукта:\n" +
                "\"f\" -> \"Фрукты\"\n" +
                "\"m\" -> \"Мясо\"\n" +
                "\"v\" -> \"Овощи\"\n" +
                "\"b\" -> \"Выпечка\"\n");
        Type type;
        while (true) {
            symbol = scanner.nextLine();
            if (symbol.equals("f")) {
                type = Type.FRUIT;
                break;
            } else if (symbol.equals("m")) {
                type = Type.MEAT;
                break;
            } else if (symbol.equals("v")) {
                type = Type.VEGETABLES;
                break;
            } else if (symbol.equals("b")) {
                type = Type.BAKERY_PRODUCTS;
                break;
            } else {
                View.invalidSymbol();
            }
        }
        Shop.assortmentList = Shop.assortmentList.stream()
                .filter(assortment -> assortment.getType().equals(type))
                .collect(Collectors.toList());
    }

    //фильтр по ключевому слову
    protected void keywordFilter() {
        System.out.println("Введите название желаемого товара:");
        symbol = scanner.nextLine();
        Shop.assortmentList = Shop.assortmentList.stream()
                .filter(assortment -> {
                    return assortment.getName().toLowerCase().contains(symbol.toLowerCase());
                })
                .collect(Collectors.toList());
    }

    // выводим на экран функционал фильтрации продуктов, разделяем интерфейс
    // для пользователя на разные категории по правилам Interface Segregation Principle.
    protected void printFilter() {
        View.explanation();
        System.out.println("<ФИЛЬТР ТОВАРОВ>");
        System.out.println("Доступные фильтры:");
        System.out.println("\"p\" -> Цена\n" +
                "\"i\" -> Производитель\n" +
                "\"s\" -> Ключевое слово\n" +
                "\"c\" -> Категории\n" +
                "\"m\" -> Вернуться в меню\n");
    }
}
