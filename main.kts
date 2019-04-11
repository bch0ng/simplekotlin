import kotlin.math.*

// Explore a simple class
println("UW Homework: Simple Kotlin")

/**
 * Takes in any argument and returns a string
 * statement based on input.
 *
 * @param arg   an argument
 * @return      string depending on @param arg
 */
fun whenFn(arg: Any): String
{
    return when (arg) {
        "Hello" -> "world"
        is String -> "Say what?"
        0 -> "zero"
        1 -> "one"
        in 2..10 -> "low number"
        is Int -> "a number"
        else -> "I don't understand"
    }
}

/**
 * Adds two integers.
 *
 * @param num1  first integer
 * @param num2  second integer
 * @return      sum of @param num1 and @param num2
 */
fun add(num1: Int, num2: Int): Int
{
    return num1 + num2
}

/**
 * Subtracts the second integer from
 * the first integer.
 *
 * @param num1  minuend integer (being subtracted from)
 * @param num2  subtrahend integer (subtracting)
 * @return      difference of @param num1 and @param num2
 */
fun sub(num1: Int, num2: Int): Int
{
    return num1 - num2
}

/**
 *  Performs math operation given by the body argument.
 *
 * @param num1  first integer
 * @param num2  second integer
 * @param body  mathematical operation function
 * @return      result of @param body function
 *                  on @param num1 and @param num2
 */
fun mathOp(num1: Int, num2: Int, body: (num1: Int, num2: Int) -> Int): Int
{
    return body(num1, num2)
}

/**
 * Represents a person.
 *
 * @author Brandon Chong
 * @param firstName first name of person
 * @param lastname  last name of person
 * @param age       age of person
 */
class Person(var firstName: String, val lastName: String, var age: Int)
{
    val debugString: String
        get() = "[Person firstName:${firstName} lastName:${lastName} age:${age}]"
    
    /**
     *  Returns if this person and another person are the same person.
     *
     * @param otherPerson   another Person to compare
     * @return              if same person or not
     */
    fun equals(otherPerson: Person): Boolean
    {
        return this.hashCode() == otherPerson.hashCode()
    }

    /**
     * Returns a generated identifier for this person.
     *
     * @return  identifier integer code
     */
    override fun hashCode(): Int
    {
        return (firstName + lastName + age).hashCode()
    }
}

/**
 * Represents money.
 *
 * @author Brandon Chong
 * @param amount    amount of money
 * @param currency  currency of money
 */
data class Money(val amount: Int, val currency: String)
{
    val acceptedCurrrency: List<String> = listOf("USD", "EUR", "CAN", "GBP")
    val exchangeRateToUSD: List<Double> = listOf(1.0, 0.667, 0.8, 2.0)

    init
    {
        if (this.amount < 0)
            throw Exception("Amount cannot be less than zero.")
        if (this.currency !in acceptedCurrrency)
            throw Exception("Currency not accepted")
    }

    /**
     * Converts this Money into another currency
     * and returns as new converted Money.
     *
     * @param currency  currency to convert to
     * @return          new Money in @param currency
     */
    public fun convert(currency: String): Money
    {
        if (this.currency !in acceptedCurrrency)
            throw Exception("Currency not accepted")
        if (this.currency == currency)
            return Money(this.amount, this.currency)
        else {
            val indexOfCurrency = this.acceptedCurrrency.indexOf(this.currency)
            val exchangeRateCurrency = this.exchangeRateToUSD.get(indexOfCurrency)
            val convertedToCurrency = this.amount * exchangeRateCurrency
            var roundedIntCurrency = round(convertedToCurrency).toInt()
            if (currency != "USD") {
                val indexOfCurrency2 = this.acceptedCurrrency.indexOf(currency)
                val exchangeRateCurrency2 = this.exchangeRateToUSD.get(indexOfCurrency2)
                val convertedToCurrency2 = roundedIntCurrency / exchangeRateCurrency2
                roundedIntCurrency = round(convertedToCurrency2).toInt()
            }
            return Money(roundedIntCurrency, currency)
        }
    }
    
    /**
     * Adds two Money objects (converts them if need be)
     * and returns their sum.
     *
     * @param otherMoney    other Money to add to this Money
     * @returns             new Money sum
     */
    operator fun plus(otherMoney: Money): Money
    {
        if (this.currency != otherMoney.currency)
            return Money((this.amount + otherMoney.convert(this.currency).amount), this.currency)
        else
            return Money((this.amount + otherMoney.amount), this.currency)
    }
}

// ============ DO NOT EDIT BELOW THIS LINE =============

print("When tests: ")
val when_tests = listOf(
    "Hello" to "world",
    "Howdy" to "Say what?",
    "Bonjour" to "Say what?",
    0 to "zero",
    1 to "one",
    5 to "low number",
    9 to "low number",
    17.0 to "I don't understand"
)
for ((k,v) in when_tests) {
    print(if (whenFn(k) == v) "." else "!")
}
println("")

print("Add tests: ")
val add_tests = listOf(
    Pair(0, 0) to 0,
    Pair(1, 2) to 3,
    Pair(-2, 2) to 0,
    Pair(123, 456) to 579
)
for ( (k,v) in add_tests) {
    print(if (add(k.first, k.second) == v) "." else "!")
}
println("")

print("Sub tests: ")
val sub_tests = listOf(
    Pair(0, 0) to 0,
    Pair(2, 1) to 1,
    Pair(-2, 2) to -4,
    Pair(456, 123) to 333
)
for ( (k,v) in sub_tests) {
    print(if (sub(k.first, k.second) == v) "." else "!")
}
println("")

print("Op tests: ")
print(if (mathOp(2, 2, { l,r -> l+r} ) == 4) "." else "!")
print(if (mathOp(2, 2, ::add ) == 4) "." else "!")
print(if (mathOp(2, 2, ::sub ) == 0) "." else "!")
print(if (mathOp(2, 2, { l,r -> l*r} ) == 4) "." else "!")
println("")


print("Person tests: ")
val p1 = Person("Ted", "Neward", 47)
print(if (p1.firstName == "Ted") "." else "!")
p1.age = 48
print(if (p1.debugString == "[Person firstName:Ted lastName:Neward age:48]") "." else "!")
println("")

print("Money tests: ")
val tenUSD = Money(10, "USD")
val twelveUSD = Money(12, "USD")
val fiveGBP = Money(5, "GBP")
val fifteenEUR = Money(15, "EUR")
val fifteenCAN = Money(15, "CAN")
val convert_tests = listOf(
    Pair(tenUSD, tenUSD),
    Pair(tenUSD, fiveGBP),
    Pair(tenUSD, fifteenEUR),
    Pair(twelveUSD, fifteenCAN),
    Pair(fiveGBP, tenUSD),
    Pair(fiveGBP, fifteenEUR)
)
for ( (from,to) in convert_tests) {
    print(if (from.convert(to.currency).amount == to.amount) "." else "!")
}
val moneyadd_tests = listOf(
    Pair(tenUSD, tenUSD) to Money(20, "USD"),
    Pair(tenUSD, fiveGBP) to Money(20, "USD"),
    Pair(fiveGBP, tenUSD) to Money(10, "GBP")
)
for ( (pair, result) in moneyadd_tests) {
    print(if ((pair.first + pair.second).amount == result.amount &&
              (pair.first + pair.second).currency == result.currency) "." else "!")
}
println("")
