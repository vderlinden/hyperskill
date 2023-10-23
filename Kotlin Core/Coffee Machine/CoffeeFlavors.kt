package machine

enum class CoffeeFlavors(
    val number: Int,
    val flavor: String,
    val water: Int,
    val milk: Int,
    val beans: Int,
    val cost: Int
) {
    ESPRESSO(
        number = 1,
        flavor = "Espresso",
        water = 250,
        milk = 0,
        beans = 16,
        cost = 4
    ),
    LATTE(
        number = 2,
        flavor = "Latte",
        water = 350,
        milk = 75,
        beans = 20,
        cost = 7
    ),
    CAPPUCCINO(
        number = 3,
        flavor = "Cappuccino",
        water = 200,
        milk = 100,
        beans = 12,
        cost = 6
    );
}