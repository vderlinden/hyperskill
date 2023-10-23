import kotlin.system.exitProcess

fun main() {
    var parking: Parking? = null
//    var parking = Parking(20)

    while (true) {
        val commandLine = readln().split("\\s+".toRegex())
        val command = commandLine.first()

        if (parking != null) {
            when (command) {
                "create" -> {
                    val nrOfLots = commandLine[1].toInt()
                    parking = Parking(nrOfLots)
                    println("Created a parking lot with $nrOfLots spots.")
                }
                "exit" -> exitProcess(0)
                "park" -> {
                    val registration = commandLine[1]
                    val color = commandLine[2]
                    val car = Car(registration, color)
                    val spot = parking.park(car)
                    if (spot >= 1) {
                        println("$color car parked in spot $spot.")
                    } else {
                        println("Sorry, the parking lot is full.")
                    }
                }

                "leave" -> {
                    val spotNr = commandLine[1].toInt()
                    parking.leave(spotNr)
                    println("Spot $spotNr is free.")
                }

                "status" -> {
                    parking.printStatus()
                }

                "spot_by_color" -> parking.printSpotsByColor(commandLine[1])
                "spot_by_reg" -> parking.printSpotsByRegistration(commandLine[1])
                "reg_by_color" -> parking.printRegistrationByColor(commandLine[1])
            }
        } else {
            when {
                command == "exit" -> exitProcess(0)
                command == "create" -> {
                    val nrOfLots = commandLine[1].toInt()
                    parking = Parking(nrOfLots)
                    println("Created a parking lot with $nrOfLots spots.")
                }

                else -> println("Sorry, a parking lot has not been created.")
            }
        }
    }
}

data class Car(val registration: String, val color: String)

class Parking(private val nrOfLots: Int) {
    private val spots: MutableList<Car?> = MutableList(nrOfLots) { null }

    fun park(car: Car): Int {
        val free: Int = spots.indexOfFirst { it == null }
        if (free >= 0) spots[free] = car
        return free + 1
    }

    fun leave(spot: Int) {
        spots[spot - 1] = null
//        println("Spot $spot is free.")
    }

    fun printStatus() {
        if (spots.all { it == null }) {
            println("Parking lot is empty.")
        } else {
            spots.forEachIndexed { index, car ->
                if (car != null) {
                    println("${index + 1} ${car.registration} ${car.color}")
                }
            }
        }
    }

    private fun spotsByColor(color: String) = spots.indices.filter { index ->
        spots[index]?.color?.uppercase() == color
    }

    fun printSpotsByColor(color: String) {
        val spotsWithColor = spotsByColor(color.uppercase())
        if (spotsWithColor.isNotEmpty()) {
            println(spotsWithColor.map { it + 1 }.joinToString())
        } else {
            println("No cars with color $color were found.")
        }
    }

    fun printSpotsByRegistration(registration: String) {
        val spotNr = spots.indexOfFirst { it?.registration == registration }
        if (spotNr >= 0) {
            println(spotNr + 1)
        } else {
            println("No cars with registration number $registration were found.")
        }
    }

    private fun registrationByColor(color: String) = spots.filter {
        it?.color?.lowercase() == color.lowercase()
    }

    fun printRegistrationByColor(color: String) {
        val found = registrationByColor(color)
        if (found.isNotEmpty()) {
            println(found.map { it?.registration }.joinToString())
        } else {
            println("No cars with color $color were found.")
        }
    }
}