package assortment;

/*
Товары в магазине, по мимо производителей, делятся на различные категории.
Создаём enum типов этих категорий, и присваиваем эти категории товарам
через имплементацию интерфейса Products.
*/
public enum Type {
    FRUIT("Фрукты"),
    MEAT("Мясо"),
    VEGETABLES("Овощи"),
    BAKERY_PRODUCTS("Выпечка"),
    ABSTRACT_TYPE("Абстрактный тип");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /*
    Интерфейс Products будет прародителем для интерфейсов на каждый тип продукта.
    */
    public interface Products {
        Type type = Type.ABSTRACT_TYPE;

        default Type getType() {
            return type;
        }

        default String getTypeName() {
            return type.getValue();
        }
    }
}
