package task.scheduler.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputHelper {

    private final static Scanner ui = new Scanner(System.in);

    public static String getInput() {
        return ui.nextLine();
    }

    public static String getDuration() {
        String input = ui.nextLine();
        return input.isEmpty() ? "0" : input;
    }

    public static List<Long> parseInput() {
        String input = ui.nextLine();

        return input.isEmpty() ? new ArrayList<>() :
                Arrays.stream(input.split("\\s*,\\s*"))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
