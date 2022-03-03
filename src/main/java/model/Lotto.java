package model;

import util.RandomLottoNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lotto {
    private static final int LOTTO_TICKET_PRICE = 1000;
    private static final int LOTTO_TICKET_LENGTH = 6;
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        this.numbers = numbers;
    }

    // 매개변수로 받은 숫자로 생성된 로또 반환
    public static Lotto getLotto(List<Integer> numbers) {
        Lotto lotto = new Lotto(numbers); // 로또 티켓 생성

        return lotto;
    }

    // 사용자 랜덤 로또 리스트 반환
    public static List<Lotto> getUserLotto(int count) {
        List<Lotto> userLotto = new ArrayList<>();

        // 랜덤 로또 생성
        for (int i = 0; i < count; i++) {
            Lotto lotto = Lotto.getRandomLotto();
            userLotto.add(lotto);
        }

        return userLotto;
    }

    // 로또 구입 갯수 계산 메소드
    public static int getCount(int cost) {
        return cost / LOTTO_TICKET_PRICE;
    }

    // string[] > list<Integer>
    public static List<Integer> changeStringArrayToList(String[] number) {
        int[] numbers = Arrays.stream(number).mapToInt(Integer::parseInt).toArray(); // string[] > int[]

        return IntStream.of(numbers).boxed().collect(Collectors.toList());
    }

    // 지난 주 당첨번호 String > String[] 스플릿
    public static String[] splitNumbers(String number) {
        return number.split(",");
    }

    // 로또 랜덤 숫자 반환
    private static List<Integer> getRandomNumbers() {
        List<Integer> randomNumbers = new ArrayList<>();

        for (int i = 0; i < LOTTO_TICKET_LENGTH; i++) {
            int numbers = RandomLottoNumberGenerator.randomLottoNumber(); // 랜덤 로또 번호 생성
            randomNumbers.add(numbers);
        }

        return randomNumbers;
    }

    // 랜덤 숫자로 생성된 로또 반환
    private static Lotto getRandomLotto() {
        List<Integer> randomNumber = getRandomNumbers();

        return new Lotto(randomNumber);
    }

    public List<Integer> getNumber() {
        return this.numbers;
    }

    // 당첨 번호와 사용자 로또 번호 중 일치하는 번호가 몇개인지 반환
    public int getMatchCount(Lotto winningLotto) {
        List<Integer> user = numbers;
        List<Integer> winning = winningLotto.getNumber();
        List<Integer> win = new ArrayList<>();

        for (Integer i : winning) {
            win.add(Integer.valueOf(String.valueOf(i)));
        }

        user.retainAll(win);

        return user.size();
    }

    // 보너스 번호를 포함하고 있는지 true/false 반환
    public boolean isContainsBonusNumber(int bonusNo) {
        return numbers.contains(bonusNo);
    }
}