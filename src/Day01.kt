import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Long {
        val leftNumbers: MutableList<Int> = mutableListOf()
        val rightNumbers: MutableList<Int> = mutableListOf()
        input.forEach{
            val numbers = it.split("   ")
            leftNumbers.add(numbers[0].toInt())
            rightNumbers.add(numbers[1].toInt())
        }
        leftNumbers.sort()
        rightNumbers.sort()

        var distance = 0L;
        leftNumbers.forEachIndexed { index, leftValue ->
            distance += (rightNumbers[index] - leftValue).absoluteValue
        }
        return distance
    }

    fun part2(input: List<String>): Long {
        val leftNumbers: MutableList<Int> = mutableListOf()
        val rightNumbers: MutableList<Int> = mutableListOf()
        input.forEach{
            val numbers = it.split("   ")
            leftNumbers.add(numbers[0].toInt())
            rightNumbers.add(numbers[1].toInt())
        }
        var similarityScore = 0L
        leftNumbers.forEach{ leftValue ->
            similarityScore += rightNumbers.count { it == leftValue } * leftValue
        }
        return similarityScore
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11L)
    check(part2(testInput) == 31L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
