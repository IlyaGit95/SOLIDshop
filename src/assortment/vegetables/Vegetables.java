package assortment.vegetables;

import assortment.Type;

/*
Создаём интерфейс и наследуем его от интерфейса Type.Products,переопределяем методы
под определённый тип продукта. Далее с помощью имплементации интерфейса присваиваем
продуктам их тип соблюдая правило DRY.
*/
public interface Vegetables extends Type.Products {
    Type type = Type.VEGETABLES;

    @Override
    default Type getType() {
        return type;
    }

    @Override
    default String getTypeName() {
        return type.getValue();
    }
}
