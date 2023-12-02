fun main() {

	fun part1(input: List<String>): Int {
		return parse(input).filter {
			it.sets.all { s ->
				s.blue <= 14 && s.red <= 12 && s.green <= 13
			}
		}.sumOf { it.id }
	}

	fun part2(input: List<String>): Int {
		return parse(input).map {
			val blue = it.sets.maxOf { s -> s.blue }
			val red = it.sets.maxOf { s -> s.red }
			val green = it.sets.maxOf { s -> s.green }
			blue * red * green
		}.sum()
	}

	val input = readInput("Day02")
	part1(input).println()
	part2(input).println()
}

fun setsBorder(s: String): MutableList<Int> {
	val setsEndIdx = mutableListOf<Int>()
	var setIdx = 0
	while (setIdx != -1) {
		setIdx = s.indexOf(";", setIdx + 1)
		if (setIdx != -1) {
			setsEndIdx.add(setIdx)
		}
	}
	setsEndIdx.add(s.lastIndex)
	return setsEndIdx
}

fun colorsBorder(setsBorder: List<Int>, s: String): Map<Int, Int> {
	val sets = mutableMapOf<Int, Int>() // colorIdx -> setIdx
	var idx = 0
	setsBorder.forEachIndexed { i, end ->
		while (idx != -1) {
			idx = s.indexOf(",", idx)
			if (idx != -1 && idx < end) {
				sets[idx] = i
				idx++
			} else if (end < idx) {
				idx = -1
			}
		}
		idx = end
		sets[idx] = i
	}
	sets[s.lastIndex + 1] = setsBorder.lastIndex
	return sets
}

fun parse(input: List<String>): List<Game> {
	return input.map {
		val sets = mutableListOf<Set>()
		val idEndIdx = it.indexOf(":")
		var idStartIdx: Int = idEndIdx
		while (it[idStartIdx] != ' ') {
			idStartIdx--
		}
		idStartIdx++

		val id = it.substring(idStartIdx, idEndIdx).toInt()
		val setsBorderIdx = setsBorder(it)
		setsBorderIdx.forEach { _ ->
			sets.add(Set(0, 0, 0))
		}

		val colorsBorderIdx = colorsBorder(setsBorderIdx, it)

		colorsBorderIdx.forEach { (cIdx, sIdx) ->
			val colorBuilder = StringBuilder()
			var colorIdx = cIdx - 1
			while (it[colorIdx] != ' ') {
				colorBuilder.append(it[colorIdx])
				colorIdx--
			}
			val color = colorBuilder.reverse().toString()

			val countBuilder = StringBuilder()
			var countIdx = colorIdx - 1
			while (it[countIdx] != ' ') {
				countBuilder.append(it[countIdx])
				countIdx--
			}
			val count = countBuilder.reverse().toString().toInt()

			when (color) {
				"blue" -> sets[sIdx].blue = count
				"green" -> sets[sIdx].green = count
				"red" -> sets[sIdx].red = count
			}
		}

		Game(
			id = id,
			sets = sets
		)
	}

}

data class Game(
	val id: Int,
	val sets: List<Set>
)

data class Set(
	var blue: Int,
	var red: Int,
	var green: Int
)