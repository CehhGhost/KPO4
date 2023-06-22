package com.example.orders.services;

import com.example.orders.models.Dish;
import com.example.orders.repositories.DishRepository;
import com.example.orders.requests.CreateOrUpdateDishRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/*** Класс DishService предназначен для работы с сущностью блюда / блюд в системе заказов.
 * Методы класса позволяют создавать новые блюда, изменять их параметры, удалять и получать данные блюд.
 ***/
@Service
@AllArgsConstructor
public class DishService {
    // репозиторий для работы с сущностью Dish, управляет доступом к данным.
    private final DishRepository dishRepository;
    /*** Метод getAllDishes возвращает список всех блюд,
     * у которых количество равно или больше нуля. Пустой список,
     * если таких блюд нет.
     ***/
    public List<Dish> getAllDishes() {
        List<Dish> allDishes = new ArrayList<>();
        for (var item : dishRepository.findAll()) {
            if (item.getQuantity() > 0) {
                allDishes.add(item);
            }
        }
        return allDishes;
    }
    /*** Метод checkCreateDishRequest проверяет корректность параметров запроса при создании или изменении блюда.
     * @param request - запрос на создание / изменение блюда.
     ***/
    private Dish checkCreateDishRequest(CreateOrUpdateDishRequest request) {
        if (dishRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Dish with name like this is already existed");
        }
        if (request.getQuantity() != null && request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be more than 0 or equals");
        }
        if (request.getPrice() != null && request.getPrice().longValue() < 0) {
            throw new IllegalArgumentException("Price must be more than 0");
        }
        Dish newDish = new Dish();
        newDish.setName(request.getName());
        newDish.setDescription(request.getDescription());
        newDish.setPrice(request.getPrice());
        newDish.setQuantity(request.getQuantity());
        return newDish;
    }
    /*** Метод createDishRequest создает новое блюдо в системе.
     * @param request - запрос на создание блюда.
     ***/
    public void createDishRequest(CreateOrUpdateDishRequest request) {
        Dish newDish = checkCreateDishRequest(request);
        dishRepository.save(newDish);
    }
    /*** Метод updateDishById обновляет параметры блюда с заданным идентификационным номером.
     * @param id - идентификационный номер блюда.
     * @param request - запрос на изменение параметров блюда.
     ***/
    public void updateDishById(Integer id, CreateOrUpdateDishRequest request) {
        if (dishRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Wrong id for dish");
        }
        Dish newDish = checkCreateDishRequest(request);
        newDish.setId(id);
        dishRepository.save(newDish);
    }
    /*** Метод deleteDishById удаляет блюдо с заданным идентификационным номером.
     * @param id - идентификационный номер блюда.
     ***/
    public void deleteDishById(Integer id) {
        if (dishRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Wrong id for dish");
        }
        dishRepository.deleteById(id);
    }
    /*** Метод getDishById возвращает блюдо с заданным идентификационным номером.
     * @param id - идентификационный номер блюда.
     ***/
    public Dish getDishById(Integer id) {
        var dish = dishRepository.findById(id);
        if (dish.isEmpty()) {
            throw new IllegalArgumentException("Wrong id for dish");
        }
        return dish.get();
    }
}
