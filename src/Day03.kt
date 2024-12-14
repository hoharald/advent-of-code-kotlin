import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Long {

        var result = 0L

        val regex = """mul\((?<m1>\d{1,3}),(?<m2>\d{1,3})\)""".toRegex()

        input.forEach { line ->

            val instructions = regex.findAll(line)

            instructions.forEach instruction@{ match ->
                val m1 = match.groups["m1"]?.value?.toInt()
                val m2 = match.groups["m2"]?.value?.toInt()

                if (m1 == null || m2 == null) return@instruction

                result += m1 * m2
            }
        }
        return result
    }

    fun part2(input: List<String>): Long {

        var result = 0L

        val regex = """mul\((?<m1>\d{1,3}),(?<m2>\d{1,3})\)""".toRegex()
        val regexDo = """do\(\)""".toRegex()
        val regexDont = """don't\(\)""".toRegex()

        val source = input.joinToString("")


        val dos = regexDo.findAll(source).toList()
        val donts = regexDont.findAll(source).toList()
        val instructions = regex.findAll(source)

        instructions.forEach instruction@{ match ->

            val mostRecentDo = dos.findLast { it.range.start < match.range.start }
            val mostRecentDont = donts.findLast { it.range.start < match.range.start }

            if ((mostRecentDont?.range?.start ?: -1) > (mostRecentDo?.range?.start ?: -1)) return@instruction

            val m1 = match.groups["m1"]?.value?.toInt()
            val m2 = match.groups["m2"]?.value?.toInt()

            if (m1 == null || m2 == null) return@instruction

            result += m1 * m2
        }
        return result
    }

    // Or read a large test input from the `src/Day03_test.txt` file:
    var testInput = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
    check(part1(testInput) == 161L)
    testInput = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
    check(part2(testInput) == 48L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
