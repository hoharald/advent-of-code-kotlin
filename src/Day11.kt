fun main() {

    fun blinkStone(engraving: String): Sequence<String> {
        return if(engraving == "0") {
            sequenceOf("1")
        } else if(engraving.length % 2 == 0) {
            sequenceOf(
                engraving.substring(0, engraving.length/2).toLong().toString(),
                engraving.substring(engraving.length/2, engraving.length).toLong().toString()
            )
        } else {
            sequenceOf(
                (engraving.toLong() * 2024L).toString()
            )
        }
    }

    fun blinkStones(stones: Sequence<String>): Sequence<String> {
        val stonesAfterBlink: MutableList<String> = mutableListOf()
        stones.forEach {
            stonesAfterBlink.addAll(blinkStone(it))
        }
        return stonesAfterBlink.asSequence()
    }

    fun blinkStones(stonesText: String, blinkTimes: Int): String {
        val stones = stonesText.splitToSequence(' ')
        var stonesAfterBlink = stones
        for (i in 1..blinkTimes) {
            stonesAfterBlink = blinkStones(stonesAfterBlink)
        }
        return stonesAfterBlink.joinToString(" ")
    }

    // Test if implementation meets criteria from the description, like:
    val testResult1 = blinkStones("0 1 10 99 999", 1)
    check(testResult1 == "1 2024 1 0 9 9 2021976")

    val testResult2 = blinkStones("125 17", 6)
    check(testResult2 == "2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2")
    val count2 = testResult2.split(' ').count()
    check(count2 == 22)

    val testResult3 = blinkStones("125 17", 25)
    val count3 = testResult3.split(' ').count()
    check(count3 == 55312)

    // Read the input from the `src/Day11.txt` file.
    val input = readInput("Day11")

    // Part 1
    val part1Stones = blinkStones(input[0], 25)
    val part1NumberOfStones = part1Stones.split(' ').count()
    part1NumberOfStones.println()

    // Part 2
    val part2Stones = blinkStones(input[0], 75)
    val part2NumberOfStones = part2Stones.split(' ').count()
    part2NumberOfStones.println()
}