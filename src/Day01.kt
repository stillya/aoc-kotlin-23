fun main() {
	fun part1(input: List<String>): Int {
		return input.sumOf {
			(it.first { c -> c.isDigit() }.toString() + it.last { c -> c.isDigit() }
				.toString()).toInt()
		}
	}

	/**
	 * NOTE: "eightwo" -> 82 and not 88
	 */
	fun part2(input: List<String>): Int {
		val digits = mapOf(
			"one" to "o1e",
			"two" to "t2o",
			"three" to "th3ee",
			"four" to "f4ur",
			"five" to "f5ve",
			"six" to "s6x",
			"seven" to "s7ven",
			"eight" to "ei8ht",
			"nine" to "n9ne",
		)
		return input.sumOf { i ->
			var text = i
			digits.forEach { d ->
				text = text.replace(d.key, d.value)
			}
			 (text.first { c -> c.isDigit() }
				.toString() + text.last { c -> c.isDigit() }
				.toString()).toInt()
		}
	}

	val input = readInput("Day01")
	part1(input).println()
	part2(input).println()
}
