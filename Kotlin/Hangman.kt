import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

// variables
var word: String = ""
val letterList: MutableList<Char> = ArrayList()
var display: MutableList<Char> = ArrayList()
var previousGuesses: MutableList<Char> = mutableListOf()
val alpha = Regex("^[a-zA-Z]*\$")
var gameOn = false
var turns: Int = 0

fun mainMenu() {
    println("         Hangman     \n")
    println("1. New Game")
    println("2. Exit")
    val input = Scanner(System.`in`)
    print("\nEnter an option: ")
    try {
        val option = input.nextInt()
        if (option == 1) {
            println("\nLoading Hangman ....")
            gameOn = true
        } else {
            println("\nQuiting Hangman ....")
            exitProcess(1)
        }
    } catch (e: InputMismatchException) {
        println("Invalid option, try again.")
        mainMenu()
    }
}

fun listToDisplay(mutableList: MutableList<Char>): String {
    var displayLetters = mutableList.toString()
    displayLetters = displayLetters.replace(",", "")
    displayLetters = displayLetters.replace("[", "")
    displayLetters = displayLetters.replace("]", "")
    return displayLetters
}

fun getWord() {
    val input = Scanner(System.`in`)
    print("\nEnter a word : ")
    word = input.nextLine().toUpperCase()
    if (word.matches(alpha) && word.isNotEmpty() && word.length > 3) {
        word.forEach {
            run {
                display.add('_')
                letterList.add(it)
            }
        }
        clearConsole()
    } else {
        println("\n---------------------INVALID WORD-----------------------")
        println("| 1. The word cannot contain any special characters    |")
        println("| 2. The word cannot contain any numeric digits.       |")
        println("| 3. The word cannot be less than 3 letters in length. |")
        println("--------------------------------------------------------")
        getWord()
    }

}

fun getGuess() {
    val input = Scanner(System.`in`)
    print("\nEnter a guess : ")

    val guess = input.nextLine().toUpperCase()

    if (guess.length == 1 && guess.matches(alpha)) {
        checkGuess(guess.toCharArray()[0])
    } else {
        println("\n----------------------INVALID GUESS-----------------------")
        println("| 1. The guess cannot contain any special characters.     |")
        println("| 2. The guess cannot contain any numeric digits.         |")
        println("| 3. The guess cannot be greater than 1 letter in length. |")
        println("-----------------------------------------------------------")
        getGuess()
    }

}

fun checkGuess(guess: Char) {
    if (guess in previousGuesses) {
        println(
            "You already guessed this letter! Previous guesses : ${previousGuesses.toString().replace(
                "[",
                ""
            ).replace("]", "")}."
        )
        return
    }

    if (guess in letterList) {
        for ((index, it) in letterList.withIndex()) {
            if (guess == it) {
                display[index] = it
            }
        }
        previousGuesses.add(guess)
        println("You guessed a letter in the word!")
    } else {
        println("The word doesn't contain this letter!")
    }
}

fun checkWin() {
    if (listToDisplay(display).replace(" ", "").matches(alpha)) {
        gameOn = false
        printDisplay()
        println("\nYou won! You took $turns attempts.")
        playAgain()
        return
    }

    if (turns == word.length + 5) {
        gameOn = false
        println("You ran out of turns, the word was $word")
        playAgain()
    }
}

fun printDisplay() {
    val board = StringBuilder()
    display.forEach {
        if (it == '_') {
            board.append(" * ")
        } else {
            board.append(" $it ")
        }
    }
    print("\n$board\n")
}

fun main() {
    mainMenu()
    getWord()
    while (gameOn) {
        printDisplay()
        getGuess()
        checkWin()
        turns++
    }
}

fun playAgain() {
    val input = Scanner(System.`in`)
    print("\nDo you want to play again? [Y/N]")

    val option = input.nextLine().toUpperCase()

    if (option.startsWith("Y")) {
        clearConsole()
        main()
    } else {
        exitProcess(1)
    }

}

fun clearConsole() {
    for (i in 0 until 80 * 300) {
        print("\n") // Prints a new line
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }
}


