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
}