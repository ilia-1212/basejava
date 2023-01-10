package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStreamTest {
    public static void main(String[] args) {
        int[] intValues = {9, 2, 3, 1, 4, 3, 5};
        System.out.println("числа до обработки: " + Arrays.toString(intValues));

        System.out.println("числа после обработки на уникальность: " + Arrays.toString(IntStream.of(intValues).distinct().toArray()));
        System.out.println("минимальное число: " + minValue(intValues));

        List<Integer> integerList = Arrays.stream(intValues).boxed().toList();
        System.out.println("числа после обработки на четное удаление: " + oddOrEven(integerList));

    }

    private static int minValue(int[] values) {
        return (IntStream.of(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> /*(int)Math.pow(10, (int)(Math.log10(b) + 1)) * a + b*/ 10 * a + b)
        );
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        // Boolean isOddEven = integers.stream().mapToInt(i -> i.intValue()).sum()%2 == 0;
        // Map<Boolean, List<Integer>> tempList = integers.stream().collect(Collectors.partitioningBy(x -> x%2 ==0));
        // return tempList.get(!isOddEven);

        Map<Boolean, List<Integer>> tempMap = integers.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0));
        return tempMap.get(tempMap.size() % 2 != 0);
    }
}
