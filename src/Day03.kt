fun main() {

	fun part1(input: List<String>): Int {
		val matrix = parse(input)
		var sum = 0
		matrix.forEachIndexed { rIdx, row ->
			val num = StringBuilder()
			row.forEachIndexed { cIdx, col ->
				if (col.isDigit()) {
					num.append(col)
				}
				if (!col.isDigit() || cIdx == matrix[0].lastIndex) {
					if (num.isNotEmpty()) {
						val detail = num.toString().toInt()
						val minIndex =
							if (cIdx - num.length > 0) (cIdx - num.length) - 1 else 0
						if (checkSubMatrix(matrix, rIdx, minIndex, cIdx)) {
							sum += detail
						}
						num.clear()
					}
				}
			}
		}

		return sum
	}

//	val input = listOf(
//		"467..114..",
//		"...*......",
//		"..35..633.",
//		"......#...",
//		".#........",
//		"1.1.......",
//		"..........",
//		"..........",
//		"617*......",
//		".....+.58.",
//		"..592.....",
//		"......755.",
//		"...$.*....",
//		".664.598.."
//	)
	val input = readInput("Day03")
	part1(input).println()
}

private fun checkSubMatrix(
	matrix: Array<CharArray>,
	row: Int,
	minIndex: Int,
	maxIndex: Int
): Boolean {
	if ((matrix[row][minIndex] != '.' && !matrix[row][minIndex].isDigit())
		|| (matrix[row][maxIndex] != '.' && !matrix[row][maxIndex].isDigit())
	) {
		return true
	}
	if (row < matrix.size - 1) {
		matrix[row + 1].copyOfRange(minIndex, maxIndex + 1)
			.forEach { r ->
				if (!r.isDigit() && r != '.') {
					return@checkSubMatrix true
				}
			}
	}
	if (row > 0) {
		matrix[row - 1].copyOfRange(minIndex, maxIndex + 1)
			.forEach { r ->
				if (!r.isDigit() && r != '.') {
					return@checkSubMatrix true
				}
			}
	}
	return false
}

private fun parse(input: List<String>): Array<CharArray> {
	val matrix = Array(input.size) { CharArray(input[0].length) }

	input.forEachIndexed { row, s ->
		s.forEachIndexed { column, c ->
			matrix[row][column] = c
		}
	}

	return matrix
}