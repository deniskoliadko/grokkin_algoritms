class Solution {

    /*
        2357. Make Array Zero by Subtracting Equal Amounts
        You are given a non-negative integer array nums. In one operation, you must:

        Choose a positive integer x such that x is less than or equal to the smallest non-zero element in nums.
        Subtract x from every positive element in nums.
        Return the minimum number of operations to make every element in nums equal to 0.

        Создаем пустой HashSet<Int>()
        Проходим по всем элементам массива
        Если элемент не равен 0, добавляем его в сет
        Возвращаем размер сет

        Поскольку HashSet хранит только уникальные значения, одинаковые числа автоматически
        объединяются.

        Время: O(n)
        Память: O(n)
     */
    fun minimumOperations(nums: IntArray): Int {
        val set = HashSet<Int>()

        for (num in nums) {
            if (num != 0) {
                set.add(num)
            }
        }

        return set.size
    }


    /*
        1365. How Many Numbers Are Smaller Than the Current Number
        Given the array nums, for each nums[i] find out how many numbers in the array are
        smaller than it. That is, for each nums[i] you have to count the number of
        valid j's such that j != i and nums[j] < nums[i]. Return the answer in an array.

        Самое очевидное решение - Для каждого элемента пройтись по всему массиву

        Более быстрое решение O(n log n)
        отсортировать массив
        создать мапу по этому массиву - элемент -> индекс первого вхождения,
        перед ним находятся все меньшие числа
        итерируем по исходному массиву и берем валью по ключу

        Время: O(n log n)
        Память: O(n)
     */
    fun smallerNumbersThanCurrent(nums: IntArray): IntArray {
        val sorted = nums.sorted()

        val map = HashMap<Int, Int>()

        for (i in sorted.indices) {
            map.putIfAbsent(sorted[i], i)
        }

        val answer = IntArray(nums.size)

        for (i in nums.indices) {
            answer[i] = map[nums[i]]!!
        }

        return answer
    }


    /*
        1827. Minimum Operations to Make the Array Increasing
        You are given an integer array nums (0-indexed). In one operation, you can choose
        an element of the array and increment it by 1. For example, if nums = [1,2,3],
        you can choose to increment nums[1] to make nums = [1,3,3]. Return the minimum
        number of operations needed to make nums strictly increasing. An array nums is
        strictly increasing if nums[i] < nums[i+1] for all 0 <= i < nums.length - 1.
        An array of length 1 is trivially strictly increasing.

        задача решается жадным алгоритмом

        Для каждого элемента есть всего два варианта

        1 Если уже подходит nums[i] > nums[i-1] - ничего делать не нужно

        2 Если не подходит - нужно увеличить nums[i-1] + 1  - любое увеличение сверх этого только
        увеличит число операций и еще сильнее "поднимет планку" для следующих элементов -
        єто и есть жадный выбор

        Алгоритм

            Идем слева направо
            Для каждого элемента:
                если nums[i] <= nums[i-1]
                    нужно увеличить
                    nums[i-1] + 1
                    ответ += новое - старое
                    nums[i] = новое

        Время: O(n) - один проход по массиву
        Память: O(1) - используем только несколько переменных
     */
    fun minOperations(nums: IntArray): Int {
        var operations = 0

        for (i in 1 until nums.size) {
            if (nums[i] <= nums[i - 1]) {
                val newValue = nums[i - 1] + 1
                operations += newValue - nums[i]
                nums[i] = newValue
            }
        }

        return operations
    }

    /*
        462. Minimum Moves to Equal Array Elements II
        Given an integer array nums of size n, return the minimum number of moves required to make all array elements equal.
        In one move, you can increment or decrement an element of the array by 1.
        Test cases are designed so that the answer will fit in a 32-bit integer.

        посчитал оба примера предложеных в хадаче
        [1,2,3] и   [1,10,2,9]
        [3,2,3]     [19, 18, 16, 16]
        ответ 2 и 16
        интуитивно - массив сортируем и берем из середины - в обоих случаях 2 и (2 или 9) выигрывает середина


        Алгоритм

            отсортировать
            пройти по массиву и сложить все (n[i] - середина)

        Время: O(n log n) - сортировка + один проход по массиву
        Память: O(1) - используем эффективный алгоритм в jvm для сортировки существующего массива nums.sort()
     */
    fun minMoves2(nums: IntArray): Int {
        nums.sort()

        val median = nums[nums.size / 2]

        var moves = 0L // лонг от переполнения

        for (n in nums) {
            moves += kotlin.math.abs(n.toLong() - median)
        }

        return moves.toInt()
    }

    /*
    3072. Distribute Elements Into Two Arrays II
    You are given a 1-indexed array of integers nums of length n.

    We define a function greaterCount such that greaterCount(arr, val) returns the number of elements in arr that are strictly greater than val.

    You need to distribute all the elements of nums between two arrays arr1 and arr2 using n operations. In the first operation, append nums[1] to arr1. In the second operation, append nums[2] to arr2. Afterwards, in the ith operation:

    If greaterCount(arr1, nums[i]) > greaterCount(arr2, nums[i]), append nums[i] to arr1.
    If greaterCount(arr1, nums[i]) < greaterCount(arr2, nums[i]), append nums[i] to arr2.
    If greaterCount(arr1, nums[i]) == greaterCount(arr2, nums[i]), append nums[i] to the array with a lesser number of elements.
    If there is still a tie, append nums[i] to arr1.
    The array result is formed by concatenating the arrays arr1 and arr2. For example, if arr1 == [1,2,3] and arr2 == [4,5,6], then result = [1,2,3,4,5,6].

    Return the integer array result.

    Hint 1
    We need a data structure that counts the number of integers greater than a given value x and supports insertion.
    Hint 2
    Use Segment Tree or Binary Indexed Tree by compressing the numbers to the range [1,n].

    просто в лоб не проходит по времени O(n) * O(n) = O(n2)


     */
    fun resultArray(nums: IntArray): IntArray {
        val arr1 = mutableListOf<Int>()
        val arr2 = mutableListOf<Int>()

        arr1.add(nums[0])
        arr2.add(nums[1])

        for (i in 2 until nums.size) {
            val num = nums[i]

            val greater1 = greaterCount(arr1, num)
            val greater2 = greaterCount(arr2, num)

            if (greater1 > greater2) {
                arr1.add(num)
            } else if (greater1 < greater2) {
                arr2.add(num)
            } else {
                if (arr1.size <= arr2.size) {
                    arr1.add(num)
                } else {
                    arr2.add(num)
                }
            }
        }

        return (arr1 + arr2).toIntArray()
    }

    private fun greaterCount(arr: List<Int>, value: Int): Int {
        var count = 0

        for (num in arr) {
            if (num > value) {
                count++
            }
        }

        return count
    }

    // copilot
    fun resultArrayByCopilot(nums: IntArray): IntArray {
        // 1. Coordinate compression
        val sorted = nums.clone()
        sorted.sort()

        val rank = HashMap<Int, Int>()

        var index = 1
        for (num in sorted) {
            if (!rank.containsKey(num)) {
                rank[num] = index++
            }
        }

        // 2. Fenwick trees for arr1 and arr2
        val tree1 = FenwickTree(rank.size)
        val tree2 = FenwickTree(rank.size)

        val arr1 = mutableListOf<Int>()
        val arr2 = mutableListOf<Int>()

        // First two elements
        arr1.add(nums[0])
        tree1.add(rank[nums[0]]!!)

        arr2.add(nums[1])
        tree2.add(rank[nums[1]]!!)

        // 3. Process remaining elements
        for (i in 2 until nums.size) {
            val num = nums[i]
            val r = rank[num]!!

            // Number of elements > num
            val greater1 = arr1.size - tree1.query(r)
            val greater2 = arr2.size - tree2.query(r)

            if (greater1 > greater2) {
                arr1.add(num)
                tree1.add(r)
            } else if (greater1 < greater2) {
                arr2.add(num)
                tree2.add(r)
            } else {
                // Same greaterCount -> choose smaller array
                if (arr1.size <= arr2.size) {
                    arr1.add(num)
                    tree1.add(r)
                } else {
                    arr2.add(num)
                    tree2.add(r)
                }
            }
        }

        // 4. Concatenate arr1 + arr2
        return (arr1 + arr2).toIntArray()
    }

    class FenwickTree(private val n: Int) {
        private val tree = IntArray(n + 1)

        fun add(index: Int) {
            var i = index

            while (i <= n) {
                tree[i]++
                i += i and -i
            }
        }

        // Number of elements with rank <= index
        fun query(index: Int): Int {
            var i = index
            var result = 0

            while (i > 0) {
                result += tree[i]
                i -= i and -i
            }

            return result
        }
    }

}