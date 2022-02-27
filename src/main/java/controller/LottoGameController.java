package controller;

import model.Lotto;
import model.Rank;
import model.WinningLotto;
import view.InputView;
import view.OutputView;

import java.util.*;

import static model.InputValidator.*;

public class LottoGameController {
    private static final int LOTTO_TICKET_PRICE = 1000;
    private static final int LOTTO_MIN_RANDOM_NUMBER = 1;

    public void play() {
        List<Lotto> userLotto = createUserLotto(); // 사용자 로또 생성
        WinningLotto winningLotto = createWinningLotto(); // 당첨 로또 생성
        printWinningStatisticsResult(userLotto, winningLotto); // 당첨 통계 출력
    }

    // 사용자 로또 생성
    private List<Lotto> createUserLotto() {
        return getRandomLottoNumber(getPurchaseAmount());
    }

    // 구매한 로또 갯수만큼 사용자 총 로또 리스트 반환
    public List<Lotto> getRandomLottoNumber(int amount) {
        List<Lotto> userLotto = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            List<Integer> lottoNumber = Lotto.getRandomNumber();
            Lotto lotto = new Lotto(lottoNumber);
            userLotto.add(lotto);
        }

        printUserLottoNumber(userLotto);

        return userLotto;
    }

    // 사용자 총 로또 리스트 출력
    private void printUserLottoNumber(List<Lotto> userLotto) {
        for (Lotto lotto : userLotto) {
            List<Integer> lottoNumber = lotto.getNumber();

            OutputView.printLottoNumber(lottoNumber);
        }

        System.out.println("");
    }

    // 구매 갯수 반환
    private int getPurchaseAmount() {
        int cost = inputCost();
        int amount = calculateCount(cost); // 구매 갯수 계산

        OutputView.printPurchaseAmountMessage(amount); // 구매 갯수 메세지 출력

        return amount;
    }

    // 구매 금액 입력
    private int inputCost() {
        int cost = LOTTO_TICKET_PRICE;

        do {
            cost = InputView.inputLottoPurchaseCost(); // 구매 금액 입력
        } while (!(isValidateCost(cost)));

        System.out.println("");

        return cost;
    }

    // 구매 갯수 계산 메소드
    public int calculateCount(int cost) {
        int count = cost / LOTTO_TICKET_PRICE;

        return count;
    }

    // 당첨 로또 생성
    private WinningLotto createWinningLotto() {
        List<Integer> winningNumber = inputWinningNumber();
        int bonusNumber = inputBonusNumber();

        Lotto lotto = new Lotto(winningNumber); // 로또 티켓 생성

        WinningLotto winningLotto = new WinningLotto(lotto, bonusNumber);

        return winningLotto;
    }

    // 지난 주 당첨 번호 입력
    private List<Integer> inputWinningNumber() {
        String[] winningNumber = new String[6];

        do {
            String lottoNumber = InputView.inputWinningNumber(); // 지난주 당첨번호 입력
            winningNumber = splitLastWeekNumber(lottoNumber); // 당첨번호 , 기준으로 split
        }
        while (!(isValidateWinningNumberLength(winningNumber)));

        List<Integer> winningNumbers = new ArrayList(Arrays.asList(winningNumber));

        return winningNumbers;
    }

    // 지난 주 당첨번호 String > String[] 스플릿
    public String[] splitLastWeekNumber(String number) {
        return number.split(",");
    }

    // 지난 주 보너스 번호 입력
    private int inputBonusNumber() {
        int bonusNumber = LOTTO_MIN_RANDOM_NUMBER;

        do {
            bonusNumber = InputView.inputBonusNumber();
            System.out.println("");
        } while (!(isValidateBonusNumber(bonusNumber)));

        return bonusNumber;
    }

    // 당첨 통계 출력
    private void printWinningStatisticsResult(List<Lotto> userLotto, WinningLotto winningLotto) {
        Map<Rank, Integer> statistics = initHashMap();

        checkMatch(userLotto, winningLotto, statistics);

        int winningMoney = getTotalWinningMoney(statistics);
        int amount = userLotto.size();
        double yield = getTotalYield(winningMoney, amount);

        OutputView.printWinningStatisticsResult(statistics);
        OutputView.printTotalYield(yield);
    }

    // HashMap 초기화
    private Map<Rank, Integer> initHashMap() {
        Map<Rank, Integer> lottoResult = new HashMap<>();

        for (Rank rank : Rank.values()) {
            lottoResult.put(rank, 0);
        }

        return lottoResult;
    }

    // 당첨 통계
    private void checkMatch(List<Lotto> userLotto, WinningLotto winningLotto, Map<Rank, Integer> statistics) {
        for (Lotto lotto : userLotto) {
            Rank rank = winningLotto.match(lotto);
            statistics.put(rank, statistics.getOrDefault(rank, 0) + 1);
        }
    }

    // 총 당첨금액
    private int getTotalWinningMoney(Map<Rank, Integer> statistics) {
        int totalWinningMoney = 0;

        for (Rank rank : Rank.values()) {
            int winningMoney = rank.getWinningMoney();
            int amount = statistics.get(rank);

            totalWinningMoney += winningMoney * amount;
        }

        return totalWinningMoney;
    }

    // 총 수익률
    public double getTotalYield(double winningMoney, int amount) {
        double amountPaid = (amount * LOTTO_TICKET_PRICE);
        double yield = winningMoney / amountPaid;

        return yield;
    }
}
