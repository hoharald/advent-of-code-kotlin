fun main() {

    fun part1(input: List<String>): Long {

        var rulesCompleted = false
        val rules: MutableList<Pair<Int, Int>> = mutableListOf()
        val updates: MutableList<List<Int>> = mutableListOf()

        input.forEach {

            if (it.isEmpty()) {
                rulesCompleted = true
                return@forEach
            }

            if (rulesCompleted) {
                val update = it.split(',')
                    .map { text -> text.trim().toInt() }
                updates.add(update)
            } else {
                val ruleList = it.split('|')
                    .map { text -> text.trim().toInt() }
                rules.add(Pair(ruleList[0], ruleList[1]))
            }
        }

        var sumOfMiddlePages = 0L

        updates.forEach update@{ update ->
            rules.forEach rule@{ rule ->
                if (!update.containsAll(rule.toList())) return@rule
                if (update.indexOf(rule.first) > update.indexOf(rule.second)) return@update
            }
            sumOfMiddlePages += update[update.size / 2]
        }
        return sumOfMiddlePages
    }

    fun part2(input: List<String>): Long {

        var rulesCompleted = false
        val rules: MutableList<Pair<Int, Int>> = mutableListOf()
        val updates: MutableList<List<Int>> = mutableListOf()

        input.forEach {

            if (it.isEmpty()) {
                rulesCompleted = true
                return@forEach
            }

            if (rulesCompleted) {
                val update = it.split(',')
                    .map { text -> text.trim().toInt() }
                updates.add(update)
            } else {
                val ruleList = it.split('|')
                    .map { text -> text.trim().toInt() }
                rules.add(Pair(ruleList[0], ruleList[1]))
            }
        }

        var sumOfMiddlePages = 0L

        updates.forEach update@{ update ->
            val correctedUpdate = update.toMutableList()

            var corrections = 0

            do {
                var corrected = false
                
                rules.forEach rule@{ rule ->
                    if (!correctedUpdate.containsAll(rule.toList())) return@rule

                    val firstIdx = correctedUpdate.indexOf(rule.first);
                    val secondIdx = correctedUpdate.indexOf(rule.second);

                    if (firstIdx <= secondIdx) return@rule

                    corrected = true
                    corrections++

                    val temp = correctedUpdate[firstIdx]
                    correctedUpdate[firstIdx] = correctedUpdate[secondIdx]
                    correctedUpdate[secondIdx] = temp
                }
            } while (corrected)

            if(corrections > 0) {
                sumOfMiddlePages += correctedUpdate[correctedUpdate.size / 2]
            }
        }
        return sumOfMiddlePages
    }

    // Or read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143L)
    check(part2(testInput) == 123L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
