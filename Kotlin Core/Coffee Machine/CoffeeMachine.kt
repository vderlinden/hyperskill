package machine

import kotlin.system.exitProcess

// This is the actual apparatus
class CoffeeMachine {
    private var water: Int = 0
    private var milk: Int = 0
    private var beans: Int = 0
    private var cups: Int = 0
    private var money: Int = 0

    // Fill supplies on creation
    init {
        this.fill(water = 400, milk = 540, beans = 120, cups = 9, money = 550)
    }

    fun start() {
        // Request action from user
        println("Write action (buy, fill, take, remaining or exit):")
        val action: String = readln()
        this.action(action)
    }

    private fun action(action: String) {
        when (action) {
            "buy" -> this.actionCoffee()

            "fill" -> this.actionFill()

            "take" -> this.take()

            "remaining" -> this.stats()

            "exit" -> exitProcess(0)

            else -> {
                println("Action not possible!")
                this.start()
            }
        }
    }

    private fun actionCoffee() {
        println("What do you want to buy? ${this.showCoffeeFlavors()}: ")
        val flavor = readln()
        if (flavor == "back") {
            this.start()
        } else {
            this.selectCoffeeFlavor(flavor = flavor.toInt())
        }
    }

    private fun actionFill() {
        println("Write how many ml of water you want to add:")
        val water = readln().toInt()
        println("Write how many ml of milk you want to add:")
        val milk = readln().toInt()
        println("Write how many grams of coffee beans you want to add:")
        val beans = readln().toInt()
        println("Write how many disposable cups you want to add:")
        val cups = readln().toInt()
        this.fill(water = water, milk = milk, beans = beans, cups = cups)
        this.start()
    }

    private fun showCoffeeFlavors(): String {
        var flavors = ""
        for (enum in CoffeeFlavors.values()) {
            flavors += "${enum.number} - ${enum.flavor}"
            if (enum.ordinal != (CoffeeFlavors.values().size - 1))
                flavors += ", "
        }
        return flavors
    }

    private fun selectCoffeeFlavor(flavor: Int) {
        for (enum in CoffeeFlavors.values()) {
            if (flavor == enum.number) {
                this.buy(enum)
            } else {
                continue
            }
        }
    }

    private fun fill(water: Int = 0, milk: Int = 0, beans: Int = 0, cups: Int = 0, money: Int = 0) {
        this.water += water
        this.milk += milk
        this.beans += beans
        this.cups += cups
        this.money += money
    }

    private fun use(water: Int = 0, milk: Int = 0, beans: Int = 0, cups: Int = 0, money: Int = 0) {
        this.water -= water
        this.milk -= milk
        this.beans -= beans
        this.cups -= cups
        this.money -= money
    }

    private fun buy(coffeeFlavors: CoffeeFlavors) {
        when {
            water < coffeeFlavors.water -> println("Sorry, not enough water!")
            milk < coffeeFlavors.milk -> println("Sorry, not enough milk!")
            beans < coffeeFlavors.beans -> println("Sorry, not enough beans!")
            cups < 1 -> println("Sorry, not enough cups!")
            else -> {
                this.use(water = coffeeFlavors.water, milk = coffeeFlavors.milk, beans = coffeeFlavors.beans, cups = 1)
                this.fill(money = coffeeFlavors.cost)
                println("I have enough resources, making you a coffee!")
                this.start()
            }
        }
    }

    private fun take() {
        println("I gave you \$${this.money}")
        this.use(money = this.money)
        this.start()
    }

    private fun stats() {
        println()
        println(
            """
            The coffee machine has:
            $water ml of water
            $milk ml of milk
            $beans g of coffee beans
            $cups disposable cups
            $$money of money
            """.trimIndent()
        )
        println()
        this.start()
    }
}
