package machine

// Main program
fun main() {
    // Create coffee machine object
    val coffeeMachine = CoffeeMachine()

    // Take orders, make money!
    while (true) {
        // Invoke the apparatus!
        coffeeMachine.start()
    }
}
