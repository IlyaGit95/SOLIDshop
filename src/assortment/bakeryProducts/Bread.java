package assortment.bakeryProducts;

import assortment.Assortment;
/*
Создаём класс определённого продукта и наследуем его от абстрактного класса Assortment и имплементируем интерфейс
потомок Type.Products, наделяя класс необходимыми свойствами соблюдая правила Liskov Substitution Principle,
Single Responsibility Principle, Dependency Inversion Principle.
*/
public class Bread extends Assortment implements BakeryProducts {
    public Bread(String name, int price) {
        super(name, price);
    }
}
