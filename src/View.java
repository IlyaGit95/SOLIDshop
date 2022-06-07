import assortment.Assortment;

import java.util.List;

/*
По правилу DRY создаём класс View который содержит часто повторяющийся код для вывода информации на экран.
*/
class View {
    static void explanation() {
        System.out.println("############################################################\n" +
                "Введите символ указанный в кавычках что бы выбрать действие:\n");
    }

    static void invalidSymbol() {
        System.out.println("Не верный ввод данных! повторите попытку:");
    }

    static void printMenu() {
        explanation();
        System.out.println("<МЕНЮ>");
        System.out.println("\"a\" -> Список всех товаров\n" +
                "\"f\" -> Фильтр товаров\n" +
                "\"k\" -> Корзина\n" +
                "\"h\" -> История покупок\n" +
                "\"e\" -> завершение программы\n");
    }

    static void printAssortment(List<Assortment> assortmentList) {
        System.out.println("<СПИСОК ТОВАРОВ>");
        System.out.println("Доступный список:");

        if (assortmentList.size() == 0 || assortmentList == null) {
            System.out.println("Товары по вашему запросу не найдены!");
        } else {
            for (int i = 0; i < assortmentList.size(); i++) {
                System.out.println("\"" + i + "\"" + assortmentList.get(i));
            }
        }

        System.out.println("\n\"<номер товара>\" -> Введите номер товара для покупки\n" +
                "\"f\" -> Применить фильтр к данному списку\n" +
                "\"k\" -> Перейти корзину\n" +
                "\"m\" -> Вернуться в меню\n");
    }

}
