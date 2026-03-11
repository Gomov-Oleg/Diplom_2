package model;

import java.util.List;

// Класс создаваемого заказа
public class OrderCreate {

    private List<String> ingredients;

    // Конструктор с параметром List<String>
    public OrderCreate(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    // Конструктор без параметров
    public OrderCreate(){}

    // Геттеры и сеттеры
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
