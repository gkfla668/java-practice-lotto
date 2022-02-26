package view;

import java.util.List;

public class OutputView {
    public static void printPurchaseCountMessage(int amount) {
        System.out.println(amount + "개를 구매했습니다.");
    }

    public static void printUserLottoNumber(List<Integer> userLottoNumber) {
        System.out.println(userLottoNumber);
    }

    public static void printWinningStatisticsResult() {
        System.out.println("당첨 통계\n--------------");
        System.out.println("3개 일치 (5,000원) - ");
        System.out.println("4개 일치 (50,000원) - ");
        System.out.println("5개 일치 (1,500,000원) - ");
        System.out.println("5개 일치, 보너스 볼 일치 (30,000,000원) - ");
    }

    public static void printTotalYield(int yield) {
        System.out.println("총 수익률은" + yield + "입니다.");
    }
}
