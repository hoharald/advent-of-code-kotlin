fun main() {

    fun part1(input: List<String>): Int {
        val diffRange = 1..3
        var safeReports = 0
        input.forEach { report ->
            val levels = report.split(" ").map { string -> string.toInt() }
            if (levels.count() < 2) {
                return@forEach
            }

            var isSaveInc = false
            var isSaveDec = false
            levels.forEachIndexed { index, level ->
                if (index > 0) {
                    isSaveInc = isSaveInc && level - levels[index - 1] in diffRange
                    isSaveDec = isSaveDec && levels[index - 1] - level in diffRange
                } else {
                    isSaveInc = levels[1] - level in diffRange
                    isSaveDec = level - levels[1] in diffRange
                }
                if (!isSaveInc && !isSaveDec) return@forEach
            }
            safeReports++
        }
        return safeReports
    }

    fun part2(input: List<String>): Int {
        val diffRange = 1..3
        var safeReports = 0

        // Iterate over all reoprts
        input.forEach report@{ report ->
            val levels = report.split(" ").map { string -> string.toInt() }
            if (levels.count() < 2) {
                return@report
            }

            var isSaveInc = false
            var isSaveDec = false

            run level@{
                // Iterate over all levels
                levels.forEachIndexed { index, level ->
                    if (index > 0) {
                        isSaveInc = isSaveInc && level - levels[index - 1] in diffRange
                        isSaveDec = isSaveDec && levels[index - 1] - level in diffRange
                    } else {
                        isSaveInc = levels[index + 1] - level in diffRange
                        isSaveDec = level - levels[index + 1] in diffRange
                    }

                    // In case of bad level remove it from report and check for save
                    if (!isSaveInc && !isSaveDec) {
                        var reducedLevels = levels.toMutableList().apply { removeAt(index) }.toList()
                        var reducedIsSaveInc = false
                        var reducedIsSaveDec = false

                        reducedLevels.forEachIndexed { reducedIndex, reducedLevel ->
                            if (reducedIndex > 0) {
                                reducedIsSaveInc =
                                    reducedIsSaveInc && reducedLevel - reducedLevels[reducedIndex - 1] in diffRange
                                reducedIsSaveDec =
                                    reducedIsSaveDec && reducedLevels[reducedIndex - 1] - reducedLevel in diffRange
                            } else {
                                reducedIsSaveInc = reducedLevels[reducedIndex + 1] - reducedLevel in diffRange
                                reducedIsSaveDec = reducedLevel - reducedLevels[reducedIndex + 1] in diffRange
                            }

                            // In case removeing level results still in a bad result try remove next level
                            if (!reducedIsSaveInc && !reducedIsSaveDec && levels.count() > index + 1) {
                                reducedLevels = levels.toMutableList().apply { removeAt(index + 1) }.toList()

                                reducedLevels.forEachIndexed { nIndex, nLevel ->
                                    if (nIndex > 0) {
                                        reducedIsSaveInc =
                                            reducedIsSaveInc && nLevel - reducedLevels[nIndex - 1] in diffRange
                                        reducedIsSaveDec =
                                            reducedIsSaveDec && reducedLevels[nIndex - 1] - nLevel in diffRange
                                    } else {
                                        reducedIsSaveInc = reducedLevels[nIndex + 1] - nLevel in diffRange
                                        reducedIsSaveDec = nLevel - reducedLevels[nIndex + 1] in diffRange
                                    }

                                    // In case removeing level results still in a bad result try remove previous level
                                    if (!reducedIsSaveInc && !reducedIsSaveDec && index >= 1) {
                                        reducedLevels = levels.toMutableList().apply { removeAt(index - 1) }.toList()

                                        reducedLevels.forEachIndexed { pIndex, pLevel ->
                                            if (pIndex > 0) {
                                                reducedIsSaveInc =
                                                    reducedIsSaveInc && pLevel - reducedLevels[pIndex - 1] in diffRange
                                                reducedIsSaveDec =
                                                    reducedIsSaveDec && reducedLevels[pIndex - 1] - pLevel in diffRange
                                            } else {
                                                reducedIsSaveInc = reducedLevels[pIndex + 1] - pLevel in diffRange
                                                reducedIsSaveDec = pLevel - reducedLevels[pIndex + 1] in diffRange
                                            }

                                            if (!reducedIsSaveInc && !reducedIsSaveDec) return@report
                                        }
                                        return@level
                                    }
                                    if (!reducedIsSaveInc && !reducedIsSaveDec) return@report
                                }
                                return@level
                            }
                            if (!reducedIsSaveInc && !reducedIsSaveDec) return@report
                        }
                        return@level
                    }
                }
            }
            safeReports++
        }
        return safeReports
    }

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
