package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Класс создаваемого заказа
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreate {

    private List<String> ingredients;
}