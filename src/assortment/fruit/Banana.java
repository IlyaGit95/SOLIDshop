package assortment.fruit;

import assortment.Assortment;

/*
Создаём класс определённого продукта и наследуем его от абстрактного класса Assortment и имплементируем интерфейс
потомок Type.Products, наделяя класс необходимыми свойствами соблюдая правила Liskov Substitution Principle,
Single Responsibility Principle, Dependency Inversion Principle.
*/
public class Banana extends Assortment implements Fruit{
    public Banana(String name, int price) {
        super(name, price);
    }

}
