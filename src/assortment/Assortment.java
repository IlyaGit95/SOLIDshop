package assortment;

import assortment.bakeryProducts.*;
import assortment.fruit.*;
import assortment.meat.*;
import assortment.vegetables.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/*
По принципам Dependency inversion principle, Open-Closed principle, Single-responsibility principle и DRY,
создаём абстрактный класс прародитель Assortment и наследуем от него все товары в магазине.
Содержит в себе реализованные методы для типовых действий с любыми товарами в магазине.
*/
public abstract class Assortment implements Type.Products, Cloneable {
    private final String name;
    private Manufacturer manufacturer;
    private final int price;
    private int amount = 0;
    private ArrayList<Integer> rating;
    private final static String KG = "(за кг)";
    private final static String SHT = "(за шт)";
    private String conventionalUnits = KG;


    protected Assortment(String name, int price) {
        this.name = name;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public String getConventionalUnits() {
        return conventionalUnits;
    }

    public String getRating() {
        if (rating == null) {
            return "нет оценок";
        }
        int sum = 0;
        for (int rat : rating) {
            sum += rat;
        }
        return String.format("%.1f", ((double) sum / rating.size()));
    }

    protected Assortment setStartRating(Integer... rating) {
        this.rating = new ArrayList<>(Arrays.asList(rating));
        return this;
    }

    public void addRating(int newRating) {
        if (rating == null) {
            rating = new ArrayList<>();
        }
        rating.add(newRating);
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void clearAmount() {
        amount = 0;
    }

    public int getAmount() {
        return amount;
    }

    public Assortment setConventionalUnits(String conventionalUnits) {
        this.conventionalUnits = conventionalUnits;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assortment that = (Assortment) o;
        return price == that.price && Objects.equals(name, that.name) && manufacturer == that.manufacturer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, manufacturer, price);
    }

    @Override
    public String toString() {
        String ratingNow;
        if (rating == null) {
            ratingNow = "нет оценок";
        } else {
            ratingNow = getRating();
        }

        return String.format(" %s: цена: %d %s, производитель: %s,  рейтинг: %s",
                name, price, conventionalUnits, manufacturer.getValue(), ratingNow);
    }


    /*
    Чтобы избежать появления "магических" значений в коде, создаём импровизированную
    базу данных типа enum, которая содержит в себе информацию о всех доступных производителях товаров
    и о самих товарах которые были произведены данными производителями.
    */
    public enum Manufacturer {
        PROMTORG("\"OOO ПРОМТОРГ\"",
                new Assortment[]{
                        new Apple("Яблоки-сезонные", 90).setStartRating(5),
                        new Banana("Бананы-фасованные", 95),
                        new Pineapple("Ананасы", 95).setConventionalUnits(SHT).setStartRating(4, 5),
                        new Beef("Говядина", 739)
                }),

        MIRATORG("\"OOO МИРФТОРГ\"",
                new Assortment[]{
                        new Beef("Стейк говядина-мраморная", 500).setConventionalUnits("(за 400 г/шт)"),
                        new Mutton("Баранина-рёбра", 260).setConventionalUnits("(за 300 г/шт)"),
                        new Pork("Свинина-окорок", 300).setStartRating(3, 4, 2),
                        new Cucumber("Огурцы", 120).setStartRating(4, 4, 5)
                }),

        GROWERS("\"OOO ГРОВЕРС\"",
                new Assortment[]{
                        new Cucumber("Огурцы-короткоплодные", 120).setStartRating(3, 4),
                        new Onion("Лук-репчатый", 60).setStartRating(1),
                        new Tomato("Помидоры", 130).setStartRating(5, 5, 4),
                        new Bread("Хлеб с отрубями", 30).setConventionalUnits(SHT)
                }),
        KORKYNOV("\"OOO КОРКУНОВ\"",
                new Assortment[]{
                        new Bread("Хлеб-бородинский", 35).setConventionalUnits(SHT),
                        new Cookie("Печенье с шоколадом", 29).setConventionalUnits(SHT).setStartRating(5, 4, 4),
                        new Donut("Пончики", 30).setConventionalUnits(SHT),
                });

        private final String value;
        private final Assortment[] assortments;

        Manufacturer(String value, Assortment[] assortments) {
            this.value = value;
            this.assortments = assortments;
        }

        public String getValue() {
            return value;
        }

        public Assortment[] getAssortments() {
            for (Assortment assortment : assortments) {
                assortment.manufacturer = this;
            }
            return assortments;
        }
    }
}
