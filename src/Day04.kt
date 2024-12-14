fun main() {
    // # | 0 1 2 3 4 5 6 7 8 9
    // = | ===================
    // 0 | . . . . X X M A S .
    // 1 | . S A M X M S . . .
    // 2 | . . . S . . A . . .
    // 3 | . . A . A . M S . X
    // 4 | X M A S A M X . M M
    // 5 | X . . . . . X A . A
    // 6 | S . S . S . S . S S
    // 7 | . A . A . A . A . A
    // 8 | . . M . M . M . M M
    // 9 | . X . X . X M A S X

    fun getAmount(input: Array<String>): Long {

        var result = 0L

        val regex = """XMAS""".toRegex()
        val regexB = """SAMX""".toRegex()

        input.forEach { line ->
            val amount = regex.findAll(line).count()
            val amountB = regexB.findAll(line).count()
            result += amount + amountB
        }

        return result
    }

    fun part1(input: List<String>): Long {

        var result = 0L

        val regex = """XMAS""".toRegex()
        val regexB = """SAMX""".toRegex()

        val size = input[0].length

        val verticals = Array(size) { "" }
        val diagonalsLTR = Array(size + input.size - 1) { "" }
        val diagonalsRTL = Array(size + input.size - 1) { "" }

        // Horizontal lookup and vertical list preperation
        input.forEachIndexed { lineIdx, line ->

            val horizontal = regex.findAll(line).count()
            val horizontalBackwards = regexB.findAll(line).count()
            result += horizontal + horizontalBackwards

            line.forEachIndexed { index, c ->

                // Create verticals
                verticals[index] = verticals[index] + c

                // Create LTR diagonals
                var diagonalIdx = index + input.size - 1
                var lineCharIdx = lineIdx + index

                if (lineCharIdx < line.length) {
                    diagonalsLTR[diagonalIdx] = diagonalsLTR[diagonalIdx] + line[lineCharIdx]
                }

                diagonalIdx = input.size - 1 - lineIdx + index

                if (diagonalIdx < input.size - 1) {
                    diagonalsLTR[diagonalIdx] = diagonalsLTR[diagonalIdx] + c
                }

                // Create RTL diagonals
                diagonalIdx = index
                lineCharIdx = index - lineIdx

                if (lineCharIdx >= 0) {
                    diagonalsRTL[diagonalIdx] = diagonalsRTL[diagonalIdx] + line[lineCharIdx]
                }

                diagonalIdx = lineIdx + index

                if (diagonalIdx > line.length - 1 && diagonalIdx < diagonalsRTL.size) {
                    diagonalsRTL[diagonalIdx] = diagonalsRTL[diagonalIdx] + c
                }
            }
        }

        result += getAmount(verticals)
        result += getAmount(diagonalsLTR)
        result += getAmount(diagonalsRTL)

        return result
    }

    fun part2(input: List<String>): Long {

        var result = 0L

        val sizeX = input[0].length
        val sizeY = input.size

        val field = Array(sizeY) { Array(sizeX) { '.' } }
        val possibleCenters: MutableList<Pair<Int, Int>> = mutableListOf()

        for (y in 0..<sizeY) {
            for (x in 0..<sizeX) {
                field[y][x] = input[y][x]

                if (x > 0 && y > 0 && x < sizeX - 1 && y < sizeY - 1 && input[y][x] == 'A') {
                    possibleCenters.add(Pair(y, x))
                }
            }
        }

        possibleCenters.forEach {
            val ltrMas =
                (field[it.first - 1][it.second - 1] == 'M' &&
                field[it.first + 1][it.second + 1] == 'S') ||
                (field[it.first - 1][it.second - 1] == 'S' &&
                field[it.first + 1][it.second + 1] == 'M')

            val rtlMas =
                (field[it.first + 1][it.second - 1] == 'M' &&
                field[it.first - 1][it.second + 1] == 'S') ||
                (field[it.first + 1][it.second - 1] == 'S' &&
                field[it.first - 1][it.second + 1] == 'M')

            if(ltrMas && rtlMas) result++
        }

        return result
    }

    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18L)
    check(part2(testInput) == 9L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
