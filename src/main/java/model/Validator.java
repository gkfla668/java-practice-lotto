package model;

public class Validator {
    private static final String ERROR_COST_MESSAGE = "[ERROR] 로또 한장 당 1000원 입니다.\n1000원 이상, 1000원 단위로 입력해 주세요. \n";
    private static final String ERROR_BONUS_NUMBER_MESSAGE = "[ERROR] 1~45 사이의 보너스 숫자를 입력해 주세요. \n";
    private static final String ERROR_WINNING_NUMBER_LENGTH_MESSAGE = "[ERROR] 6개의 당첨 번호를 입력해 주세요. \n";
    private static final int MIN_LOTTO_COST = 1000;
    private static final int MIN_RANDOM_NUMBER = 1;
    private static final int MAX_RANDOM_NUMBER = 45;
    private static final int LOTTO_COST_REMAINDER = 0;

    // 보너스 넘버_범위_1~45_체크
    public static boolean isValidBonusNumber(int bonusNumber) {
        boolean check = true;

        if (bonusNumber < MIN_RANDOM_NUMBER || bonusNumber > MAX_RANDOM_NUMBER) {
            System.out.println(ERROR_BONUS_NUMBER_MESSAGE);
            check = false;
        }

        return check;
    }

    // 당첨 번호 split 최대 6개
    public static boolean isValidNumberLength(String[] numbers) {
        boolean check = true;

        if (numbers.length != 6) {
            System.out.println(ERROR_WINNING_NUMBER_LENGTH_MESSAGE);
            check = false;
        }

        return check;
    }

    // 구입 금액 체크
    public static boolean isValidCost(int cost) {
        boolean check = true;

        if (!(isValidCostMinCost(cost)) || !(isValidCostUnit(cost))) {
            System.out.println(ERROR_COST_MESSAGE);
            check = false;
        }

        return check;
    }

    // 구입 금액_단위_1000원 단위인지
    private static boolean isValidCostUnit(int cost) {
        return ((cost % MIN_LOTTO_COST) == LOTTO_COST_REMAINDER);
    }

    // 구입 금액_최소금액_1000원인지
    private static boolean isValidCostMinCost(int cost) {
        return cost >= MIN_LOTTO_COST;
    }
}