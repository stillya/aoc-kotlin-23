fun main() {

	fun part1(input: List<String>): Int {
		val (gears, numbers) = parseSpec(input)
		return gears.associateWith { g ->
			numbers.filter { n -> n.row in (g.row - 1)..(g.row + 1) }
				.filter { n ->
					g.column in n.colRange.first - 1..n.colRange.second + 1
				}
		}.map { (_, numbers) ->
			numbers.fold(0) { acc, n -> acc + n.value }
		}.sum()
	}

	fun part2(input: List<String>): Int {
		val (gears, numbers) = parseSpec(input, '*')
		return gears.associateWith { g ->
			numbers.filter { n -> n.row in (g.row - 1)..(g.row + 1) }
				.filter { n ->
					g.column in n.colRange.first - 1..n.colRange.second + 1
				}
		}.filter { it.value.size == 2 }
			.map { (_, numbers) -> numbers.fold(1) { acc, n -> acc * n.value } }
			.sum()
	}

	val input = readInput("Day03")
	part1(input).println()
	part2(input).println()
}

private fun parseSpec(
	input: List<String>,
	gearSym: Char? = null
): Pair<List<Gear>, List<Number>> {
	val gears = mutableListOf<Gear>()
	val numbers = mutableListOf<Number>()
	val num = StringBuilder()
	input.forEachIndexed { rIdx, row ->
		row.forEachIndexed { cIdx, col ->
			if ((gearSym?.let { col == it } ?: col.let { it != '.' && !it.isDigit() })) {
				gears.add(Gear(rIdx, cIdx))
			} else if (col.isDigit()) {
				num.append(col)
			}
			if (!col.isDigit() || cIdx == row.lastIndex) {
				if (num.isNotEmpty()) {
					val detail = num.toString().toInt()
					val minIndex =
						if (num.length == 1 && cIdx == row.lastIndex) cIdx else if (num.length == 1) cIdx - 1 else cIdx - num.length
					val maxIndex =
						if (num.length == 1 && cIdx == row.lastIndex) cIdx else cIdx - 1
					numbers.add(Number(detail, rIdx, minIndex to maxIndex))
					num.clear()
				}
			}
		}
	}

	return gears to numbers
}

private data class Gear(val row: Int, val column: Int)

private data class Number(val value: Int, val row: Int, val colRange: Pair<Int, Int>)