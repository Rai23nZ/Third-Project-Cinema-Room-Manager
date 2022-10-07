package cinema

const val SEATS_SUM = 60
const val HALF = 2
const val PRICE_TEN = 10
const val PRICE_EIGHT = 8
var purchasedTickets = 0
var currentIncome = 0

fun main() {

    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsInRows = readln().toInt()
    val cinema = createRoom(rows, seatsInRows, "S")
    menu()
    var inputNumber: Int
    do {
        inputNumber = readln().toInt()
        when (inputNumber) {
            1 -> {
                printTheatre(cinema)
                menu()
            }
            2 -> {
                reserveSeatAndTicketPrice(rows, seatsInRows, cinema)
                menu()
            }
            3 -> {
                stat(rows, seatsInRows)
                menu()
            }
        }
    } while (inputNumber != 0)
}

fun menu() {
    println("""
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
    """.trimIndent())
}

fun createRoom(rows: Int, columns: Int, fill: String) =
    MutableList(rows) { MutableList(columns) { fill } }

fun printTheatre(cinema: MutableList<MutableList<String>>) {
    println("\nCinema:")
    println("  ${(1..cinema.first().size).joinToString(" ")}")
    cinema.forEachIndexed { rowNumber, row ->
        println(row.joinToString(prefix = "${rowNumber + 1} ", separator = " "))
    }
    println()
}

fun reserveSeatAndTicketPrice(rows: Int, seatsInRows: Int, cinema: MutableList<MutableList<String>>) {
    println("Enter a row number:")
    val chosenRow = readln().toInt()
    println("Enter a seat number in that row:")
    val chosenSeats = readln().toInt()
    if ((chosenRow > rows) || (chosenSeats > seatsInRows)) {
        println("Wrong Input!")
        return reserveSeatAndTicketPrice(rows, seatsInRows, cinema)
    }
    if (cinema[chosenRow - 1][chosenSeats - 1] == "B") {
        println("That ticket has already been purchased!")
        return reserveSeatAndTicketPrice(rows, seatsInRows, cinema)
    }
    val numberOfSeatsAll = rows * seatsInRows
    val ticketPrice = if (numberOfSeatsAll < SEATS_SUM) {
        currentIncome += PRICE_TEN
        println("Ticket price: $$PRICE_TEN")
    } else if (chosenRow <= rows / HALF) {
        currentIncome += PRICE_TEN
        println("Ticket price: $$PRICE_TEN")
    } else {
        currentIncome += PRICE_EIGHT
        println("Ticket price: $$PRICE_EIGHT")
    }
    purchasedTickets++
    cinema[chosenRow - 1][chosenSeats - 1] = "B"
}

fun stat(rows: Int, seatsInRows: Int) {
    println("Number of purchased tickets: $purchasedTickets")
    val percentage: Double = purchasedTickets * 100 / (rows * seatsInRows).toDouble()
    val formatPercentage = "%.2f".format(percentage)
    println("Percentage: ${formatPercentage}%")
    println("Current income: $${currentIncome}")
    val allSeats = rows * seatsInRows
    val total = if (allSeats < SEATS_SUM) {
        PRICE_TEN * allSeats
    } else {
        (rows / HALF * seatsInRows * PRICE_TEN) + ((rows / HALF + rows % HALF) * seatsInRows * PRICE_EIGHT)
    }
    println("Total income: $${total}")
}